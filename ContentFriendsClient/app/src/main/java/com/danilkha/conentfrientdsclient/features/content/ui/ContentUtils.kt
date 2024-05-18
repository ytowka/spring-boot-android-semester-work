package com.danilkha.conentfrientdsclient.features.content.ui

import kotlin.math.abs

object ContentUtils {

    private const val format = "%.1f"

    fun formatMark(avg: Number): String =
        if(avg is Float){
            format.format(avg)
        }else avg.toString()
}