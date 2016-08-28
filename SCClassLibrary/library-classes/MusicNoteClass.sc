MusicNote : RectangleClass{
     classvar<>selNotesCollection=nil;
     classvar<>allNotesCollection=nil;
	 classvar<>topGridPos=0;
	 classvar<>gridLineOffset=0;
	 var<>grid;

	 *new { arg view,xloc,yloc,initWidth,initHeight,color,grid;
		^super.new.init(view,xloc,yloc,initWidth,initHeight,color,grid);

    }

	init { arg view,xloc,yloc,initWidth,initHeight,color,grid;
        this.view=UserView(view,Rect(xloc,yloc,initWidth,initHeight)).background_(color);
		this.parentview=view;
		this.xlocInParent=xloc;
		this.ylocInParent=yloc;
		this.initWidth=initWidth;
		this.initHeight=initHeight;
		this.color=color;
		this.grid=grid;
    }

	 addNoteEvents {arg edgeEditOffset;
		 this.addDefMouseDownEvent(edgeEditOffset);
		 this.addDefMouseMoveEvent;
		 this.addDefMouseUpEvent;
    }

	*grid{arg gridstart,gridheight;
         this.topGridPos=gridstart;
		 this.gridLineOffset=gridheight;
	}






	addDefMouseDownEvent
	{
		arg edgeEditOffset;


		view.mouseDownAction = { |view,mouse_x,mouse_y|
              this.mouse_x_on_press=mouse_x;
			  this.mouse_y_on_press=mouse_y;
			  this.xRightLocFromSide=initWidth-this.mouse_x_on_press;


			  if(this.grid.actionTypeForMusicNote==1,
			  {
				//MusicNote.removeAll;
				 this.grid.deselectAll;

			     this.grid.addToNoteSelectionArray(this);
				//MusicNote.addToNoteCollection(this);
				this.addBorder(Color.white);
			    this.setZonePressed(edgeEditOffset);

			  },
			  {

				 this.musicNoteObj = MusicNote.new(
						this.parentview,
						mouse_x+this.xlocInParent,
						this.ylocInParent,
						1,
						initHeight,
						color:Color.red,
						grid:this.grid
					);

				 this.grid.deselectAll;
				 this.grid.addToNoteSelectionArray(musicNoteObj);
				 this.grid.addToNoteCollection(musicNoteObj);
				 this.musicNoteObj.addBorder(Color.white);
				 this.musicNoteObj.addNoteEvents(10);
			  });

		 };

	}

	addDefMouseMoveEvent
	{

		view.mouseMoveAction = { |view,mouse_x,mouse_y|
		if(this.grid.actionTypeForMusicNote==1,
	    {

			var offsetX=this.mouse_x_on_press-mouse_x;
            var offsetY=this.mouse_y_on_press-mouse_y;
			var newXLocation;
			var newYLocation;

			if(this.zonePressedDown=="right"){
				var newWidth;
				if(mouse_x+xRightLocFromSide<this.minWidth,{
				   newWidth=this.minWidth-1;
				},{
				   newWidth=mouse_x+xRightLocFromSide;
			    });
				this.changeWidthR(newWidth);
				this.grid.allNotesCollection.sort({arg start,end; start.xlocInParent<end.xlocInParent});
			};

			if(this.zonePressedDown=="centre"){

						//ONLY MOVES IF WITHIN BOUNDARIES
						if((ylocInParent-offsetY)<(topGridPos+2) && (offsetY>0),
						{
							newYLocation=topGridPos+2;
						}
						,
						{
							newYLocation=ylocInParent-(offsetY);
						}
						);

				if((xlocInParent-offsetX)<0 && (offsetX>0),
						{
							newXLocation=0;
						}
						,
						{
							newXLocation=xlocInParent-(offsetX);
						}
						);
				this.changeXYLocation(newXLocation,newYLocation);
				this.grid.allNotesCollection.sort({arg start,end; start.xlocInParent<end.xlocInParent});
						/**/

			};

			if(this.zonePressedDown=="left"){
                var newLeft;
				if((xlocInParent-(offsetX))>(this.minWidth-xRightLocInParent),{
				   newLeft=xlocInParent-(offsetX);
				},{
				   newLeft=this.minWidth-xRightLocInParent;
			    });

				this.changeWidthL(newLeft,offsetX);
				this.grid.allNotesCollection.sort({arg start,end; start.xlocInParent<end.xlocInParent});
			};

		 },
		 {
					var new_butt_w_resize=mouse_x-this.mouse_x_on_press;
					this.musicNoteObj.changeWidthR(new_butt_w_resize);

			});
    	 };



	}

	addDefMouseUpEvent
	{
		view.mouseUpAction = { |view,mouse_x,mouse_y|
			var newXLocation;
					var newYLocation;
		if(editmode==1,
			  {

			if(this.zonePressedDown=="centre"){

					var p=(((this.ylocInParent+mouse_y)-topGridPos)/gridLineOffset).roundUp(1);
					var y_mouse_gridnumber=(topGridPos+2)+(gridLineOffset*(p-1));

						if(y_mouse_gridnumber>(topGridPos+2),
				{
					newYLocation=y_mouse_gridnumber;
				},
				{
					newYLocation=topGridPos+2;

				});

				this.changeYLocation(newYLocation);

			}

			});
		};

        this.zonePressedDown="";

	}



}
