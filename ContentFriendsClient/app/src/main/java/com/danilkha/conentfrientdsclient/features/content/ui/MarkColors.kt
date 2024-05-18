package com.danilkha.conentfrientdsclient.features.content.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object MarkColors {
    val highestColor = Color(0xFFf5d742)
    val highColor = Color(0xFF26c937)
    val midColor = Color(0xff878787)
    val poorColor = Color(0xff8c0000)


    fun getMarkColor(mark: Number): Color = when {
        mark.toFloat() > 9f -> highestColor
        mark.toFloat() > 7f -> highColor
        mark.toFloat() > 5f -> midColor
        else -> poorColor
    }

}