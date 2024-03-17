package com.example.invatlas.views

import android.R.attr.bitmap
import android.graphics.Bitmap
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.invatlas.PlantWindow
import com.example.invatlas.R
import com.example.invatlas.navigation.InvAtlasDestinations
import com.example.invatlas.utils.checkForPermission
import com.example.invatlas.utils.getCurrentLocation
import com.example.invatlas.utils.saveBitmap
import com.example.invatlas.viewmodels.PlantViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.io.ByteArrayOutputStream


@Composable
fun AtlasScreen(vm: PlantViewModel, nav: NavHostController) {
    val context = LocalContext.current
    val defaultLocation = LatLng(46.5458, -72.7492) // Shawinigan, QC
    var currentUserPosition by remember { mutableStateOf(defaultLocation) }
    var showPopup by remember { mutableStateOf(false) }
    var showMap by remember { mutableStateOf(false) }

    LaunchedEffect(Unit, block = {
        vm.getUserPlants()
    })

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { newImage ->
            val outputStream = ByteArrayOutputStream()
            newImage?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

            var base = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
            if (base != null) {
                vm.identifyPlant(currentUserPosition.latitude, currentUserPosition.longitude, base)
                showPopup = true
                showMap = false
            } else {
                Toast.makeText(context, "Impossible d'enregistrer l'image", Toast.LENGTH_SHORT).show()
            }

        }
    )

    when {
        showPopup -> {
            if (vm.userPlant == null) {
                UnknownPlantPopup(
                    onDismissRequest = { showPopup = false },
                    onConfirmation = {
                        showPopup = false
                    },
                )
            } else {
                CongratulationPopup(
                    onDismissRequest = { showPopup = false },
                    onChat = {
                        showPopup = false
                        nav.navigate("chat/${vm.userPlant!!.code}/${vm.userPlant!!.plant}")
                    },
                    userPlant = vm.userPlant!!
                )
            }
        }
    }


    val hasLocationPermission = remember {
        mutableStateOf(
            checkForPermission(context)
        )
    }

    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.HYBRID, isMyLocationEnabled = true))
    }

    getCurrentLocation(context) {
        currentUserPosition = it
        showMap = true
    }
   if (showMap) {
        val cameraPositionState = rememberCameraPositionState {
            position = if (hasLocationPermission.value) {
                CameraPosition.Builder().target(currentUserPosition).zoom(16f).build()
            } else {
                CameraPosition.Builder().target(defaultLocation).zoom(8f).build()
            }
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .clip(RoundedCornerShape(10.dp)),
            properties = properties,
            cameraPositionState = cameraPositionState
        ) {
            vm.userPlants.forEach { userPlant ->
                MarkerInfoWindow(
                    state = MarkerState(position = LatLng(userPlant.latitude, userPlant.longitude)),
                    title = "Shawi",
                ) { marker ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        PlantWindow(userPlant)
                    }
                }
            }
        }

    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    FloatingActionButton(
        onClick = { cameraLauncher.launch() },
        modifier = Modifier.padding(top = 10.dp, start = 10.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_photo_camera_24),
            contentDescription = "Floating action button."
        )
    }
}