package kea.dpang.upload

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ImageUploadServerApplication

fun main(args: Array<String>) {
    runApplication<ImageUploadServerApplication>(*args)
}
