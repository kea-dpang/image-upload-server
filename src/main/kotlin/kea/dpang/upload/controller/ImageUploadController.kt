package kea.dpang.upload.controller

import kea.dpang.upload.base.SuccessResponse
import kea.dpang.upload.dto.UploadResponse
import kea.dpang.upload.service.ImageUploadService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/image")
class ImageUploadController(
    private val imageUploadService: ImageUploadService
) {

    @PostMapping(consumes = ["multipart/form-data"], produces = ["application/json"])
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<SuccessResponse<UploadResponse>> {
        // 업로드할 파일의 경로, 이름, 컨텐트 타입을 설정한다.
        val path = "image"
        val fileName = file.originalFilename ?: "default.jpg"
        val contentType = file.contentType ?: "application/octet-stream"

        // 파일을 업로드하고, 업로드된 파일의 URL을 받는다.
        val uploadedFileUrl = imageUploadService.uploadFile(path, fileName, contentType, file)

        // 성공 응답 객체를 생성한다.
        val successResponse = SuccessResponse(
            status = HttpStatus.CREATED.value(),
            message = "파일이 성공적으로 업로드되었습니다.",
            data = UploadResponse(uploadedFileUrl)
        )

        // 성공 응답 객체를 ResponseEntity로 감싸서 반환한다.
        return ResponseEntity(successResponse, HttpStatus.CREATED)
    }


}
