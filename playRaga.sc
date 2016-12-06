/*-----------------------------------------------------------------------
Algorithmic Computer Music Final


-----------------------------------------------------------------------*/

//Get Raw Score


NetAddr.langPort;

(
var playRaga;

p = 0;

playRaga = {
	arg score;

	~score = score;

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

	x = case
	{ (0 <= ~score) && (~score <= 10) }   { ~raga = ~ragaShivranjani }
	{ (11 <= ~score) && (~score <= 20) }   { ~raga = ~ragaMianKiTodi }
	{ (21 <= ~score) && (~score <= 30) }   { ~raga = ~ragaShree }
	{ (31 <= ~score) && (~score <= 40) }   { ~raga = ~ragaBairagiBhairav }
	{ (41 <= ~score) && (~score <= 50) }   { ~raga = ~ragaMarwa }
	{ (51 <= ~score) && (~score <= 60) }   { ~raga = ~ragaYaman }
	{ (61 <= ~score) && (~score <= 70) }   { ~raga = ~ragaGaudSarang }
	{ (71 <= ~score) && (~score <= 80) }   { ~raga = ~ragaBihaag }
	{ (81 <= ~score) && (~score <= 90) }   { ~raga = ~ragaTilakKamod }
	{ (91 <= ~score) && (~score <= 100) }   { ~raga = ~ragaDes };

	~raga.postln;

	SynthDef(\sinegrain, {arg pan, freq, amp; var grain;

		grain= SinOsc.ar(freq, 0, amp)*(XLine.kr(1.001,0.001,0.1,doneAction:2)-0.001);

		Out.ar(0,Pan2.ar(grain, pan))}).add;

	p = 1;
	r = Routine {


		1000.do{arg i;
			var note;
			var size;
			var timeprop = (1/((i))**1.2);

			size = ~raga[0].size;
			note = ~raga[0][i%size];
			note = note +70;
			note = note.midicps;

			Synth(\sinegrain,[\freq,note,\amp, exprand(0.05,0.1), \pan, 1.0.rand2]);
			//0.01.wait

			rrand((timeprop*99).min(0.1), (timeprop*100).min(0.1)).wait;

			//XLine.kr(1, 0.01, 10).wait;

			//rrand((timeprop*0.4).max(1), timeprop*0.1).wait
		};


		//Music for Main section


		500.do{arg i;
			var note;
			var size;
			var timeprop = (1/(sqrt(i)));


			size = ~raga[1].size;
			note = ~raga[1][i%size];
			note = note + 70;
			note = note.midicps;

			Synth(\sinegrain,[\freq,note,\amp, exprand(0.05,0.1), \pan, 1.0.rand2]);
			0.01.wait
			//rrand(0.005, (timeprop*0.9).min(0.1)).wait;

			//XLine.kr(1, 0.01, 10).wait;

			//rrand((timeprop*0.4).max(1), timeprop*0.1).wait
		};





		200.do{arg i;
			var note;
			var size;
			var timeprop = (i/199.0)**3;

			size = ~raga[2].size;
			note = ~raga[2][i%size];
			note = note + 70;
			note = note.midicps;

			Synth(\sinegrain,[\freq,note,\amp, exprand(0.05,0.1), \pan, 1.0.rand2]);

			//0.01.wait
			//rrand(0.005, (timeprop*0.9).min(0.1)).wait;
			//XLine.kr(1, 0.01, 10).wait;

			rrand((timeprop*0.1).max(0.01),timeprop*0.4).wait
		};



	}.play;
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