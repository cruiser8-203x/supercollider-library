(

var window;
var screenWidth  = Window.screenBounds.width;
var screenHeight = Window.screenBounds.height;
var windowHorzBarHeightArea = 40;
var windowVertBarWidthArea  = 40;

var scrollview;
var scrollwidth;

var grid;
var grid_window;
var view_width,view_height;
var grid_container;

var keyboard;
var keyboard_window;
var keyboard_container;
var keyboardgrid;

var yZoomSlider;
var xZoomSlider;

var drawBtn;
var playBtn;
var instrumentBtn;

//WINDOW, GRID OVERFLOW INITIALIZATION

window = Window.new(
	"Colins Supercollider GUI",
	Rect(left:0, top:0, width:1000, height:600
  )
);

window.background_(Color.grey(0.05));
window.front;
window.acceptsMouseOver = true;



//SCROLLVIEW FOR

scrollwidth = screenWidth - windowVertBarWidthArea;

scrollview = ScrollView(
	window,
	Rect(left:20 ,  top:windowHorzBarHeightArea , width:scrollwidth  - 22 , height:647)
  )
  .hasBorder_(false);





//GRID
//COMPOSITE VIEW WHICH I USE FOR HIDING GRID

view_height = screenHeight-windowHorzBarHeightArea;

grid_container = CompositeView.new(scrollview,Rect(0,0,2000,view_height));


grid_window =
    UserView.new
      (
	    grid_container ,       // parent
	    Rect( left:0, top:0, width:2000, height:view_height )
      );

grid_window.background_(Color.grey(0.10));





//KEYBOARD

keyboard_container = CompositeView.new(scrollview,Rect(0,0,2000,view_height));

keyboard_window =
    UserView.new
      (
	    keyboard_container,       // parent
	    Rect( left:0, top:0, width:2000, height:view_height )
      );



keyboard_window.background_(Color.grey(0.10));

//GRID VISIBLE ON LOAD UP SO...

keyboard_container.visible=false;

//CREATION OF GRID

grid = SequencerGrid.new(parent:grid_window,numOfGridLines:1000);

grid.render();
grid.addGridViewDown();
grid.addTimeLine();
grid.addDeleteEvents();

//CREATION OF GRID
/*
keyboardgrid = KeyboardGrid.new(parent:keyboard_window,numOfGridLines:1000);
keyboardgrid.setGridLineXOffSet(8);
keyboardgrid.setGridLineYOffSet(8);
keyboardgrid.setAccentedLineAcrossEach(5);
keyboardgrid.setAccentedLineDownEach(5);
keyboardgrid.render();

keyboardgrid.addDeleteEvents();
keyboardgrid.addVectorLine();
keyboardgrid.addGridViewDown();
keyboardgrid.addGridMouseOver();
*/

//SLIDERS

xZoomSlider=EZSlider(
	window,
	Rect(screenWidth-315, 5, 300, 28),
	" xoom ",
	[1, 100, \exp, 1, 0, "%"].asSpec,
	unitWidth:28,
	labelWidth:60,
	numberWidth:30,
	layout:\horz,
	margin: 0@0
  );

xZoomSlider.setColors(
	stringBackground: Color.black,
	stringColor: Color.red,
	sliderBackground: Color.grey(0.3),
	numBackground:Color.white,
	numStringColor:Color.blue,
	numNormalColor:Color.grey(0.3),
	numTypingColor:nil,
	knobColor:nil,
	background:Color.grey(0.7)
  );

xZoomSlider.font_(Font("Helvetica",10));

yZoomSlider=EZSlider(
	window,
	Rect(screenWidth - 35, 40, 32 , 300),
	" yoom ",
	[1, 100, \exp, 1, 0, "%"].asSpec,
	unitWidth:10,
	labelHeight:30,
	numberWidth:30,
	layout:\vert,
	margin: 0@0
  );

yZoomSlider.setColors(
	stringBackground: Color.black,      //main background
	stringColor: Color.red,        //label text colour
	sliderBackground: Color.grey(0.3), //scrollline
	numBackground:Color.white,      //percentage textbox background color
	numStringColor:Color.blue,
	numNormalColor:Color.grey(0.3),  //percentage text color
	numTypingColor:nil,
	knobColor:nil,
	background:Color.grey(0.7)  //scroll track border
  );




//BUTTON TOGGLE FOR CREATING AND EDITING MUSIC NOTE

drawBtn = Button(
	window,
	Rect( left:10, top:5, width:100, height:28
        )
    )
    .states_(
	[
	 [
		"Edit",
		Color.white,
		Color.red
	 ],
	 [
		"Draw",
	    Color.white,
	    Color.red
	 ]
    ]
    )
    .action_({ arg butt;

	   MusicNote.editmode=butt.value;
	   grid.actionTypeForMusicNote=butt.value;
	  // keyboardgrid .actionTypeForMusicNote=butt.value;
    });




//PLAY BUTTON

playBtn = Button(
   window,
   Rect( left:120, top:5, width:100, height:28)
   )
  .states_(
	[
	 [
		"Play",
		Color.black,
		Color.green
	 ],
	 [
		"Stop",
		Color.grey(0.20),
		Color.red

	 ]
    ]
  )
  .action_(
	        { arg butt;

	          if(butt.value==0,
	          {
			    grid.stop=true;
			    grid.stopAppClock();
		      },
		      {
				grid.stop=false;
				/*
				   play button added to grid class
				   for access to its state
				*/
		        grid.playIndicator(playBtn);

	          }
	         );
            }
           );

instrumentBtn = Button(
   window,
   Rect( left:230, top:5, width:100, height:28)
   )
  .states_(
	[
	 [
		"Instrument",
		Color.black,
		Color.green
	 ],
	 [
		"Sequencer",
		Color.white,
		Color.blue

	 ]
    ]
  )
  .action_(
	        { arg butt;

	          if(butt.value==0,
	          {
				grid_container.visible=true;
				keyboard_container.visible=false;

		      },
		      {
				keyboard_container.visible=true;
				keyboard_window.front;
				grid_container.visible=false;
			}
	         );
            }
           );

window.refresh;
window.onClose_()
{
	//s.quit;
}


)

