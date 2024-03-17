package xyz.moevm.ecology.ui.routes

import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.rememberCameraPositionState
import xyz.moevm.ecology.R
import xyz.moevm.ecology.api.parseObjColor
import xyz.moevm.ecology.api.types.CallState
import xyz.moevm.ecology.data.viewmodels.MapDataViewModel
import kotlin.math.pow

@Composable
fun ShareScreen(
    modifier: Modifier = Modifier,
    mapDataVM: MapDataViewModel = viewModel()
) {
    val objectAddCallState by mapDataVM.lastCallState.collectAsState()
    val lastAddedObject by mapDataVM.lastAddedObject.collectAsState()

    when (objectAddCallState) {
        CallState.SUCCESS -> SuccessShareScreen(
            objectName = lastAddedObject!!.name,
            objectType = lastAddedObject!!.type,
            objectColor = parseObjColor(lastAddedObject!!.color),
            coordinates = lastAddedObject!!.coordinates.map { LatLng(it[0], it[1]) },
            modifier = modifier
        )
        CallState.WAITING -> WaitingShareScreen(
            modifier = modifier
        )
        CallState.ERROR -> ErrorShareScreen(
            modifier = modifier
        )
        else -> ErrorShareScreen(
            modifier = modifier
        )
    }
}


@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun SuccessShareScreen(
    objectName: String,
    objectType: String,
    objectColor: Int,
    coordinates: List<LatLng>,
    modifier: Modifier = Modifier,
    mapDataVM: MapDataViewModel = viewModel()
) {

    val cameraPos by mapDataVM.cameraPos.collectAsState()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cameraPos, 10f)
    }

    // Создаем расчет минимального охватывающего экрана.
    val boundsBuilder = LatLngBounds.builder()
    for (coordinate in coordinates) {
        boundsBuilder.include(coordinate)
    }
    val bounds = boundsBuilder.build()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(15.dp, 5.dp)
            .fillMaxSize()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(25.dp)
            )
            Text(
                text = stringResource(id = R.string.share_screen_success_1),
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        Column {
            Text(text = stringResource(id = R.string.share_screen_success_2) + " " + objectName)
            Text(text = stringResource(id = R.string.share_screen_success_3) + " " + objectType)
        }

        Box(modifier = Modifier.size(400.dp)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
            ) {
                cameraPositionState.move(
                    update = CameraUpdateFactory.newLatLngBounds(bounds, 100)
                )

                MapEffect { map ->
                    map.clear()
                    map.uiSettings.setAllGesturesEnabled(false)
                    // Добавляем объект.
                    val polygonOptions = PolygonOptions().strokeColor(objectColor)
                        .fillColor(Color.argb(127, objectColor.red, objectColor.green, objectColor.blue))

                    coordinates.forEach { point ->
                        polygonOptions.add(point)
                    }

                    map.addPolygon(polygonOptions)
                }
            }
        }

        Button(
            modifier = Modifier.align(Alignment.End).padding(top = 10.dp),
            onClick = { /* Do something or nothing */ }
        ) {
            Text(text = stringResource(id = R.string.share_screen_success_4))
        }
    }
}

@Composable
fun ErrorShareScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(15.dp, 5.dp)
            .fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Filled.Cancel,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(50.dp)
        )
        Text(text = stringResource(id = R.string.share_screen_error_1))
        Text(text = stringResource(id = R.string.share_screen_error_2))
    }
}

@Composable
fun WaitingShareScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .padding(15.dp, 5.dp)
            .fillMaxSize()
    ) {
        CircularProgressIndicator()
        Text(text = stringResource(id = R.string.share_screen_wait_1))
        Text(text = stringResource(id = R.string.share_screen_wait_2))
    }
}