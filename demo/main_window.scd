(

var window;
var openGrid;

//START SERVER SOUND
/*
s = Server.local;
s.options.device ="ASIO4ALL";
s.boot;
*/

var currentPath=(thisProcess.nowExecutingPath.dirname)++"/sequencer_window.scd";

//WINDOW, GRID OVERFLOW INITIALIZATION

window = Window.new(
	"Supercollider GUI",
	Rect(left:0, top:0, width:1000, height:600
  )
);

window.background_(Color.grey(0.05));
window.front;


//PLAY BUTTON

openGrid = Button(
   window,
   Rect( left:120, top:5, width:100, height:28)
   )
  .states_(
	[
	 [
		"View Grid",
		Color.white,
		Color.blue;
	 ]
    ]
  )
  .action_(
	        { arg butt;
		(currentPath).loadPaths;

            }
           );



window.refresh;

)