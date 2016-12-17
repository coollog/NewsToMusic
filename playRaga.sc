/*-----------------------------------------------------------------------
Algorithmic Computer Music Final


-----------------------------------------------------------------------*/

// Check port of OSC server
NetAddr.langPort;

// Run this
(
var playRaga;
b = Buffer.read(s, thisProcess.nowExecutingPath.dirname +/+"TablaLowLongHitG.wav");

p = 0;

playRaga = {

	//get score, assign to global
	arg score;
	~score = score;
	~strength=~score%5;

	//initialize raga arrays
	~raga;
	~backraga;

	/*Arrays of Raga patterns fomat:

	RagaTitle = [intro, main pattern, outro]
	Index of raga corresponds to pattern used:
	intro = RagaTitle[0]
	main pattern = RagaTitle[1]
	outro = RagaTitle[2]

	*/


	//0-10
	~ragaShivranjani = [[0,2,3,7,9,11], [2,3,7,9,7,3,2,3,0,2,-3,0], [12,9,7,3,2,0]];

	//11-20
	~ragaMianKiTodi = [[0,1,3,6,7,8,11,12], [8,8,7,6,3,1,0], [12,11,8,7,6,3,1,3,0]];

	//21-30
	~ragaShree = [[0,1,5,7,11,12], [0,1,1,7,7,5,4,1,1,1,0], [12,11,8,7,5,4,1,0]];

	//31-40
	~ragaBairagiBhairav = [[0,1,5,7,10,12], [-2,1,5,7,10,7,10,10,5,1,-2,1,0], [12,10,7,5,1,0]];

	//41-50
	~ragaMarwa = [[0,1,4,6,9,11,9,12], [9,6,4,1,4,6,4,1,0], [12,13,11,9,6,4,1,0]];

	//51-60
	~ragaYaman = [[0,2,4,6,7,9,11,12], [-1,2,4,2,0,7,6,4,2,0], [12,11,9,7,6,4,2,0]];

	//61-70
	~ragaGaudSarang = [[-1,0,4,2,5,4,7,5,9,7,12], [0,4,2,5,4,7,2,0], [12,11,9,7,6,7,5,4,2,4,2,5,4,7,2,0]];

	//71-80
	~ragaBihaag = [[-1,0,2,5,7,11,12], [7,11,9,7,6,7,4,5,4,2,0], [12,11,9,7,6,7,4,5,4,2,0]];

	//81-90
	~ragaTilakKamod = [[-5,-1,0,2,4,0,2,5,7,11,12], [-5,-1,0,2,4,0,2,7,5,9,12,11], [12,7,9,5,4,0,2,4,0,-1,-5,-1,0,2,4,0]];

	//91-100
	~ragaDes = [[0,2,5,7,11,12], [2,2,5,7,10,9,7,5,4,2,4,-1,0], [12,10,9,7,5,4,2,4,0]];


	//case statement to assign ragas
	(
		x = case
		{ (0 <= ~score) && (~score <= 10) }   { ~raga = ~ragaShivranjani; ~backraga = ~ragaMianKiTodi; ~strength=~score/2  }
		{ (11 <= ~score) && (~score <= 15) }   { ~raga = ~ragaMianKiTodi; ~backraga = ~ragaShivranjani }
		{ (16 <= ~score) && (~score <= 20) }   { ~raga = ~ragaMianKiTodi; ~backraga = ~ragaShree }
		{ (21 <= ~score) && (~score <= 25) }   { ~raga = ~ragaShree; ~backraga =~ragaMianKiTodi  }
		{ (25 <= ~score) && (~score <= 30) }   { ~raga = ~ragaShree; ~backraga = ~ragaBairagiBhairav }
		{ (31 <= ~score) && (~score <= 35) }   { ~raga = ~ragaBairagiBhairav; ~backraga =~ragaShree  }
		{ (36 <= ~score) && (~score <= 40) }   { ~raga = ~ragaBairagiBhairav; ~backraga =~ragaMarwa  }
		{ (41 <= ~score) && (~score <= 45) }   { ~raga = ~ragaMarwa; ~backraga = ~ragaBairagiBhairav }
		{ (46 <= ~score) && (~score <= 50) }   { ~raga = ~ragaMarwa; ~backraga =~ragaYaman  }
		{ (51 <= ~score) && (~score <= 55) }   { ~raga = ~ragaYaman; ~backraga = ~ragaMarwa }
		{ (56 <= ~score) && (~score <= 60) }   { ~raga = ~ragaYaman; ~backraga =  ~ragaGaudSarang}
		{ (61 <= ~score) && (~score <= 65) }   { ~raga = ~ragaGaudSarang; ~backraga =~ragaYaman  }
		{ (66 <= ~score) && (~score <= 70) }   { ~raga = ~ragaGaudSarang; ~backraga = ~ragaBihaag }
		{ (71 <= ~score) && (~score <= 75) }   { ~raga = ~ragaBihaag; ~backraga = ~ragaGaudSarang }
		{ (76 <= ~score) && (~score <= 80) }   { ~raga = ~ragaBihaag; ~backraga = ~ragaTilakKamod }
		{ (81 <= ~score) && (~score <= 85) }   { ~raga = ~ragaTilakKamod; ~backraga = ~ragaBihaag }
		{ (86 <= ~score) && (~score <= 90) }   { ~raga = ~ragaTilakKamod; ~backraga = ~ragaDes }
		{ (91 <= ~score) && (~score <= 100) }   { ~raga = ~ragaDes; ~backraga =  ~ragaTilakKamod; ~strength=(~score-90)/2};

		~raga.postln;
		~backraga.postln;
	);


	//Convert pitch classes in above arrays to interval ratios
	e = Scale.chromatic;

	~intervalRatios = Dictionary.newFrom(List[
		-5, 1 - (e.ratios[5] - 1),
		-4, 1 - (e.ratios[4] - 1),
		-3, 1 - (e.ratios[3] - 1),
		-2, 1 - (e.ratios[2] - 1),
		-1, 1 - (e.ratios[1] - 1),
		0, e.ratios[0],
		1, e.ratios[1],
		2, e.ratios[2],
		3, e.ratios[3],
		4, e.ratios[4],
		5, e.ratios[5],
		6, e.ratios[6],
		7, e.ratios[7],
		8, e.ratios[8],
		9, e.ratios[9],
		10, e.ratios[10],
		11, e.ratios[11],
		12, [2],
		13, 1 + e.ratios[1]
	]);

	~convertedraga = List[];
	~convertedbackraga = List[];

	for(0, 2,
		{arg i;
			var cr = List[1];
			~raga[i].do({|j|
				cr.add(~intervalRatios[j])
			});
			~convertedraga.add(cr);
		}
	);


	for(0, 2,
		{arg i;
			var cbr = List[1];
			~backraga[i].do({|j|
				cbr.add(~intervalRatios[j])
			});
			~convertedbackraga.add(cbr);
		}
	);

	//final note arrays for ragas to be played below
	~convertedraga.postln;
	~convertedbackraga.postln;

	//-----------------------------------------------------------------------
	//¡Generate some grainz!
	//-----------------------------------------------------------------------

	//A routine with 4 synthdefs (1 for intro, 2 for main, 1 for outro)

	//Speed of playback of sample is varied with the trate variable
	//Fades accomplished with the XOut Ugen

	p = 1;
	(
		r = Routine{
			SynthDef(\grainy0,{ arg out=0, buffer=0, fade=1;
				var signal, trate, dur, rate;
				trate = XLine.kr(1/20* BufDur.kr(buffer),250, 30);
				rate=Dseq(~convertedraga[0].asArray,inf);
				dur = BufDur.kr(buffer);
				signal = 	TGrains.ar(2, Impulse.kr(trate), buffer, Dseq(~convertedraga[0].asArray,inf), dur/2, dur,0, 0.7, 4);
				XOut.ar(0, fade, signal)
			}).send(s);
			SynthDef(\grainy1,{ arg out=0, buffer=0;
				var signal, trate, dur, rate,env;
				env = Env.new([250, 250, 100, 250, 250, 30, 250, 500, 500, 250], [5, 1.5, 1.5, 4, 15, 15, 2, 5, 10], [\hold, \lin, \lin, \hold,\exp,\exp, \wel, \hold, \cubed]);
				trate = EnvGen.kr(env);
				rate=Dseq(~convertedraga[1].asArray,inf);
				dur = BufDur.kr(buffer);
				signal = 	TGrains.ar(2, Impulse.kr(trate), buffer, Dseq(~convertedraga[1].asArray,inf), dur/2, dur,0, 0.7, 4);
				XOut.ar(0, Line.kr(0, 1, 7), signal)
			}).send(s);
			SynthDef(\backgrainy1,{ arg out=0, buffer=0;
				var signal, trate, dur, rate, env;
				env = Env.new([0.01, 100*~strength, 0.01],[21, 21],\wel);
				trate = EnvGen.kr(env);
				dur = BufDur.kr(buffer);
				signal = 	TGrains.ar(2, Impulse.kr(trate), buffer, Dseq(~convertedbackraga[1].asArray,inf), (2*dur)/3, dur,0, 0.7, 4);
				Out.ar(0, signal)
			}).send(s);
			SynthDef(\grainy2,{ arg out=0, buffer=0;
				var signal, trate, dur, rate;
				trate = Line.kr(250,1/20* BufDur.kr(buffer), 35);
				rate=Dseq(~convertedraga[0].asArray,inf);
				dur = BufDur.kr(buffer);
				signal = 	TGrains.ar(2, Impulse.kr(trate), buffer, Dseq(~convertedraga[2].asArray,inf), dur/2, dur,0, 0.7, 4);
				XOut.ar(0,Line.kr(0, 1, 7) , signal)
			}).send(s);

			g = Synth(\grainy0, [\buffer, b.bufnum, \fade, 1]);
			30.wait;
			u = Synth.tail(s,\grainy1, [\buffer, b.bufnum]);
			7.wait;
			g.free;
			q=Synth(\backgrainy1, [\buffer, b.bufnum]);
			10.wait;
			10.wait;
			w = Synth.tail(s,\grainy2, [\buffer, b.bufnum,]);
			15.wait;
			u.free;
			q.free;
			20.wait;
			w.free;
		}.play
	)


};


o = OSCFunc({
	arg msg, time, addr, recvPort;
	var score = msg[1].asInteger;
	('Score: ' ++ score).postln;

	if(p==1,r.stop);
	playRaga.value(score);
}, '/score');
);



// Free the OSCFunc before making a new one
o.free;