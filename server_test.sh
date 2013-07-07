api_key="AIzaSyBj5TgPdgib0xdhS6gZmdwHjiB9k_AH3Z4";
#reg_id="APA91bEg4wM4M6bH9icXKW9g3BRWtWK_HYV7YxcvDkFLEE2Jct_jui3SE-u9tT2yAmCrVCy5BoShm6bLMNxS-kAyqxvqk6CAxQbyt6AoQUFTO8wpEzBatActRPxYgd0O8NkLjg3-fp0PT83YCitIeTlIYbRp-A5POA"
reg_id="APA91bEo7urOF9fzFV7rZmr02HwvW0MrjqZnrLl6jeGQPUvkZG6nVD8ctess5u63GPPabuMhCvmekP5HMfGF_LpMttH91DGGJtlxeg5hKV50S17rHRXy83cQ_JZAP-cnjpZCzwaR31-iiTKWCOtxgO9ducauBQjqZw"

#06-29 13:09:39.157: I/GCMIntentService(1881): Device registered: regId = APA91bEg4wM4M6bH9icXKW9g3BRWtWK_HYV7YxcvDkFLEE2Jct_jui3SE-u9tT2yAmCrVCy5BoShm6bLMNxS-kAyqxvqk6CAxQbyt6AoQUFTO8wpEzBatActRPxYgd0O8NkLjg3-fp0PT83YCitIeTlIYbRp-A5POA

#curl --header "Authorization: key=$api_key" --header "Content-Type: application/json" https://android.googleapis.com/gcm/send -X POST -d "{\"registration_ids\":[\"APA91bH6xM9BsaowZKlGnQDG8c9_IV-y-qPTfh8PmBZyPYgiEGId0HabARg-b9rwafhHrGqomQzk9-UVUJGblCRZ9OfzlCU7seZdqnrM3Eg_9ou1PJ7OFlt2BhE2pcxrfSHB-jeqBCXPv8vFJJYzrvrkipduoDH2_A\"]}"
collapse_key="lonoti_get_location"
curl --header "Authorization: key=$api_key" --header "Content-Type: application/json" https://android.googleapis.com/gcm/send -X POST -d "{\"data\": { \"event\": \"4\"},\"registration_ids\":[\"$reg_id\"], \"collapse_key\":\"$collapse_key\"}"
#
#{ "collapse_key": "score_update",
#   "time_to_live": 108,
#   "delay_while_idle": true,
#   "data": {
#       "score": "4 x 8",
#       "time": "15:16.2342"
#   },
#   "registration_ids":["4", "8", "15", "16", "23", "42"]
#}
