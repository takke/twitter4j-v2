package twitter4j

// iOS(Native) には ConcurrentHashMap が無いため通常の LinkedHashMap を用いる。
// このキャッシュは冪等（同一 Configuration に対し決定的な V2Configuration を格納）であり、
// 競合しても再計算・上書きされるだけで問題ない。
internal actual fun <K : Any, V : Any> newV2ConcurrentMap(): MutableMap<K, V> = LinkedHashMap()
