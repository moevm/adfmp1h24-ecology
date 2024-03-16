package xyz.moevm.ecology.api

import android.graphics.Color

fun parseObjColor(colorStr: String): Int {
    return when (colorStr) {
        "green" -> Color.rgb(0, 128, 0)
        "red" -> Color.rgb(255, 0, 0)
        "aquamarine" -> Color.rgb(127, 255, 212)
        "darkgrey" -> Color.rgb(169, 169, 169)
        else -> Color.rgb(0, 0, 0)
    }
}