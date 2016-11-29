NEWS_API_KEY = 'd0670c8293a4460997eff0019b492536'

import requests, csv

class GradeNews:
  wordScores = {}
  sources = {
    'associated-press': 6,
    'bloomberg': 5,
    'cnn': 6,
    'fortune': 4,
    'google-news': 15,
    'the-new-york-times': 9,
    'the-wall-street-journal': 7,
    'the-washington-post': 7,
    'usa-today': 7
  }

  def __init__(self):
    self.initWithHedonometer()

  def initWithHedonometer(self):
    with open('words.csv', 'rb') as f:
      reader = csv.reader(f)
      for row in reader:
        word = row[0]
        score = row[1]
        self.wordScores[word] = float(score)
    self.initWith = 'hedonometer'

  def initWithSentiments(self):
    def addWords(filename, score):
      with open(filename, 'r') as f:
        for word in f.readlines():
          if word.startswith(';') or len(word) == 0:
            continue
          word = word.rstrip()
          self.wordScores[word] = score

    addWords('positive-words.txt', 1)
    addWords('negative-words.txt', -2)

    self.initWith = 'sentiments'

  def gradeNews(self):
    articles = self.getArticles()
    return self.gradeArticles(articles)

  def gradeNewsBySource(self):
    grades = {}
    for source in self.sources:
      articles = self.getArticlesBySource(source)
      grades[source] = self.gradeArticles(articles)
    print(grades)

    grade = 0
    total = 0
    for source in grades:
      reliability = self.sources[source]
      grade += grades[source] * reliability
      total += reliability

    return grade / total

  def gradeArticles(self, articles):
    overallScore = 0

    for article in articles:
      score = self.grade(article)
      overallScore += score
      print(round(score, 2), article)

    meanScore = overallScore / len(articles)
    scaledScore = max(0, min(100, meanScore * 20 + 50))
    return int(round(scaledScore))

  def grade(self, text):
    score = 0.0
    count = 0
    for word in text.split():
      word = word.lower()
      if word in self.wordScores:
        wordScore = self.wordScores[word]
        if self.initWith == 'hedonometer':
          if wordScore < 4 or wordScore > 6.5:
            score += wordScore
            count += 1
        elif self.initWith == 'sentiments':
          score += wordScore

    if self.initWith == 'hedonometer':
      if count == 0:
        return count
      else:
        return score / count - 6.5
    elif self.initWith == 'sentiments':
      return score

  def getArticles(self):
    articles = []

    count = 0
    for source in self.sources:
      articles += self.getArticlesBySource(source)
      count += 1
      print('Progress: %i%%' % (count * 100 / len(self.sources)))

    return articles

  def getArticlesBySource(self, source):
    try:
      url = 'https://newsapi.org/v1/articles'
      params = {
        'source': source,
        'sortBy': 'popular',
        'apiKey': NEWS_API_KEY
      }
      response = requests.get(url, params).json()

      if response['status'] == 'error':
        params['sortBy'] = 'top'
        response = requests.get(url, params).json()

      articles = response['articles']

      titles = []
      for article in articles:
        title = article['title']
        titles.append(title)

      return titles
    except:
      return getArticlesBySource(source)