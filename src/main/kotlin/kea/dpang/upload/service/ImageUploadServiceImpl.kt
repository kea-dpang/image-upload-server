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

    @Value("\${kakao-cloud.account}")
    private lateinit var account: String

    @Value("\${kakao-cloud.bucket}")
    private lateinit var bucket: String

    @Value("\${kakao-cloud.api-key}")
    private lateinit var apiKey: String

    private val log = LoggerFactory.getLogger(ImageUploadServiceImpl::class.java)

    override fun uploadFile(
        path: String,
        file: String,
        contentType: String?,
        data: MultipartFile
    ) {
        val result = kakaoCloudObjectStorageClient.uploadFile(
            account = account,
            bucket = bucket,
            path = path,
            file = file,
            token = apiKey,
            data = data
        )

        log.info(result.toString())
    }
}