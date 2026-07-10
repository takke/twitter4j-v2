package twitter4j

import java.util.concurrent.ConcurrentHashMap

// JVM では従来どおり ConcurrentHashMap を用いる（マップ構造自体のスレッドセーフ性を維持）。
internal actual fun <K : Any, V : Any> newV2ConcurrentMap(): MutableMap<K, V> = ConcurrentHashMap()
