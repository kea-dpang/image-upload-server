package kea.dpang.upload.feign.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthResponse(
    @JsonProperty("token") val token: Token
) {
    data class Token(
        @JsonProperty("methods") val methods: List<String>,
        @JsonProperty("expires_at") val expiresAt: String,
        @JsonProperty("user") val user: User,
        @JsonProperty("project") val project: Project,
        @JsonProperty("roles") val roles: List<Role>,
        @JsonProperty("audit_ids") val auditIds: List<String>,
        @JsonProperty("issued_at") val issuedAt: String,
        @JsonProperty("catalog") val catalog: List<Catalog>,
        @JsonProperty("application_credential") val applicationCredential: ApplicationCredential
    ) {
        data class User(
            @JsonProperty("domain") val domain: Domain,
            @JsonProperty("id") val id: String,
            @JsonProperty("name") val name: String,
            @JsonProperty("password_expires_at") val passwordExpiresAt: String?
        ) {
            data class Domain(
                @JsonProperty("id") val id: String,
                @JsonProperty("name") val name: String
            )
        }

        data class Project(
            @JsonProperty("domain") val domain: Domain,
            @JsonProperty("id") val id: String,
            @JsonProperty("name") val name: String
        ) {
            data class Domain(
                @JsonProperty("id") val id: String,
                @JsonProperty("name") val name: String
            )
        }

        data class Role(
            @JsonProperty("id") val id: String,
            @JsonProperty("name") val name: String
        )

        data class Catalog(
            @JsonProperty("endpoints") val endpoints: List<Endpoint>,
            @JsonProperty("type") val type: String,
            @JsonProperty("id") val id: String,
            @JsonProperty("name") val name: String
        ) {
            data class Endpoint(
                @JsonProperty("id") val id: String,
                @JsonProperty("interface") val `interface`: String,
                @JsonProperty("region") val region: String,
                @JsonProperty("region_id") val regionId: String,
                @JsonProperty("url") val url: String
            )
        }

        data class ApplicationCredential(
            @JsonProperty("name") val name: String,
            @JsonProperty("id") val id: String,
            @JsonProperty("restricted") val restricted: Boolean
        )
    }
}
