package com.android.knewsapp.core_ui.components

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.knewsapp.core_ui.theme.KNewsAppTheme
import org.junit.Rule
import org.junit.Test

class KNewsButtonSnapshotTest {
    @get:Rule
    val paparazzi =
        Paparazzi(
            deviceConfig = DeviceConfig.PIXEL_5,
        )

    @Test
    fun loginButton_normal() {
        paparazzi.snapshot {
            KNewsAppTheme {
                KNewsButton(
                    text = "Login",
                    onClick = {},
                )
            }
        }
    }

    @Test
    fun loginButton_loading() {
        paparazzi.snapshot {
            KNewsAppTheme {
                KNewsButton(
                    text = "Login",
                    onClick = {},
                    isLoading = true,
                )
            }
        }
    }
}
