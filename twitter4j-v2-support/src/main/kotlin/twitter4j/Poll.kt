package twitter4j

import java.util.*

data class Poll(
        val id: Long,
        val options: Array<PollOption>,
        val votingStatus: VotingStatus,
        val endDatetime: Date,
        val durationMinutes: Int
) {
    enum class VotingStatus {
        OPEN, CLOSED
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Poll

        if (id != other.id) return false
        if (!options.contentEquals(other.options)) return false
        if (votingStatus != other.votingStatus) return false
        if (endDatetime != other.endDatetime) return false
        if (durationMinutes != other.durationMinutes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + options.contentHashCode()
        result = 31 * result + votingStatus.hashCode()
        result = 31 * result + endDatetime.hashCode()
        result = 31 * result + durationMinutes
        return result
    }

    companion object {
        fun parse(poll: JSONObject?): Poll? {
            poll ?: return null

            val id = ParseUtil.getLong("id", poll)
            val optionsArray = ArrayList<PollOption>()
            val options = poll.getJSONArray("options")
            for (i in 0 until options.length()) {
                val o = options.getJSONObject(i)
                optionsArray.add(PollOption(
                        o.getInt("position"),
                        o.getString("label"),
                        o.getInt("votes"))
                )
            }

            val votingStatus = when (poll.getString("voting_status")) {
                "closed" -> VotingStatus.CLOSED
                else -> VotingStatus.OPEN
            }
            val endDatetime = ParseUtil.getDate("end_datetime", poll, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val durationMinutes = ParseUtil.getInt("duration_minutes", poll)

            return Poll(id, optionsArray.toTypedArray(), votingStatus, endDatetime, durationMinutes)
        }

    }
}