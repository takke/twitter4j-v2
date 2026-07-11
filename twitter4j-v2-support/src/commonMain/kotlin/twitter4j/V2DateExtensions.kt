@file:OptIn(ExperimentalTime::class)

package twitter4j

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/*
 * v2 モデルの Date プロパティを [kotlin.time.Instant] として取得する拡張群。
 *
 * 本体（twitter4j-core）の DateExtensions.kt と同方針:
 * 消費側をこれらの拡張へ寄せきってから、将来的に本体プロパティを Instant へ差し替える
 * （Date→Instant完全統一のPhase A受け皿）。
 */

/** [Poll.endDatetime] を [Instant] として取得する（endDatetime が null の場合は null）。 */
val Poll.endDatetimeAsInstant: Instant?
    // 注: 本体の `Date.toInstant()` 拡張は使わない。ライブラリ境界を跨ぐ JVM コンパイルでは
    // java.util.Date のメンバ toInstant()（java.time.Instant を返す）が優先されて型が合わないため、
    // 全ターゲットで解決可能な getTime()（epoch millis）経由で生成する。
    get() = endDatetime?.let { Instant.fromEpochMilliseconds(it.getTime()) }
