from flask import Flask, request, jsonify

from GradeNews import GradeNews

app = Flask(__name__, static_folder='static', static_url_path='')

def sendScoreToSC(score):
  import OSC
  c = OSC.OSCClient()
  c.connect(('127.0.0.1', 57120))   # connect to SuperCollider
  oscmsg = OSC.OSCMessage()
  oscmsg.setAddress('/score')
  oscmsg.append(score)
  c.send(oscmsg)

@app.route('/')
def index():
  return app.send_static_file('chooseSources.html')

@app.route('/score', methods=['post'])
def score():
  sources = request.form.getlist('sources[]')

  grader = GradeNews(sources)
  score = grader.gradeNewsBySource()

  res = {'score': score}

  sendScoreToSC(score)

  return jsonify(res)

if __name__ == '__main__':
  app.run(debug=True)