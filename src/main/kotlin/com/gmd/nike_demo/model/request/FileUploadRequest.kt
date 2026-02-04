package com.gmd.nike_demo.model.request

data class FileUploadRequest(
    val filename: String,
    val content: String
)