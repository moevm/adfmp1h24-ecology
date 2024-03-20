package xyz.moevm.ecology.ui.routes

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import xyz.moevm.ecology.api.parseObjColor
import xyz.moevm.ecology.data.viewmodels.MapDataViewModel
import kotlin.math.pow
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState


@SuppressLint("MissingPermission")
@OptIn(MapsComposeExperimentalApi::class, ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    mapDataVM: MapDataViewModel = viewModel()
) {
    // Для получения разрешения на геолокацию.
    val fineLocation = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    val coarseLocation = rememberPermissionState(permission = Manifest.permission.ACCESS_COARSE_LOCATION)
    val allLocations = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    // Спрашивали ли пользователя за время запуска приложения уже?
    val userGeoPermissionAsked by mapDataVM.userGeoPermissionAsked.collectAsState()

    // Объекты на карте.
    val mapsDataState by mapDataVM.maps.collectAsState()
    val objectsDataState by mapDataVM.objects.collectAsState()
    val cameraStartPos by mapDataVM.cameraPos.collectAsState()

    // Последняя позиция камеры.
    val lastPos = Location("")
    lastPos.latitude = cameraStartPos.latitude
    lastPos.longitude = cameraStartPos.longitude
    var lastZoom = 10f
    var lastScanRadius = 50000.0

    // Получаем начальные объекты и изображения.
    mapDataVM.getMapsAndObjectsNear(lastPos.latitude, lastPos.longitude, lastScanRadius)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(lastPos.latitude, lastPos.longitude), lastZoom)
    }
    mapDataVM.setCameraPos(LatLng(lastPos.latitude, lastPos.longitude))

    // Проверяем, что текущие объекты и тайлы актуальны, если нет, то запрашиваем с сервера новые.
    LaunchedEffect(Unit) {
        while(true) {
            val newPos = Location("")
            newPos.latitude = cameraPositionState.position.target.latitude
            newPos.longitude = cameraPositionState.position.target.longitude
            val newZoom = cameraPositionState.position.zoom
            val distance = lastPos.distanceTo(newPos).toDouble()

            // Запоминаем новый центр камеры.
            mapDataVM.setCameraPos(LatLng(newPos.latitude, newPos.longitude))

            if (lastZoom > newZoom || distance > lastScanRadius) {
                // Заменяем старый zoom на новый.
                lastPos.latitude = newPos.latitude
                lastPos.longitude = newPos.longitude
                lastScanRadius = if (lastZoom > newZoom) lastScanRadius * (2.0).pow((lastZoom - newZoom).toDouble()) else distance
                lastZoom = newZoom

                // Получаем новый список объектов и карт для отображения.
                mapDataVM.getMapsAndObjectsNear(lastPos.latitude, lastPos.longitude, lastScanRadius)
            }
            delay(1000)
        }
    }

    // Добавляем карту.
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            MapEffect(mapsDataState, objectsDataState) { map ->
                // Включение кнопки геолокации или запрос разрешения пользователя.
                if (fineLocation.status.isGranted || coarseLocation.status.isGranted) {
                    map.isMyLocationEnabled = true
                } else if (!userGeoPermissionAsked) {
                    allLocations.launchMultiplePermissionRequest()
                    mapDataVM.setUserGeoPermissionAsked(true)
                }

                map.clear()
                // Добавляем текущие тайлы на карту.
                // P.S. Это не работает, потому что google map тайлеру не нравятся тайлы, которые
                // прилетают с сервера, хотя leaflet-у было ок.
                // Скорее всего UrlTileProvider кривой кусок $%$#$%#.
//                if (!mapsDataState.isNullOrEmpty()) {
//                    mapsDataState!!.forEach { mapTiles ->
//                        val tileProvider: TileProvider = object : UrlTileProvider(256, 256) {
//                            override fun getTileUrl(x: Int, y: Int, zoom: Int): URL? {
//
//                                /* Define the URL pattern for the tile images */
//                                val url = "${SERVER_URL}tiles/tile/${mapTiles.id}/$zoom/$x/$y"
//                                return if (!checkTileExists(x, y, zoom)) {
//                                    null
//                                } else try {
//                                    URL(url)
//                                } catch (e: MalformedURLException) {
//                                    null
//                                }
//                            }
//
//                            private fun checkTileExists(x: Int, y: Int, zoom: Int): Boolean {
//                                val minZoom = 1
//                                val maxZoom = 17
//                                return zoom in minZoom..maxZoom
//                            }
//                        }
//
//                        map.addTileOverlay(TileOverlayOptions().tileProvider(tileProvider))
//                    }
//                }
                // Добавляем текущие объекты на карту.
                if (!objectsDataState.isNullOrEmpty()) {
                    objectsDataState!!.forEach { mapObject ->
                        // Получаем цвет.
                        val color = parseObjColor(mapObject.color)

                        if (mapObject.coordinates.isNotEmpty()) {
                            val polygonOptions = PolygonOptions()
                                .fillColor(Color.argb(127, color.red, color.green, color.blue))
                                .strokeColor(color)

                            for (i in mapObject.coordinates.indices) {
                                polygonOptions.add(
                                    LatLng(
                                        mapObject.coordinates[i][0],
                                        mapObject.coordinates[i][1]
                                    )
                                )
                            }
                            map.addPolygon(polygonOptions)
                        }
                    }
                    map.setOnMapLoadedCallback {
                        // Установить значение камеры.
                    }
                }
            }
        }
    }
}


