package com.gmd.nike_demo.controller

import com.gmd.nike_demo.model.metrics.Metrics
import com.gmd.nike_demo.model.request.FileUploadRequest
import jakarta.annotation.PostConstruct
import java.io.File
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/files")
class FileController {

    private val storageDir = File("storage")

    @PostConstruct
    fun init() {
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
    }

    @PostMapping
    fun uploadFile(@RequestBody request: FileUploadRequest): ResponseEntity<Void> {
        val file = File(storageDir, request.filename)
        file.writeText(request.content)

        Metrics.uploads.incrementAndGet()
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/{filename}")
    fun getFile(@PathVariable filename: String): ResponseEntity<String> {
        val file = File(storageDir, filename)

        if (!file.exists()) {
            Metrics.errors.incrementAndGet()
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }

        Metrics.reads.incrementAndGet()
        return ResponseEntity.ok(file.readText())
    }

}