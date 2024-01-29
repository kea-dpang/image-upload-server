package kea.dpang.upload.feign.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @see https://docs.kakaocloud.com/start/api-preparation
 */
data class AuthRequest(
    @JsonProperty("auth") val auth: Auth
) {
    data class Auth(
        @JsonProperty("identity") val identity: Identity
    ) {
        data class Identity(
            @JsonProperty("methods") val methods: List<String>,
            @JsonProperty("application_credential") val applicationCredential: ApplicationCredential
        ) {
            data class ApplicationCredential(
                @JsonProperty("id") val id: String,
                @JsonProperty("secret") val secret: String
            )
        }
    }
}