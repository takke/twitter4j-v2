// twitter4j-v2-support - KMP移行 Phase 6
// 本体（twitter4j-core）の Phase 1 と同方式で KMP 化する。
plugins {
    kotlin("multiplatform")
    `maven-publish`
}

kotlin {
    jvmToolchain(11)

    // 本体（twitter4j-core）と同方式: AGP は使わず jvm ターゲットを "android" という名前で構成する。
    jvm("android") {
        // 既存テストは JUnit4（org.junit.Test）ベースのため JUnit Platform は有効化しない。
    }

    // iOS ターゲット（TwitPane 本体の慣例に合わせ iosX64 は含めない）。
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                // 本体 twitter4j-core の共通API（JSONObject / HttpParameter / ParseUtil / Date 等）に依存する。
                api("org.twitter4j:twitter4j-core:4.1.0-SNAPSHOT")
                // dateToISO8601 の共通実装で使用（本体と同一の 0.8.0）。
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.8.0")
            }
        }
        val androidMain by getting {
        }
        // JVM(android)ユニットテスト。既存テストは JUnit4 + AssertJ ベース。
        // ネットワークが必要なテストが多く（実際の API 呼び出し）、CI/ローカルでは基本 skip 想定だが、
        // JSON パース系のオフラインテスト（PollResponseTest / UsersResponseTest 等）は実行可能。
        val androidTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-test")
                implementation("org.jetbrains.kotlin:kotlin-test-junit")
                implementation("junit:junit:4.13.2")
                implementation("org.assertj:assertj-core:3.21.0")
            }
        }
    }
}

// -------------------------------------------------------------------------
// publishing 設定（本体 twitter4j-core と同方式）
//   - KMP が自動生成する android publication の artifactId を従来の
//     "jp.takke.twitter4j-v2" にオーバーライドして publish する（座標を維持）。
//   - root(kotlinMultiplatform) publication = umbrella（twitter4j-v2-support）も publish する。
//     consumer（TwitPane）の commonMain から参照するために必要（iOS ターゲット解決用）。
//     Android 変種は available-at で従来座標 jp.takke.twitter4j-v2 を指すため既存consumerへの影響なし。
//   - group は allprojects で io.github.takke を設定済み。
// -------------------------------------------------------------------------
publishing {
    publications {
        withType<MavenPublication> {
            if (name == "android") {
                artifactId = "jp.takke.twitter4j-v2"
            }
        }
    }
}
