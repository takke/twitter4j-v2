// twitter4j-v2 私家版 - KMP移行 Phase 6
// バージョン方針:
//   - KMP系列は 2.0 系とする（既存の 1.4.4 を mavenLocal で上書きしないため）
//   - takke.github.io/maven へ公開するため SNAPSHOT ではなく日付付き固定版 (2.0.0-YYYYMMDD) を採用
//     （本体 twitter4j-core の 4.1.0-YYYYMMDD とセットで公開する。日付は本体と揃える）
//     公開済みバージョンは Gradle が永久キャッシュするため、再公開時は必ず日付を上げること
//   - 公開手順は ../publish_all_libs_to_github_io.sh を参照
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
    version = "2.0.0-20260908"

    repositories {
        // twitter4j-core を mavenLocal (publish 直後) または takke.github.io/maven (公開済み) から解決する
        mavenLocal()
        maven { url = uri("https://takke.github.io/maven") }
        google()
        mavenCentral()
    }
}
