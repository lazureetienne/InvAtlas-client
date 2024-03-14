package com.example.invatlas

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun AtlasScreen() {
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.HYBRID, isMyLocationEnabled = true))
    }

    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp, top = 40.dp),
        properties = properties,
        cameraPositionState = cameraPositionState
    )
    FloatingActionButton(
        onClick = { /*TODO: open camera*/ },
        modifier = Modifier.padding(top = 50.dp, start = 10.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_photo_camera_24),
            contentDescription = "Floating action button."
        )
    }
}