package kr.ac.changwon.together.common.util

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.util.IOUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.util.*

@Component
class ImageUploader(
    private val client: AmazonS3Client
) {

    @Value("\${cloud.aws.s3.bucket}")
    lateinit var bucket: String

    @Value("\${cloud.aws.s3.dir}")
    lateinit var dir: String

    fun upload(file: MultipartFile): String {
        val fileName = "${UUID.randomUUID()}-${file.originalFilename}"
        val objMeta = ObjectMetadata()

        val bytes = IOUtils.toByteArray(file.inputStream)
        objMeta.contentLength = bytes.size.toLong()
        objMeta.contentType = file.contentType

        val byteArrayIs = ByteArrayInputStream(bytes)
        val key = "${dir}/${fileName}"

        client.putObject(
            PutObjectRequest(bucket, key, byteArrayIs, objMeta)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        )

        return client.getUrl(bucket, key).toString()
    }
}