package com.danilkha.conentfrientdsclient.features.content.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object MarkColors {
    val highestColor = Color(0xFFf5d742)
    val highColor = Color(0xFF26c937)
    val midColor = Color(0xff878787)
    val poorColor = Color(0xff8c0000)


    fun getMarkColor(mark: Float): Color = when {
        mark > 9f -> highestColor
        mark > 7f -> highColor
        mark > 5f -> midColor
        else -> poorColor
    }

}