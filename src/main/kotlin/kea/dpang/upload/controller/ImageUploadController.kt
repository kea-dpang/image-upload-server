package kea.dpang.upload.controller

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
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        val path = "image"
        val fileName = file.originalFilename ?: "default.jpg"
        val contentType = file.contentType ?: "application/octet-stream"

        imageUploadService.uploadFile(path, fileName, contentType, file)
        return ResponseEntity("File uploaded successfully", HttpStatus.OK)
    }

}
