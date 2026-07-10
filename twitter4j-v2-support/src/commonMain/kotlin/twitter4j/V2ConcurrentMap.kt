package twitter4j

/**
 * KMP移行 Phase 6: [V2Configuration] のキャッシュに使われていた
 * `java.util.concurrent.ConcurrentHashMap` を common 化するためのファクトリ。
 *
 * 本体 twitter4j-core にも同等の `newConcurrentMap` があるが `internal` で別モジュールから
 * 参照できないため、v2 内に同等の仕組みを複製する。
 *
 * - JVM(Android): `ConcurrentHashMap` を返し、マップ構造自体のスレッドセーフ性を維持する。
 * - iOS(Native): 通常の [LinkedHashMap] を返す。当該キャッシュは同一 Configuration に対して
 *   決定的な値を格納する冪等キャッシュであり、競合しても再計算・上書きされるだけで害はない。
 */
internal expect fun <K : Any, V : Any> newV2ConcurrentMap(): MutableMap<K, V>
