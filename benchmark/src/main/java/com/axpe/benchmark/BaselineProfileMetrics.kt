package com.axpe.benchmark

import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.TraceSectionMetric

class BaselineProfileMetrics {
    companion object {
        @OptIn(ExperimentalMetricApi::class)
        val jitCompilationMetric = TraceSectionMetric("JIT Compiling %", label = "JIT compilation")

        @OptIn(ExperimentalMetricApi::class)
        val classInitMetric = TraceSectionMetric("L%/%;", label = "ClassInit")

        @OptIn(ExperimentalMetricApi::class)
        val allMetrics = listOf(StartupTimingMetric(), jitCompilationMetric, classInitMetric)
    }
}