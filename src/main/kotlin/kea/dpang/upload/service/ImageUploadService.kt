package kea.dpang.upload.service

import org.springframework.web.multipart.MultipartFile


/**
 * 파일 서비스 인터페이스
 */
interface ImageUploadService {

    /**
     * 파일 업로드 메소드
     *
     * @param path 파일 경로
     * @param fileName 파일 이름
     * @param contentType 콘텐츠 타입
     * @param data 업로드할 파일 데이터
     */
    fun uploadFile(
        path: String,
        fileName: String,
        contentType: String?,
        data: MultipartFile
    ) : String
}