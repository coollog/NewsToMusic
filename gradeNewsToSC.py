from GradeNews import GradeNews

grader = GradeNews()
score = grader.gradeNewsBySource()

import OSC
c = OSC.OSCClient()
c.connect(('127.0.0.1', 57120))   # connect to SuperCollider
oscmsg = OSC.OSCMessage()
oscmsg.setAddress('/score')
oscmsg.append(score)
c.send(oscmsg)