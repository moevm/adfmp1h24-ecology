package xyz.moevm.ecology.ui.routes

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Location
import android.service.autofill.UserData
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FrontHand
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import xyz.moevm.ecology.R
import xyz.moevm.ecology.api.getCenter
import xyz.moevm.ecology.api.parseObjColor
import xyz.moevm.ecology.api.types.SendObjectInfo
import xyz.moevm.ecology.data.DataSource
import xyz.moevm.ecology.data.viewmodels.MapDataViewModel
import xyz.moevm.ecology.data.viewmodels.UserDataViewModel
import xyz.moevm.ecology.ui.components.DropDownMenu
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.pow

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("SimpleDateFormat", "MissingPermission")
@Composable
fun AddObjectScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    mapDataVM: MapDataViewModel = viewModel(),
    userDataVM: UserDataViewModel = viewModel()
) {
    // Для проверки разрешения на геолокацию.
    val fineLocation = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    val coarseLocation = rememberPermissionState(permission = Manifest.permission.ACCESS_COARSE_LOCATION)

    val userDataState by userDataVM.state.collectAsState()

    val objectTypes: List<String> = DataSource.objectTypes.map { stringResource(it) }
    val objectColorMap: Map<String, String> = DataSource.objectTypesColors.mapKeys {
        stringResource(it.key)
    }

    var objectName by remember { mutableStateOf("" )}
    var objectType by remember { mutableStateOf(objectTypes[0])}
    var mapModeDraw by remember { mutableStateOf(true) }
    var coordinates: List<LatLng> by remember { mutableStateOf(listOf()) }

    val cameraPos by mapDataVM.cameraPos.collectAsState()
    val startZoom = 10f

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cameraPos, startZoom)
    }

    LaunchedEffect(Unit) {
        while(true) {
            val newPos = Location("")
            newPos.latitude = cameraPositionState.position.target.latitude
            newPos.longitude = cameraPositionState.position.target.longitude

            // Запоминаем новый центр камеры.
            mapDataVM.setCameraPos(LatLng(newPos.latitude, newPos.longitude))
            delay(1000)
        }
    }

    Column(
        modifier = modifier
            .padding(15.dp, 5.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.add_object_title),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(5.dp)
        )
        OutlinedTextField(
            value = objectName,
            onValueChange = { objectName = it },
            label = { Text(stringResource(R.string.add_object_name_inp_label)) },
            placeholder = { Text(stringResource(R.string.add_object_inp_placeholder)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        DropDownMenu(
            suggestions = objectTypes,
            selectedVar = objectType,
            onSelect = { label -> objectType = label },
            label = { Text(stringResource(R.string.add_object_type_inp_label)) },
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = stringResource(R.string.add_object_object_pos), modifier = Modifier.padding(top = 10.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Switch(
                checked = mapModeDraw,
                onCheckedChange = { mapModeDraw = it },
                thumbContent = if (mapModeDraw) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                } else {
                    {
                        Icon(
                            imageVector = Icons.Filled.FrontHand,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                onClick = { coordinates = listOf() }
                ) {
                Text(text = stringResource(R.string.add_object_delete_polygon))
            }
        }

        // Добавляем карту.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapClick = {clickPos ->
                    if (mapModeDraw) {
                        coordinates += listOf(clickPos)
                    }
                }
            ) {
                MapEffect(mapModeDraw, coordinates, objectType) { map ->
                    // Включение кнопки геолокации, если разрешение есть.
                    if (fineLocation.status.isGranted || coarseLocation.status.isGranted) {
                        map.isMyLocationEnabled = true
                    }

                    map.clear()
                    val colorInt = parseObjColor(objectColorMap[objectType]!!)
                    if (mapModeDraw) {
                        map.uiSettings.setAllGesturesEnabled(false)

                        if (coordinates.isNotEmpty()) {
                            // Если рисуем полигон, то добавляем линию.
                            val polylineOptions = PolylineOptions().color(colorInt)
                            coordinates.forEach { point ->
                                polylineOptions.add(point)
                            }
                            map.addPolyline(polylineOptions)

                            // Добавим круг на последнюю точку, для удобства пользователя.
                            val circleOptions = CircleOptions().center(coordinates.last())
                                .fillColor(colorInt)
                                .radius(2.0.pow(18.0 - cameraPositionState.position.zoom.toDouble()))
                            map.addCircle(circleOptions)
                        }
                    } else {
                        map.uiSettings.setAllGesturesEnabled(true)

                        if (coordinates.isNotEmpty()) {
                            // Если смотрим карту, то добавляем нарисованный полигон.
                            val polygonOptions = PolygonOptions().strokeColor(colorInt)
                                .fillColor(Color.argb(127, colorInt.red, colorInt.green, colorInt.blue))
                            coordinates.forEach { point ->
                                polygonOptions.add(point)
                            }
                            map.addPolygon(polygonOptions)
                        }
                    }
                }
            }
        }

        if (userDataState == null) {
            Text(text = stringResource(R.string.error_login), color = MaterialTheme.colorScheme.error )
        } else {
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.End),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                    onClick = { navController.navigateUp() }) {
                    Text(text = stringResource(R.string.add_object_cancel))
                }
                Button(
                    enabled = coordinates.size > 2 && userDataState != null,
                    onClick = {
                        val time = Calendar.getInstance().time
                        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")

                        val addedObject = SendObjectInfo(
                            type = objectType,
                            name = objectName,
                            color = objectColorMap[objectType]!!,
                            updateUserId = userDataState!!._id!!.id!!,
                            updateDatetime = formatter.format(time),
                            center = getCenter(coordinates),
                            coordinates = coordinates.map { listOf(it.latitude.toDouble(), it.longitude.toDouble()) }

                        )
                        mapDataVM.updateObjects(addedObject)
                        navController.navigate(DataSource.TopNavItems[1].route)
                    }) {
                    Text(text = stringResource(R.string.add_object_confirm))
                }
            }
        }
    }
}
