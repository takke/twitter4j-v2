rootProject.name = "twitter4j-v2"

include(":twitter4j-v2-support")

// KMP移行 Phase 6: サンプルモジュール（kotlin/java example）は KMP 化の対象外。
// 旧 Groovy ビルドでは subprojects へ kotlin-jvm を一括適用してビルド対象にしていたが、
// KMP 構成では本体ライブラリのみをビルド対象とする（サンプルのソースは現状維持で残す）。
//include(":twitter4j-v2-support-kotlin-example")
//include(":twitter4j-v2-support-java-example")
