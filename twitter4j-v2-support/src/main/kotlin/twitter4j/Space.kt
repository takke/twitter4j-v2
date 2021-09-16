package twitter4j

import java.util.*

data class Space(
    val id: String,
    val hostIds: List<Long>? = null,
    var createdAt: Date? = null,
    var creatorId: Long? = null,
    var lang: String? = null,
    var isTicketed: Boolean? = null,
    val invitedUserIds: List<Long>? = null,
    var participantCount: Int? = null,
    var scheduledStart: Date? = null,
    val speakerIds: List<Long>? = null,
    var startedAt: Date? = null,
    val state: State,
    var title: String? = null,
    var updatedAt: Date? = null,
) {
    enum class State(val rawValue: String) {
        Live("live"),
        Scheduled("scheduled"),
        Ended("ended")
    }

    companion object {
        fun parse(data: JSONObject): Space {
            val s = Space(
                data.getString("id"),
                state = when (data.getString("state")) {
                    "live" -> State.Live
                    "scheduled" -> State.Scheduled
                    "ended" -> State.Ended
                    else -> State.Scheduled
                },
                invitedUserIds = if (data.has("invited_user_ids")) mutableListOf() else null,
                speakerIds = if (data.has("speaker_ids")) mutableListOf() else null,
                hostIds = if (data.has("host_ids")) mutableListOf() else null,
            )

            V2Util.parseIds(data.optJSONArray("invited_user_ids"), s.invitedUserIds)
            V2Util.parseIds(data.optJSONArray("speaker_ids"), s.speakerIds)
            V2Util.parseIds(data.optJSONArray("host_ids"), s.hostIds)
            s.creatorId = data.optLong("creator_id")

            s.createdAt = V2Util.parseISO8601Date("created_at", data)
            s.lang = data.optString("lang", null)
            s.participantCount = data.optInt("participant_count", -1).takeIf { it != -1 }
            s.startedAt = V2Util.parseISO8601Date("started_at", data)
            s.title = data.optString("title", null)
            s.updatedAt = V2Util.parseISO8601Date("updated_at", data)
            s.scheduledStart = V2Util.parseISO8601Date("scheduled_start", data)
            s.isTicketed = if (data.has("is_ticketed")) data.getBoolean("is_ticketed") else null

            return s
        }
    }
}