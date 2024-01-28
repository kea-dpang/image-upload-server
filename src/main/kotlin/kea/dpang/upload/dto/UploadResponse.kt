package kea.dpang.upload.dto

/**
 * 업로드된 파일에 대한 응답을 담는 데이터 클래스
 *
 * @property uploadedFileUrl 업로드된 파일에 접근할 수 있는 URL
 */
data class UploadResponse(
    val uploadedFileUrl: String
)
