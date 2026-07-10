package twitter4j

/**
 * [V2MediaUploadSupport] の iOS(Native) 実装。
 *
 * iOS 版 TwitPane（app_research 系）は閲覧専用でありメディア投稿を行わないため、
 * いずれの操作も未サポートとして [NotImplementedError] を送出する。
 * 読み取り系 API（タイムライン取得等）はこれらを経由しないため影響しない。
 */
private const val NOT_SUPPORTED = "media upload is not supported on iOS (read-only client)"

internal actual object V2MediaUploadSupport {

    actual fun mediaHttpParameter(name: String, fileName: String, media: InputStream): HttpParameter =
        throw NotImplementedError(NOT_SUPPORTED)

    actual fun readBytes(media: InputStream): ByteArray =
        throw NotImplementedError(NOT_SUPPORTED)

    actual fun byteArrayInputStream(bytes: ByteArray): InputStream =
        throw NotImplementedError(NOT_SUPPORTED)

    actual fun readNBytes(input: InputStream, len: Int): ByteArray =
        throw NotImplementedError(NOT_SUPPORTED)

    actual fun probeContentType(file: File): String =
        throw NotImplementedError(NOT_SUPPORTED)

    actual fun fileName(file: File): String =
        throw NotImplementedError(NOT_SUPPORTED)

    actual fun openInputStream(file: File): InputStream =
        throw NotImplementedError(NOT_SUPPORTED)
}
