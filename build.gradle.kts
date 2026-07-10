// twitter4j-v2 私家版 - KMP移行 Phase 6
// バージョン方針:
//   - KMP系列は 2.0 系とする（既存の 1.4.4 を mavenLocal で上書きしないため）
//   - 2.0.0-SNAPSHOT を採用
//   - artifact 座標は従来どおり io.github.takke:jp.takke.twitter4j-v2 を維持する
plugins {
    // KMPモジュール（twitter4j-v2-support）で使用。ルートでは apply しない
    kotlin("multiplatform") version "2.2.21" apply false
}

// 共通のグループ/バージョン定義
// ⚠️ 本体（twitter4j-core）と同方式。subprojects への java プラグイン一括適用は KMP モジュールと
//    衝突するため行わず、プラグイン適用・publish 設定は各モジュール側 build.gradle.kts に寄せる。
allprojects {
    group = "io.github.takke"
    version = "2.0.0-SNAPSHOT"

    repositories {
        // twitter4j-core 4.1.0-SNAPSHOT を mavenLocal から解決する
        mavenLocal()
        google()
        mavenCentral()
    }
}
