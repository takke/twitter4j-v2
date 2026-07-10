package twitter4j

/**
 * KMP移行 Phase 6: メディアアップロード（画像・動画のバイナリ送信）に伴う JVM(java.io) 固有処理を
 * commonMain の呼び出し側（[TwitterV2] / [TwitterV2Impl]）から隔離するための expect オブジェクト。
 *
 * common 化を妨げていたのは以下の JVM 依存であり、これらをここへ集約した:
 * - `java.io.InputStream` を持つ [HttpParameter] コンストラクタ（マルチパート添付）
 * - `InputStream.readBytes()` / `ByteArrayInputStream` / `readNBytes()` によるチャンク分割
 * - `uploadMedia(file: File)` の `Files.probeContentType` / `File.name` / `File.inputStream()`
 *
 * - JVM(Android): 従来どおり実処理を行う。
 * - iOS(Native): TwitPane の iOS 版（app_research 系）は閲覧専用でメディア投稿を行わないため、
 *   いずれも [NotImplementedError] を送出する。読み取り系 API の動作には影響しない。
 */
internal expect object V2MediaUploadSupport {

    /** `java.io.InputStream` ベースのマルチパート添付パラメータを生成する。 */
    fun mediaHttpParameter(name: String, fileName: String, media: InputStream): HttpParameter

    /** ストリームを全読み込みして [ByteArray] を返す（旧: `media.readBytes()`。IOException は TwitterException へ変換）。 */
    fun readBytes(media: InputStream): ByteArray

    /** [ByteArray] を [InputStream] として開く（旧: `ByteArrayInputStream(bytes)`）。 */
    fun byteArrayInputStream(bytes: ByteArray): InputStream

    /** [input] から最大 [len] バイトを読み出す（旧: `input.readNBytes(len)`）。末尾では空配列を返す。 */
    fun readNBytes(input: InputStream, len: Int): ByteArray

    /** ファイルの MIME タイプを推定する（旧: `Files.probeContentType(file.toPath())`）。 */
    fun probeContentType(file: File): String

    /** ファイル名を返す（旧: `file.name`）。 */
    fun fileName(file: File): String

    /** ファイルを [InputStream] として開く（旧: `file.inputStream()`）。 */
    fun openInputStream(file: File): InputStream
}
