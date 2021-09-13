package twitter4j

import twitter4j.conf.Configuration
import java.util.concurrent.ConcurrentHashMap

data class V2Configuration(var baseURL: String = "https://api.twitter.com/2/")

internal object V2ConfigurationContainer {
    val v2ConfigurationMap = ConcurrentHashMap<Configuration, V2Configuration>()
}

val Configuration.v2Configuration: V2Configuration
    get() {
        return if (V2ConfigurationContainer.v2ConfigurationMap.containsKey(this)) {
            V2ConfigurationContainer.v2ConfigurationMap[this]!!
        } else {
            val v2Configuration = V2Configuration()
            V2ConfigurationContainer.v2ConfigurationMap.putIfAbsent(this, v2Configuration)
            v2Configuration
        }
    }