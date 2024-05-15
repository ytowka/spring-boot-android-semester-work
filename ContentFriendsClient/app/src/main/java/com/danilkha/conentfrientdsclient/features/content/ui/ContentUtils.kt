package com.danilkha.conentfrientdsclient.features.content.ui

object ContentUtils {

    private const val format = "%.1f"

    fun formatMark(avg: Float): String = format.format(avg)
}