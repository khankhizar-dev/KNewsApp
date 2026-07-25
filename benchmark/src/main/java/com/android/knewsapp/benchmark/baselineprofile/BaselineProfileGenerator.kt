package com.android.knewsapp.benchmark.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import org.junit.Rule
import org.junit.Test

class BaselineProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() = baselineProfileRule.collect(
        packageName = "com.android.knewsapp",
        includeInStartupProfile = true
    ) {
        pressHome()
        startActivityAndWait()
    }
}
