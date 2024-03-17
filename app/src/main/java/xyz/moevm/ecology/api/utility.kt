package xyz.moevm.ecology.api

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.google.android.gms.maps.model.LatLng

fun parseObjColor(colorStr: String): Int {
    return when (colorStr) {
        "green" -> Color.rgb(0, 128, 0)
        "red" -> Color.rgb(255, 0, 0)
        "aquamarine" -> Color.rgb(127, 255, 212)
        "darkgrey" -> Color.rgb(169, 169, 169)
        else -> Color.rgb(0, 0, 0)
    }
}


fun getCenter(coordinates: List<LatLng>): List<Double> {
    // center в обраном порядке (longitude, latitude)
    // Потому что на сервере так хранится.
    val center = mutableListOf(0.0, 0.0)
    for (coordinate in coordinates) {
        center[0] += coordinate.longitude
        center[1] += coordinate.latitude
    }
    center[0] = center[0] / coordinates.size
    center[1] = center[1] / coordinates.size
    return center
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}