import tweepy
import json
import pycouchdb
import time

auth = tweepy.OAuthHandler("lnpDYWDIWShVKmEez297bOfec", "0eYwwH3IRU8qkt5IceBrvQF0aHUaPdpEfNE7ldkEJezzgVE1ry")
auth.set_access_token("974163114433306624-fSrCQPL7HCM33RxE76V2dvFmsuQ1v5n", "i2eBRy83UvrFqhEbIWQ2u29L2ivD2TSVMJYgubPJXEhNR")

api = tweepy.API(auth)

# public_tweets = api.home_timeline()
# for tweet in public_tweets:
#     print(tweet.text)
server = pycouchdb.Server("http://admin:1q2w3e4r@localhost:5984/")
#print(server.info())
db = server.database("ccctest")
time.sleep(5)
print(db)
result = api.search(geocode = '-37.84,144.94,15mi',count=1, result_type = 'recent')
for item in result:
	data = item._json
	if data["id_str"] not in db:
		db.save({
			"_id": data["id_str"],
			"user": {
				"name": data["user"]["name"],
				"friends_count": data["user"]["friends_count"]
			},
			"text": data["text"],
			"location":data["user"]["location"],
			"created_at": data["created_at"]
		})