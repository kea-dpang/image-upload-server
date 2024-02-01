package kea.dpang.upload.service

import kea.dpang.upload.exception.ApiKeyNotFoundException
import kea.dpang.upload.feign.KakaoCloudAuthClient
import kea.dpang.upload.feign.dto.AuthRequest
import kea.dpang.upload.feign.dto.AuthRequest.Auth
import kea.dpang.upload.feign.dto.AuthRequest.Auth.Identity
import kea.dpang.upload.feign.dto.AuthRequest.Auth.Identity.ApplicationCredential
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ApiKeyServiceImpl(
    private val kakaoCloudAuthClient: KakaoCloudAuthClient,
    @Value("\${kakao-cloud.access-key-id}") private val accessKeyId: String,
    @Value("\${kakao-cloud.access-secret-key}") private val accessSecretKey: String
) : ApiKeyService {

    private val log = LoggerFactory.getLogger(ApiKeyServiceImpl::class.java)

    private var apiKey: String? = null

    @Scheduled(fixedRate = 11 * 60 * 60 * 1000 + 50 * 60 * 1000) // 11시간 50분마다 실행
    override fun updateApiKey() {
        val authRequest = AuthRequest(
            auth = Auth(
                identity = Identity(
                    methods = listOf("application_credential"),
                    applicationCredential = ApplicationCredential(
                        id = accessKeyId,
                        secret = accessSecretKey
                    )
                )
            )
        )

        // API 키 갱신을 요청한다.
        val response = kakaoCloudAuthClient.getAuthToken(authRequest)

        // 응답 헤더에서 새 API 키를 가져온다.
        val newApiKey = response.headers["X-Subject-Token"]?.firstOrNull()

        // 갱신된 API 키를 저장한다.
        this.apiKey = newApiKey

        // 로그를 출력한다.
        newApiKey?.let { key ->
            val firstPart = key.take(40)
            val lastPart = key.takeLast(30)
            val obscuredMiddle = key.drop(40).dropLast(30).map { '*' }.joinToString("")
            log.info("API 키가 갱신되었습니다. 새 API 키: $firstPart$obscuredMiddle$lastPart")
        }

    }

    override fun getApiKey(): String {
        return apiKey ?: throw ApiKeyNotFoundException()
    }
}