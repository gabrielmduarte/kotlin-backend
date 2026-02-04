package com.gmd.nike_demo.model.metrics

import java.util.concurrent.atomic.AtomicInteger

object Metrics {
    val uploads = AtomicInteger(0)
    val reads = AtomicInteger(0)
    val errors = AtomicInteger(0)
}