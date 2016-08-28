KeyboardGrid : Grid {
/*
	//CREATE A MUSIC NOTE
	var<> y_down_f_pos=0;
	var<> x_down_f_pos=0;
	var<> vectorPoint,vectorPointNew,new_def_butt_width,new_butt_w_resize,strokeAsRect;
	var<> new_def_butt_height;
	var<> y_mouse_bar_loc;
	var<> x_mouse_bar_loc;
	var<> mouseDownIsTrue=false;
	var<> selectionArray;


var<> newlayer=0;
	var<> vectorLayerCollection=nil;
	var<> selNotesCollection=nil;
	var<> allNotesCollection=nil;

		var<> loopLine;

	var<> snapLocation;

	var<> i;
	var <> t;
	var <> h;
	var <> r;
	var <> s;
	var <> pitchArr;

	var<> defmusicnoteheight=25;//offset pitch line by 20

	*new {arg parent,numOfGridLines;
		^super.new.init(parent,numOfGridLines);

	}

	init { arg parent,numOfGridLines;
	     // do initiation here

		var numOfLayers=10;
		this.parent=parent;
		this.overflow=this.parent.bounds.width;


		//default attributes
		this.barLineDownColour=Color.grey(0.16);//dark lines down
		this.octaveLineAcrossColour=Color.grey(0.16);
		this.yStartPointOfDownLine=this.writingspace;
		this.yEndPointOfDownLine=parent.bounds.height;
		this.screenWidth=parent.bounds.width;
		this.timeBarWidth=this.screenWidth;
		this.timeBarColour=Color.grey(0.02);
		yPointOffset=this.timeBarHeight;

		this.parent=parent;

		this.pitchLineAcrossColour=Color.grey(0.12);
		this.beatLineDowncolour=Color.grey(0.12); //light lines down

		this.yPointoffsetUnit=defmusicnoteheight;
		pitchArr=[587.33,554.37,523.25,493.88,466.16,440,
			415.30,392.00,369.99,349.23,329.63,311.13,293.66,
			277.18,261.63, 246.94,233.08,220.00,207.65,196.00,
			185.00,174.61,164.81,155.56,146.83,138.59,130.81,123.47,
			116.54,110.00,103.83,98.00,92.50,87.31,82.41,77.78,73.42,
			69.30,65.41,61.74,58.27,55.00
		];



		this.numOfGridLines = numOfGridLines;

	}


	addToPointCollection{
		arg pointCollectionArg;
		if(this.allNotesCollection.isNil,{
			this.allNotesCollection=[pointCollectionArg];
		}
		,
		{
			this.allNotesCollection=this.allNotesCollection.add(pointCollectionArg);
			this.allNotesCollection.sort({arg start,end; start.xlocInParent<end.xlocInParent});
	    });
	}

	addPointToSelectionArray{
		arg pointCollectionArg;
		if(this.selNotesCollection.isNil,{
		this.selNotesCollection=[pointCollectionArg];
		}
		,
		{
				this.selNotesCollection=this.selNotesCollection.add(pointCollectionArg);
	    });
	}

	deselectAll{
		this.selNotesCollection.do
		(
			{
				arg valueofitem_placeholder,
				index_placeholder;

                valueofitem_placeholder.addBorder(Color.red);
		    }

		);
		this.selNotesCollection=nil;

	}

	removeAllSelected{

		this.selNotesCollection.do
		(
			{
				arg valueofitem_placeholder,
				index_placeholder;

				if(valueofitem_placeholder!=nil,{

				this.allNotesCollection.remove(valueofitem_placeholder);
				valueofitem_placeholder.removeRect;
			});
				valueofitem_placeholder= nil;
		    }

		);
		this.selNotesCollection=nil;

	}

	addDeleteEvents{
		parent.keyDownAction = { arg view, char, modifiers, unicode, keycode;
			if(keycode==46,
				{
					this.removeAllSelected;
				}
			);
			if(keycode==8,
				{
					this.removeAllSelected;
				}
			);

		};
	}

	addMouseMove
	{
		arg new_butt_w_resize,vectorPoint,x_down_f_pos;
		parent.mouseMoveAction_(
			{|view,mouse_x,mouse_y|
				if(this.actionTypeForMusicNote==0)
				{
					//RESIZE NOTE ON CREATE
					new_butt_w_resize=mouse_x-x_down_f_pos;
					if(new_butt_w_resize>0,
						{
							vectorPoint.changeWidthR(new_butt_w_resize);
					});
				};
		});
	}
	addGridViewDown
	{
		parent.mouseDownAction_({|view_placeholder,x_placeholder,y_placeholder|

			//button state draw
			if(actionTypeForMusicNote==0)
			{
				y_down_f_pos=y_placeholder;
				x_down_f_pos=x_placeholder;

				snapLocation=((y_placeholder-timeBarHeight)/this.yPointoffsetUnit).round(1)-1;
				y_mouse_bar_loc=(timeBarHeight+2)+(this.yPointoffsetUnit*snapLocation)+(this.yPointoffsetUnit/2);

				snapLocation=(x_placeholder/this.xPointoffsetUnit).round(1);
				x_mouse_bar_loc=(this.xPointoffsetUnit*snapLocation)-(this.xPointoffsetUnit/2);


				//IF WITHIN AREA OF GRID
				if(timeBarHeight<y_placeholder,
					{
						vectorPoint = VectorClass.new(
							view_placeholder,
							x_mouse_bar_loc+2, //for some reason the first line down is offset by 2 greater than the rest
							y_mouse_bar_loc,
							this.yPointoffsetUnit-2,
							this.yPointoffsetUnit-2,
							Color.red,
							grid:this
						  );

						vectorPoint.addBorder(Color.white);
						vectorPoint.addNoteEvents(6,6);

						this.deselectAll(vectorPoint);
						VectorClass.grid(timeBarHeight,defmusicnoteheight);
						this.addPointToSelectionArray(vectorPoint);
						this.addToPointCollection(vectorPoint);
						this.createLine(this.parent,x_down_f_pos,y_down_f_pos);

					}

				);
			};
		});
	}
	createLine
	{
		arg parentCanvas,x_down_f_pos,y_down_f_pos;
		var vectorShapeLayer=UserView.new
		(
			parentCanvas,// parent
			Rect( left:x_down_f_pos, top:x_down_f_pos, width:1000, height:1000 )
		);

		if(this.vectorLayerCollection.isNil,{
			this.vectorLayerCollection=[vectorShapeLayer];
		}
		,
		{
			this.vectorLayerCollection=this.vectorLayerCollection.add(vectorShapeLayer);
	    });

		this.vectorLayerCollection[this.newlayer].drawFunc =
		{
			|v|

		    Color.red.set;

			Pen.moveTo(0@0);//x / y

			Pen.lineTo(0@510);
	        Pen.lineTo(20@500);
	        Pen.lineTo(20@20);


	        //RIGHT
			Pen.moveTo(1000@0);

			Pen.lineTo(900@520);
	        Pen.lineTo(880@500);
	        Pen.lineTo(880@20);
	        Pen.lineTo(900@0);


	        //  TOP
	        Pen.moveTo(0@0);//x / y

			Pen.lineTo(900@0);
	        Pen.lineTo(880@20);
	        Pen.lineTo(20@20);
	        Pen.lineTo(0@0);



	        //BOTTOM
	        Pen.moveTo(0@520);

			Pen.lineTo(900@520);
	        Pen.lineTo(880@500);
	        Pen.lineTo(20@500);
	        Pen.lineTo(0@520);



			Pen.stroke;

		};
		this.vectorLayerCollection[this.newlayer].refresh;
		this.newlayer=this.newlayer+1;


	}
	*/


}
