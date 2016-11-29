NetAddr.langPort;

(
o = OSCFunc({
	arg msg, time, addr, recvPort;
	var score = msg[1];
	('Score: ' ++ score).postln;
}, '/score');

);

// Free the OSCFunc before making a new one
o.free;