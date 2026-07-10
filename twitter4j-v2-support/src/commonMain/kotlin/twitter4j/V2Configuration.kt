package twitter4j

import twitter4j.conf.Configuration

data class V2Configuration(var baseURL: String = "https://api.twitter.com/2/")

internal object V2ConfigurationContainer {
    // KMP移行 Phase 6: ConcurrentHashMap → newV2ConcurrentMap()（JVM は ConcurrentHashMap、iOS は LinkedHashMap）
    val v2ConfigurationMap = newV2ConcurrentMap<Configuration, V2Configuration>()
}

val Configuration.v2Configuration: V2Configuration
    get() {
        val map = V2ConfigurationContainer.v2ConfigurationMap
        return if (map.containsKey(this)) {
            map[this]!!
        } else {
            val v2Configuration = V2Configuration()
            // 旧実装は putIfAbsent だが、いずれにせよ生成したローカルインスタンスを返す挙動は不変。
            map[this] = v2Configuration
            v2Configuration
        }
    }