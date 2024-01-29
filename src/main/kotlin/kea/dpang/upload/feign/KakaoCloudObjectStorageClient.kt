package kea.dpang.upload.feign

import feign.Response
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

/**
 * 파일 업로드 및 삭제를 위한 Feign 클라이언트 인터페이스
 */
@FeignClient(name = "KakaoCloudObjectStorage", url = "https://objectstorage.kr-gov-central-1.kakaoicloud-kr-gov.com")
interface KakaoCloudObjectStorageClient {

    /**
     * 파일 업로드 메소드
     *
     * @param account 계정 ID
     * @param bucket 버킷 이름
     * @param path 파일 경로
     * @param fileName 파일 이름
     * @param headers HTTP 헤더
     * @param data 업로드할 파일 데이터
     * @return HTTP 응답
     */
    @PutMapping("/v1/{account}/{bucket}/{path}/{file}", consumes = ["multipart/form-data"])
    fun uploadFile(
        @PathVariable("account") account: String,
        @PathVariable("bucket") bucket: String,
        @PathVariable("path") path: String,
        @PathVariable("file") fileName: String,
        @RequestHeader headers: Map<String, String>,
        @RequestBody data: MultipartFile
    ): Response

}
