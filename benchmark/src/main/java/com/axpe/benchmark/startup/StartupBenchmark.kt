package com.axpe.benchmark.startup

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.axpe.benchmark.BaselineProfileMetrics
import com.axpe.benchmark.PACKAGE_NAME
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class StartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startupWithoutPreCompilation() = startup(CompilationMode.None())


    private fun startup(compilationMode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = PACKAGE_NAME,
        metrics = BaselineProfileMetrics.allMetrics,
        compilationMode = compilationMode,
        iterations = 20,
        startupMode = StartupMode.COLD,
    ) {
        pressHome()
        startActivityAndWait()
    }

}