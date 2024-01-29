package kea.dpang.upload.service

/**
 * API 키 관리 서비스 인터페이스
 */
interface ApiKeyService {

    /**
     * API 키를 갱신하는 메서드
     */
    fun updateApiKey()

    /**
     * API 키를 반환하는 메서드
     */
    fun getApiKey(): String

}