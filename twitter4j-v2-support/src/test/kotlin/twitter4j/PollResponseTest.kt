package twitter4j

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PollResponseTest {

    @Test
    fun whenPollIsOpen() {
        val res = TweetsResponse(
            JSONObject(
                "{\"data\":[{\"attachments\":{\"poll_ids\":[\"1410\"]},\"text\":\"Winged Hussars\",\"author_id\":\"273\",\"id\":\"1190\"}],\"includes\":{\"polls\":[{\"options\":[{\"position\":1,\"label\":\"Poland\",\"votes\":1},{\"position\":2,\"label\":\"Germany\",\"votes\":0}],\"duration_minutes\":1410,\"id\":\"1410\",\"voting_status\":\"open\",\"end_datetime\":\"1410-07-15T18:00:00.000Z\"}],\"users\":[{\"id\":\"273\",\"name\":\"Władysław Jagiełło\",\"username\":\"King Władysław Jagiełło\"}]},\"meta\":{\"newest_id\":\"1190\",\"oldest_id\":\"1190\",\"result_count\":1}}"

            )
        )
        assertThat(res.pollsMap.size).isEqualTo(1)
        assertThat(res.pollsMap[1410]?.id).isEqualTo(1410)
        assertThat(res.pollsMap[1410]?.options?.size).isEqualTo(2)
        assertThat(res.pollsMap[1410]?.votingStatus).isEqualTo(Poll.VotingStatus.OPEN)
        assertThat(res.pollsMap[1410]?.endDatetime).isEqualTo("1410-07-15T18:00:00.000Z")
        assertThat(res.pollsMap[1410]?.durationMinutes).isEqualTo(1410)

        assertThat(res.pollsMap[0]?.votingStatus).isNull()
        assertThat(res.pollsMap[0]?.endDatetime).isNull()
        assertThat(res.pollsMap[0]?.durationMinutes).isNull()
    }

    @Test
    fun whenPollIsOpenWithoutData() {
        val res = TweetsResponse(
            JSONObject(
                "{\"data\":[{\"attachments\":{\"poll_ids\":[\"1410\"]},\"text\":\"Winged Hussars\",\"author_id\":\"273\",\"id\":\"1190\"}],\"includes\":{\"polls\":[{\"options\":[{\"position\":1,\"label\":\"Poland\",\"votes\":1},{\"position\":2,\"label\":\"Germany\",\"votes\":0}],\"duration_minutes\":1410,\"id\":\"1410\",\"voting_status\":\"open\"}],\"users\":[{\"id\":\"273\",\"name\":\"Władysław Jagiełło\",\"username\":\"King Władysław Jagiełło\"}]},\"meta\":{\"newest_id\":\"1190\",\"oldest_id\":\"1190\",\"result_count\":1}}"

            )
        )
        assertThat(res.pollsMap.size).isEqualTo(1)
        assertThat(res.pollsMap[1410]?.id).isEqualTo(1410)
        assertThat(res.pollsMap[1410]?.options?.size).isEqualTo(2)
        assertThat(res.pollsMap[1410]?.votingStatus).isEqualTo(Poll.VotingStatus.OPEN)
        assertThat(res.pollsMap[1410]?.endDatetime).isNull()
        assertThat(res.pollsMap[1410]?.durationMinutes).isEqualTo(1410)

        assertThat(res.pollsMap[0]?.votingStatus).isNull()
        assertThat(res.pollsMap[0]?.endDatetime).isNull()
        assertThat(res.pollsMap[0]?.durationMinutes).isNull()

    }

    @Test
    fun whenPollIsOpenWithoutVotingStatus() {
        val res = TweetsResponse(
            JSONObject(
                "{\"data\":[{\"attachments\":{\"poll_ids\":[\"1410\"]},\"text\":\"Winged Hussars\",\"author_id\":\"273\",\"id\":\"1190\"}],\"includes\":{\"polls\":[{\"options\":[{\"position\":1,\"label\":\"Poland\",\"votes\":1},{\"position\":2,\"label\":\"Germany\",\"votes\":0}],\"duration_minutes\":1410,\"id\":\"1410\",\"end_datetime\":\"1410-07-15T18:00:00.000Z\"}],\"users\":[{\"id\":\"273\",\"name\":\"Władysław Jagiełło\",\"username\":\"King Władysław Jagiełło\"}]},\"meta\":{\"newest_id\":\"1190\",\"oldest_id\":\"1190\",\"result_count\":1}}"

            )
        )
        assertThat(res.pollsMap.size).isEqualTo(1)
        assertThat(res.pollsMap[1410]?.id).isEqualTo(1410)
        assertThat(res.pollsMap[1410]?.options?.size).isEqualTo(2)
        assertThat(res.pollsMap[1410]?.votingStatus).isNull()
        assertThat(res.pollsMap[1410]?.endDatetime).isEqualTo("1410-07-15T18:00:00.000Z")
        assertThat(res.pollsMap[1410]?.durationMinutes).isEqualTo(1410)

        assertThat(res.pollsMap[0]?.votingStatus).isNull()
        assertThat(res.pollsMap[0]?.endDatetime).isNull()
        assertThat(res.pollsMap[0]?.durationMinutes).isNull()
    }

    @Test
    fun whenPollIsClose() {
        val res = TweetsResponse(
            JSONObject(
                "{\"data\":[{\"id\":\"1190\",\"author_id\":\"273\",\"text\":\"Winged Hussars\",\"attachments\":{\"poll_ids\":[\"1410\"]}}],\"includes\":{\"polls\":[{\"id\":\"1410\",\"options\":[{\"position\":1,\"label\":\"Poland\",\"votes\":1},{\"position\":2,\"label\":\"Germany\",\"votes\":0}],\"voting_status\":\"closed\",\"end_datetime\":\"1410-07-15T18:00:00.000Z\",\"duration_minutes\":5}],\"users\":[{\"id\":\"273\",\"name\":\"Władysław Jagiełło\",\"username\":\"King Władysław Jagiełło\"}]},\"meta\":{\"newest_id\":\"1939\",\"oldest_id\":\"1939\",\"result_count\":1}}"
            )
        )
        assertThat(res.pollsMap.size).isEqualTo(1)
        assertThat(res.pollsMap[1410]?.id).isEqualTo(1410)
        assertThat(res.pollsMap[1410]?.options?.size).isEqualTo(2)
        assertThat(res.pollsMap[1410]?.votingStatus).isEqualTo(Poll.VotingStatus.CLOSED)
        assertThat(res.pollsMap[1410]?.endDatetime).isEqualTo("1410-07-15T18:00:00.000Z")
        assertThat(res.pollsMap[1410]?.durationMinutes).isEqualTo(5)

        assertThat(res.pollsMap[0]?.votingStatus).isNull()
        assertThat(res.pollsMap[0]?.endDatetime).isNull()
        assertThat(res.pollsMap[0]?.durationMinutes).isNull()
    }

    @Test
    fun whenPollIsEmpty() {
        val res = TweetsResponse(
            JSONObject("{\"data\":[{\"author_id\":\"273\",\"id\":\"1190\",\"text\":\"Winged Hussars\"}],\"includes\":{\"users\":[{\"id\":\"273\",\"name\":\"Władysław Jagiełło\",\"username\":\"King Władysław Jagiełło\"}]},\"meta\":{\"newest_id\":\"1190\",\"oldest_id\":\"1190\",\"result_count\":1}}")
        )
        assertThat(res.pollsMap.size).isEqualTo(0)
        assertThat(res.pollsMap[0]?.options).isNull()
        assertThat(res.pollsMap[0]?.votingStatus).isNull()
        assertThat(res.pollsMap[0]?.endDatetime).isNull()
        assertThat(res.pollsMap[0]?.durationMinutes).isNull()
    }
}