package twitter4j

val Twitter.v2: TwitterV2
    get() {
        return TwitterV2Impl(this)
    }
