RectangleClass {

	//changes the instance of it only

    var<>view;
	var<>xlocInParent;
	var<>ylocInParent;
	var<>initWidth;
	var<>initHeight;
	var<>color;


	//edits
	var<>minWidth=8;
	var<>editSideCutoffLength=3;
	var<>mouse_x_on_press=0;
	var<>mouse_y_on_press=0;
	var<>xRightLocInParent=0;
	var<>xRightLocFromSide=0;
    var<>zonePressedDown=0;

	classvar<>editmode=0;
    classvar<>isSelected=0;

    classvar<>j=nil;
	var<> musicNoteObj;
	var<>parentview;


	//static variable inside class. Every class contains the same value for it


	*new {
        ^super.new
    }



    /////////////////////////////////////////////////
    // this is a constructor method with paremeters
    *new1 { arg view,xloc,yloc,initWidth,initHeight,color;
		^super.new.init(view,xloc,yloc,initWidth,initHeight,color);
    }


	init { arg view,xloc,yloc,initWidth,initHeight,color;
        this.view=UserView(view,Rect(xloc,yloc,initWidth,initHeight)).background_(color);
		this.parentview=view;
		this.xlocInParent=xloc;
		this.ylocInParent=yloc;
		this.initWidth=initWidth;
		this.initHeight=initHeight;
		this.color=color;


    }


	setZonePressed{arg edgeEditOffset;

		if(edgeEditOffset<1,
		{
			this.zonePressedDown="centre";
		},
		{
				if(this.mouse_x_on_press<edgeEditOffset,
				{
					this.zonePressedDown="left";
				},
				{
					if(this.mouse_x_on_press>(this.initWidth-edgeEditOffset),
				    {
					    this.zonePressedDown="right";
					},
					{
						this.zonePressedDown="centre";
					});
			     });
		});
	}
    //////////////////////////////////////////////////

	//FUNCTION TO DRAW A BORDER AROUND NOTE
	addBorder {arg color;


           view.drawFunc = {
			    //set rectange dimensions and colour
	            var strokeAsRect = Rect(1,1, view.bounds.width-2, view.bounds.height-2);
		        color.set;
                Pen.width=1;
	            //draw the outline of a rectangle
	            Pen.strokeRect(strokeAsRect);


		   };
      view.refresh;


    }

	removeRect {


           view.remove;


    }




	setMinEditWidth{arg minWidth;
	    this.minWidth=minWidth;
	}




	setNewNoteDimensions{
		xlocInParent=view.bounds.left();
		initWidth=view.bounds.width();
		initHeight=view.bounds.height();
		xRightLocInParent=view.bounds.left()+initWidth;
	}

	*editModeOn {arg isOn;
		this.editmode=isOn;
    }

	changeXLocation{arg xlocInParentParam;
        view.bounds=Rect(xlocInParentParam,this.ylocInParent,this.initWidth,this.initHeight);
		view.refresh;
		this.xlocInParent=xlocInParentParam;

	}

	changeYLocation{arg ylocInParentParam;
        view.bounds=Rect(this.xlocInParent,ylocInParentParam,this.initWidth,this.initHeight);
		view.refresh;
        this.ylocInParent=ylocInParentParam;
	}

	changeXYLocation{arg xlocInParentParam,ylocInParentParam;
        view.bounds=Rect(xlocInParentParam,ylocInParentParam,this.initWidth,this.initHeight);
		view.refresh;
		this.xlocInParent=xlocInParentParam;
        this.ylocInParent=ylocInParentParam;
	}

	*changeAllHeight{arg offsetY;


		/*
		change the arrayNote to change all notes
		view.bounds=Rect(this.xlocInParent,this.ylocInParent,this.initWidth,this.initHeight+(offsetY));

		view.refresh;
		setNewNoteDimensions;
		*/
	}

	changeWidthL{arg xlocInParentParam,offsetX;


		view.bounds=Rect(xlocInParentParam,this.ylocInParent,this.initWidth+(offsetX),this.initHeight);

		view.refresh;
		this.setNewNoteDimensions;

	}

	changeWidthR{arg newWidth;
        view.bounds=Rect(this.xlocInParent,this.ylocInParent,newWidth,this.initHeight);
		view.refresh;
		this.setNewNoteDimensions;
	}
}
