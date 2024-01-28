package kea.dpang.upload.service

import kea.dpang.upload.feign.KakaoCloudObjectStorageClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class ImageUploadServiceImpl(
    private val kakaoCloudObjectStorageClient: KakaoCloudObjectStorageClient
) : ImageUploadService {

    val baseUrl = "https://objectstorage.kr-gov-central-1.kakaoicloud-kr-gov.com/v1/"

    @Value("\${kakao-cloud.account}")
    private lateinit var account: String

    @Value("\${kakao-cloud.bucket}")
    private lateinit var bucket: String

    @Value("\${kakao-cloud.api-key}")
    private lateinit var apiKey: String

    private val log = LoggerFactory.getLogger(ImageUploadServiceImpl::class.java)

    override fun uploadFile(
        path: String,
        fileName: String,
        contentType: String?,
        data: MultipartFile
    ) {
        val result = kakaoCloudObjectStorageClient.uploadFile(
            account = account,
            bucket = bucket,
            path = path,
            fileName = fileName,
            token = apiKey,
            data = data
        )

        val uploadedFileUrl = "$baseUrl$account/$bucket/$path/$fileName"

        val logMessage = StringBuilder().apply {
            append("파일 업로드 요청이 완료되었습니다.\n")
            append("업로드 결과: ${if (result.status() == 201) "성공" else "실패"}\n")
            append("HTTP 상태: ${result.status()}\n")
            append("Response Headers: ${result.headers()}\n")
            append("업로드된 파일 URL: $uploadedFileUrl\n")
        }.toString()

        log.info(logMessage)

    }

}