TimeIndicator : RectangleClass {


	 addTimeLineEvents {arg edgeEditOffset;


		 this.addDefMouseDownEvent(edgeEditOffset);
		 this.addDefMouseMoveEvent;
		 this.addDefMouseUpEvent;

    }



	addDefMouseDownEvent
	{
		arg edgeEditOffset;


		view.mouseDownAction = { |view,mouse_x,mouse_y|
              this.mouse_x_on_press=mouse_x;
			  this.mouse_y_on_press=mouse_y;
			  this.xRightLocFromSide=initWidth-this.mouse_x_on_press;
			  this.setZonePressed(0);

		 };

	}

	addDefMouseMoveEvent
	{

		view.mouseMoveAction = { |view,mouse_x,mouse_y|
		    var offsetX=this.mouse_x_on_press-mouse_x;
            var offsetY=this.mouse_y_on_press-mouse_y;


			if(this.zonePressedDown=="right"){
				var newWidth;
				if(mouse_x+xRightLocFromSide<this.minWidth,{
				   newWidth=this.minWidth-1;
				},{
				   newWidth=mouse_x+xRightLocFromSide;
			    });
				this.changeWidthR(newWidth);
			};

			if(this.zonePressedDown=="centre"){

				this.changeXLocation(xlocInParent-(offsetX));
				if(xlocInParent<0)
				{
					this.changeXLocation(0);
				}
			};

			if(this.zonePressedDown=="left"){
                var newLeft;
				//postln("[right side="+xRightLocInParent+"] ["+(xlocInParent-(offsetX))+"]");
				if((xlocInParent-(offsetX))>(this.minWidth-xRightLocInParent),{
				   newLeft=xlocInParent-(offsetX);
				},{
				   newLeft=this.minWidth-xRightLocInParent;
			    });

				this.changeWidthL(newLeft,offsetX);

			};

		};




	}

	addDefMouseUpEvent
	{
		view.mouseUpAction = { |view,mouse_x,mouse_y|
        this.zonePressedDown="";
			};

	}
}
