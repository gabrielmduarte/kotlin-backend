package com.gmd.nike_demo.controller

import com.gmd.nike_demo.model.metrics.Metrics
import com.gmd.nike_demo.model.response.MetricsResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MetricsController {

    @GetMapping("/metrics")
    fun metrics(): MetricsResponse =
        MetricsResponse(
            uploads = Metrics.uploads.get(),
            reads = Metrics.reads.get(),
            errors = Metrics.errors.get()
        )
}