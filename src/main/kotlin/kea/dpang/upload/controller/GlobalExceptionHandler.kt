package kea.dpang.upload.controller

import kea.dpang.upload.base.ErrorResponse
import kea.dpang.upload.exception.UnauthorizedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.multipart.MaxUploadSizeExceededException
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMaxUploadSizeExceededException(
        e: MaxUploadSizeExceededException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {

        val errorMessage = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = "업로드한 파일의 크기가 최대 허용 크기를 초과하였습니다.",
            path = request.getDescription(false)
        )

        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(
        e: UnauthorizedException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {

        val errorMessage = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.UNAUTHORIZED.value(),
            error = HttpStatus.UNAUTHORIZED.name,
            message = e.message ?: "세부 정보가 제공되지 않았습니다.",
            path = request.getDescription(false)
        )

        return ResponseEntity(errorMessage, HttpStatus.UNAUTHORIZED)
    }

}
