@file:OptIn(ExperimentalTime::class)

package twitter4j

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

object V2Util {

    fun collectPolls(includes: JSONObject?, pollsMap: HashMap<Long, Poll>) {

        includes?.optJSONArray("polls")?.let { polls ->
            for (i in 0 until polls.length()) {
                val pollString = polls.getString(i)

                val poll = Poll(JSONObject(pollString)).also {
                    // original json
                    it.jsonText = pollString
                }

                pollsMap[poll.id] = poll
            }
        }
    }

    fun collectMediaKeys(includes: JSONObject?, mediaMap: HashMap<MediaKey, Media>) {
        includes?.optJSONArray("media")?.let { mediaArray ->
            for (i in 0 until mediaArray.length()) {
                val mediaString = mediaArray.getString(i)

                val media = Media.parse(JSONObject(mediaString)).also {
                    // original json
                    // TODO implement if you need
//                    it.jsonText = mediaString
                }

                mediaMap[media.mediaKey] = media
            }
        }
    }

    fun collectUsers(includes: JSONObject?, usersMap: HashMap<Long, User2>) {

        includes?.optJSONArray("users")?.let { users ->
            for (i in 0 until users.length()) {
                val user = User2.parse(users.getJSONObject(i))
                usersMap[user.id] = user
            }
        }
    }

    fun collectPlaces(includes: JSONObject?, placesMap: HashMap<String, Place2>) {
        includes?.optJSONArray("places")?.let { places ->
            for (i in 0 until places.length()) {
                val place = Place2.parse(places.getJSONObject(i))
                placesMap[place.id] = place
            }
        }
    }

    fun collectTweets(includes: JSONObject?, tweetsMap: HashMap<Long, Tweet>) {

        includes?.optJSONArray("tweets")?.let { tweets ->
            for (i in 0 until tweets.length()) {
                val tweet = Tweet.parse(tweets.getJSONObject(i))
                tweetsMap[tweet.id] = tweet
            }
        }
    }

    fun collectErrors(jsonObject: JSONObject, errors: List<ErrorInfo>) {
        val errorsArray = jsonObject.optJSONArray("errors")
        if (errorsArray != null) {
            val errors1 = errors as MutableList
            for (i in 0 until errorsArray.length()) {
                errors1.add(ErrorInfo(errorsArray.getJSONObject(i)))
            }
        }
    }

    fun parseIds(array: JSONArray?, listToStore: List<Long>?) {
        if (array != null) {
            val list = listToStore as MutableList
            for (v in 0 until array.length()) {
                list.add(array.getLong(v))
            }
        }
    }

    fun parseMeta(jsonObject: JSONObject): Meta? {
        if (jsonObject.has("meta")) {
            // KMP移行 Phase 6: core の optJSONObject は JSONObject? を返すようになったため !!（has チェック済みで非null前提）
            val metaObject = jsonObject.optJSONObject("meta")!!
            return Meta(
                metaObject.getInt("result_count"),
                metaObject.optString("previous_token", null)?.let { PaginationToken(it) },
                metaObject.optString("next_token", null)?.let { PaginationToken(it) },
                metaObject.optLongOrNull("oldest_id"),
                metaObject.optLongOrNull("newest_id")
            )
        }
        return null
    }

    fun dateToISO8601(date: Instant?): String? {
        date ?: return null
        // 旧実装: SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'") + GMT。
        // KMP(commonMain/iOS)では SimpleDateFormat が使えないため kotlinx-datetime で同一フォーマットを組み立てる。
        // 注: Instant.toString() は入力にミリ秒があると "...SSS Z" を出力してしまうため、
        // 旧出力（秒精度・ミリ秒なし）と互換になるよう手組みで秒までを整形する。
        val dt = date.toLocalDateTime(TimeZone.UTC)
        fun Int.pad(len: Int) = toString().padStart(len, '0')
        return "${dt.year.pad(4)}-${dt.monthNumber.pad(2)}-${dt.dayOfMonth.pad(2)}" +
                "T${dt.hour.pad(2)}:${dt.minute.pad(2)}:${dt.second.pad(2)}Z"
    }

    fun parseISO8601Date(key: String, data: JSONObject?): Instant? {
        // 旧実装は ParseUtil.getDate(..., "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") でパースしていた。
        // kotlin.time.Instant.parse は ISO-8601 のミリ秒あり/なし双方を解釈できるため、これで置き換える。
        val dateStr = data?.optString(key, null)
        return if (dateStr == null || dateStr == "null") {
            null
        } else {
            Instant.parse(dateStr)
        }
    }

    fun addHttpParamIfNotNull(params: ArrayList<HttpParameter>, name: String, value: String?) {
        if (value != null) {
            params.add(HttpParameter(name, value))
        }
    }

    fun addHttpParamIfNotNull(params: ArrayList<HttpParameter>, name: String, token: PaginationToken?) {
        if (token != null) {
            params.add(HttpParameter(name, token.value))
        }
    }

    fun addHttpParamIfNotNull(params: ArrayList<HttpParameter>, name: String, value: Int?) {
        if (value != null) {
            params.add(HttpParameter(name, value))
        }
    }

    fun addHttpParamIfNotNull(params: ArrayList<HttpParameter>, name: String, value: Long?) {
        if (value != null) {
            params.add(HttpParameter(name, value))
        }
    }

}
