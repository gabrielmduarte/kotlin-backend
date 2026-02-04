package com.gmd.nike_demo.model.response

data class MetricsResponse(
    val uploads: Int,
    val reads: Int,
    val errors: Int
)