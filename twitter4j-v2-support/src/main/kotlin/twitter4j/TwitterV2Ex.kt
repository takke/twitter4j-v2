package twitter4j

fun Twitter.v2(): TwitterV2 {
    return TwitterV2Impl(this)
}
