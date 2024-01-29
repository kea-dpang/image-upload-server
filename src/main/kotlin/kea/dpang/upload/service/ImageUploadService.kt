package kea.dpang.upload.service

import org.springframework.web.multipart.MultipartFile


/**
 * 파일 서비스 인터페이스
 */
interface ImageUploadService {

    /**
     * 파일 업로드 메소드
     *
     * @param data 업로드할 파일 데이터
     */
    fun uploadFile(
        data: MultipartFile
    ) : String
}