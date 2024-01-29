package kea.dpang.upload.service

import kea.dpang.upload.exception.UnauthorizedException
import kea.dpang.upload.feign.KakaoCloudObjectStorageClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class ImageUploadServiceImpl(
    private val kakaoCloudObjectStorageClient: KakaoCloudObjectStorageClient,
    private val apiKeyService: ApiKeyService,
    @Value("\${kakao-cloud.account}") private val account: String,
    @Value("\${kakao-cloud.bucket}") private val bucket: String,
) : ImageUploadService {

    // Kakao Cloud Info
    private val baseUrl = "https://objectstorage.kr-gov-central-1.kakaoicloud-kr-gov.com/v1/"

    // Log
    private val log = LoggerFactory.getLogger(ImageUploadServiceImpl::class.java)

    override fun uploadFile(
        path: String,
        fileName: String,
        data: MultipartFile
    ): String {
        // 파일을 업로드하고 결과를 받는다.
        val result = kakaoCloudObjectStorageClient.uploadFile(
            account = account,
            bucket = bucket,
            path = path,
            fileName = fileName,
            token = apiKeyService.getApiKey(),
            data = data
        )

        // HTTP 상태 코드가 401이라면 권한이 없는 경우이므로 UnauthorizedException을 던진다.
        // 참고) https://console.kakaoicloud-kr-gov.com/docs/guide/objectstorage/api/api_object
        if (result.status() == 401) {
            throw UnauthorizedException()
        }

        // 업로드된 파일에 접근할 수 있는 URL을 만든다.
        val uploadedFileUrl = "$baseUrl$account/$bucket/$path/$fileName"

        // 로그 메시지를 만들고 출력한다.
        val logMessage = StringBuilder().apply {
            append("파일 업로드 요청이 완료되었습니다.\n")
            append("업로드 결과: ${if (result.status() == 201) "성공" else "실패"}\n")
            append("HTTP 상태: ${result.status()}\n")
            append("Response Headers: ${result.headers()}\n")
            append("업로드된 파일 URL: $uploadedFileUrl\n")
        }.toString()

        log.info(logMessage)

        // 업로드된 파일의 URL을 반환한다.
        return uploadedFileUrl
    }

}