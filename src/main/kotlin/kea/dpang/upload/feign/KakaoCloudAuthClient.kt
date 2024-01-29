package kea.dpang.upload.feign

import kea.dpang.upload.config.FeignConfig
import kea.dpang.upload.feign.dto.AuthRequest
import kea.dpang.upload.feign.dto.AuthResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "kakaoCloudAuth", url = "https://iam.kakaoicloud-kr-gov.com/identity/v3/auth/tokens", configuration = [FeignConfig::class])
interface KakaoCloudAuthClient {

    @PostMapping(consumes = ["application/json"])
    fun getAuthToken(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse>

}
