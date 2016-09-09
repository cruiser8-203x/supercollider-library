Grid {

	var<>view;
	var<>xlocInParent;
	var<>ylocInParent;
	var<>initWidth;
	var<>initHeight;
	var<>color;
	var<>parent;
    var<>overflow;

	var<>new_butt_w_resize;
	var<>actionTypeForMusicNote=0;

	var<> screenWidth;
	var<> screenHeight;
	var<> windowHorzBarHeightArea=40;
	var<> windowVertBarWidthArea=32;



	var<> timeLineBar;
	var<> timeBarXPosition = 0;
	var<> timeBarYPosition = 0;
	var<> timeBarWidth;
	var<> timeBarHeight=30;
	var<> writingspace=23;
	var<> timeBarColour;
	var<> timeIndicator;
	var<> loopLine=nil;



	var<> xPointoffset=0;//this is the start point for lines going down and offsetting across
	var<> xPointoffsetUnit=100;
	var<> yPointoffsetUnit=0;
	var<> yPointOffset=0;//this is the start point for lines going across and offsetting down

	var<> yStartPointOfDownLine=0;//line will start at start of gridview top
	var<> yEndPointOfDownLine;
	var<> beatLineDowncolour; //light lines down
	var<> barLineDownColour;//dark lines down
	var<> octaveLineAcrossColour;
	var<> pitchLineAcrossColour;
	var<> accentedLineNoAcrossWays=12;
	var<> accentedLineNoDownWays=4;
	var<> numOfGridLines=0;


	var<> playNote;
	var<> stop=true;
	var<> nextNoteInt=0;
	var<> pitchArr;
	//editing musical note
	var<> lengthOfNoteObj;
	var<> pointsAwayFromEdge=15;
	var<> rightMoveBoundary;
	var<> leftMoveBoundary;
	var<> noteStartPositionBeforeEdit;
	var<> mouseXStartPositionBeforeEdit;
	var<> mouseYStartPositionBeforeEdit;
	var<> adjustRightSide=false;
	var<> adjustLeftSide=false;
	var<> newWidth=0,stpwidth=3;
	var<> drawNormalBorderFunc,drawBorderSelectedFunc,musicNoteEventsFunc;







	render
	{



		parent.drawFunc =
		{
			|v|
			//CREATE TIMELINE BAR IN VIEW
			this.timeLineBar = Rect(this.timeBarXPosition,this.timeBarYPosition, timeBarWidth, this.timeBarHeight);
			timeBarColour.set;
			Pen.fillRect(timeLineBar);

			//CREATE GRID LINES IN VIEW
			Pen.smoothing_(false); //no anti-aliasing
			this.numOfGridLines.do
			{|index|
				this.xPointoffset=this.xPointoffset+this.xPointoffsetUnit;

				//SET DOWN LINES AND OFFSET ACROSS
				Pen.strokeColor= Color.grey(0.60).set;
				Pen.moveTo(this.xPointoffset@(this.yStartPointOfDownLine));
				Pen.lineTo(this.xPointoffset@this.yEndPointOfDownLine);

				if(((index+1)%accentedLineNoDownWays)==0)
				{
					Pen.stringAtPoint(((index+1)/accentedLineNoDownWays)+"", this.xPointoffset-3@5);
					Pen.strokeColor = barLineDownColour;
					Pen.stroke;
				}
				{
					Pen.stringAtPoint((index)%accentedLineNoDownWays+1+"", this.xPointoffset-3@5);
					Pen.strokeColor = beatLineDowncolour;
					Pen.stroke;
				};

				//SET ACROSS LINES AND OFFSET DOWN
				Pen.moveTo(0@yPointOffset);
				Pen.lineTo(this.screenWidth@yPointOffset);
				yPointOffset=yPointOffset+this.yPointoffsetUnit;
				if(((index)%accentedLineNoAcrossWays)==0)
				{
					Pen.strokeColor = this.octaveLineAcrossColour;
					Pen.stroke;
				}
				{
					Pen.strokeColor = this.pitchLineAcrossColour;
					Pen.stroke;
				};



			};

			//RESET OFF SET INCASE DRAWFUNC IS CALLED MORE THAN ONCE AT RUNTIME BEFORE SCRIPT ENDS
			this.yPointOffset=0;
			yPointOffset=0;
			this.xPointoffset=0;
			this.xPointoffset=0;

			Pen.stroke;
		};

	}

	setGridLineXOffSet{arg offset;
		this.xPointoffsetUnit=offset;
	}
	setGridLineYOffSet{arg offset;
		this.yPointoffsetUnit=offset;
	}
	setAccentedLineAcrossEach{arg each;
		this.accentedLineNoAcrossWays=each;
	}
	setAccentedLineDownEach{arg each;
		this.accentedLineNoDownWays=each;
	}









}