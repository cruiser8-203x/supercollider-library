SequencerGrid : Grid {

//CREATE A MUSIC NOTE
	var<> y_down_f_pos=0;
	var<> x_down_f_pos=0;
	var<> musicNoteObj,musicNoteObjNew,new_def_butt_width,new_butt_w_resize,strokeAsRect;
	var<> new_def_butt_height;
	var<> y_mouse_bar_loc;
	var<> mouseDownIsTrue=false;
	var<> selectionArray;

		var<> loopLine;
	var<> timeIndicator;
	var<> p;

	var<> i;
	var <> t;
	var <> h;
	var <> r;
	var <> s;
	var <> pitchArr;

	var<> defmusicnoteheight=25;//offset pitch line by 20

	var<> selNotesCollection=nil;
	var<> allNotesCollection=nil;

	*new {arg parent,numOfGridLines;
		^super.new.init(parent,numOfGridLines);

	}

	init { arg parent,numOfGridLines;
	     // do initiation here
		this.parent=parent;
		this.overflow=this.parent.bounds.width;


		//default attributes

		this.pitchLineAcrossColour=Color.grey(0.20);
		this.octaveLineAcrossColour=Color.grey(0.35);
		this.yStartPointOfDownLine=this.writingspace;
		this.yEndPointOfDownLine=parent.bounds.height;
		this.screenWidth=parent.bounds.width;
		this.timeBarWidth=this.screenWidth;
		this.timeBarColour=Color.grey(0.02);
		yPointOffset=this.timeBarHeight;
		//this.defmusicnoteheight=yPointoffsetUnit;

		this.parent=parent;
		this.beatLineDowncolour=Color.grey(0.15); //light lines down
		this.barLineDownColour=Color.grey(0.35);//dark lines down
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

	playIndicator
	{
		arg playBtn;



		nextNoteInt=0;
		r  = Routine
		({
			arg appClockTime;
			i=timeIndicator.xlocInParent;
			this.overflow.do({

				(1/this.xPointoffsetUnit).yield;
				if(this.allNotesCollection!=nil,{

					while({stop==false} && {this.allNotesCollection.size>nextNoteInt}, {
						if(this.allNotesCollection[nextNoteInt].xlocInParent==i,{

							if(pitchArr.size>((this.allNotesCollection[nextNoteInt].ylocInParent-32)/14),
							{
							Synth
							     (\example5,
								  [
									\out,0,
									\freq,pitchArr[((this.allNotesCollection[nextNoteInt].ylocInParent-32)/14)],
									\dur,(this.allNotesCollection[nextNoteInt].initWidth/80),
									\root,-24
								  ]

							    );

							}
							);
							nextNoteInt=nextNoteInt+1;
							},
							{
								stop=true;
							}
						);//while end

					});//check note pos end
					stop=false;

				}); // is note array check end


				i=i+1;


				if(loopLine.isNil,
					{
						timeIndicator.changeXLocation(i);

					},
					{

						if(loopLine.xlocInParent<timeIndicator.xlocInParent,
							{
								i=0;
								nextNoteInt=0;
								timeIndicator.changeXLocation(0);
							},
							{
								timeIndicator.changeXLocation(i);
							}
						);
					}
				);

				//Implement at some stage
				//t.string=timeIndicator.xlocInParent/this.xPointoffsetUnit;
				//h.string=timeIndicator.xlocInParent;

			});//overflow.do end

			playBtn.value=0;

		});//routine end
		AppClock.play(r);

	}

	stopAppClock
	{
		AppClock.clear;

	}

	addToNoteCollection{
		arg noteCollectionArg;
		if(this.allNotesCollection.isNil,{
			this.allNotesCollection=[noteCollectionArg];
		}
		,
		{
			this.allNotesCollection=this.allNotesCollection.add(noteCollectionArg);
			this.allNotesCollection.sort({arg start,end; start.xlocInParent<end.xlocInParent});
	    });
	}

	addToNoteSelectionArray{
		arg noteCollectionArg;
		if(this.selNotesCollection.isNil,{
		this.selNotesCollection=[noteCollectionArg];
		}
		,
		{
				this.selNotesCollection=this.selNotesCollection.add(noteCollectionArg);
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
		arg new_butt_w_resize,musicNoteObj,x_down_f_pos;
		parent.mouseMoveAction_(
			{|view,mouse_x,mouse_y|
				if(this.actionTypeForMusicNote==0)
				{
					//RESIZE NOTE ON CREATE
					new_butt_w_resize=mouse_x-x_down_f_pos;
					if(new_butt_w_resize>0,
						{
							musicNoteObj.changeWidthR(new_butt_w_resize);
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

				p=((y_placeholder-timeBarHeight)/defmusicnoteheight).roundUp(1);
				y_mouse_bar_loc=(timeBarHeight+2)+(defmusicnoteheight*(p-1));

				//IF WITHIN AREA OF GRID
				if(timeBarHeight<y_placeholder,
					{

						postln(new_def_butt_height);
						musicNoteObj = MusicNote.new(
							view_placeholder,
							x_placeholder,
							y_mouse_bar_loc,1,defmusicnoteheight-3,Color.red,grid:this
						  );

						this.deselectAll(musicNoteObj);
						MusicNote.grid(timeBarHeight,defmusicnoteheight);
						this.addToNoteSelectionArray(musicNoteObj);
						this.addToNoteCollection(musicNoteObj);

						musicNoteObj.addBorder(Color.white);
						musicNoteObj.addNoteEvents(10,6);
						this.addMouseMove(new_butt_w_resize,musicNoteObj,x_down_f_pos);

					},
					{
						if(loopLine.isNil,
							{
								loopLine=TimeIndicator.new1(parent,x_down_f_pos,0,5,this.yEndPointOfDownLine,Color.blue);
								loopLine.addTimeLineEvents(100);
							},
							{
								loopLine.changeXLocation(x_down_f_pos);
						});
					}

				);
			};
		});

	}

	addTimeLine
	{

		if(timeIndicator.isNil,{

		SynthDef.new("example5", {arg out = 0, freq = 440, amp = 0.1,
			dur = 1;

			var saw, env_gen, env,pan;

			env = Env.triangle(dur, amp);
			env_gen = EnvGen.kr(env, doneAction:2);
			saw = Saw.ar(freq, env_gen);
			pan=Pan2.ar(saw,0);
			Out.ar(out, pan);
		}).load(s);

		timeIndicator=TimeIndicator.new1(parent,10,this.writingspace,4,this.yEndPointOfDownLine,Color.red);

		timeIndicator.addTimeLineEvents(0);
		});
	}

}