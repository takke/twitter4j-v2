package twitter4j

import java.io.ByteArrayInputStream
import java.io.IOException
import java.nio.file.Files

/**
 * [V2MediaUploadSupport] の JVM(Android) 実装。
 * 従来 TwitterV2Impl / TwitterV2 に直接記述されていた java.io 依存のメディアアップロード処理を移設したもの。
 */
internal actual object V2MediaUploadSupport {

    actual fun mediaHttpParameter(name: String, fileName: String, media: InputStream): HttpParameter =
        HttpParameter(name, fileName, media)

    actual fun readBytes(media: InputStream): ByteArray =
        try {
            media.readBytes()
        } catch (ioe: IOException) {
            throw TwitterException("Failed to download the file.", ioe)
        }

    actual fun byteArrayInputStream(bytes: ByteArray): InputStream =
        ByteArrayInputStream(bytes)

    actual fun readNBytes(input: InputStream, len: Int): ByteArray =
        input.readNBytes(len)

    actual fun probeContentType(file: File): String =
        Files.probeContentType(file.toPath())

    actual fun fileName(file: File): String =
        file.name

    actual fun openInputStream(file: File): InputStream =
        file.inputStream()
}
