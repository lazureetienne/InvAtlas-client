package com.example.invatlas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.invatlas.ui.theme.InvAtlasTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedItem by remember { mutableIntStateOf(0) }
            val items = listOf(
                NavigationItem("Atlas", R.drawable.outline_map_24, R.drawable.baseline_map_24),
                NavigationItem("Ivy", R.drawable.outline_forum_24, R.drawable.baseline_forum_24),
                NavigationItem("Floradex", R.drawable.outline_yard_24, R.drawable.baseline_yard_24)
            )
            InvAtlasTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    when (selectedItem) {
                        0 -> AtlasScreen()
                        1 -> IvyScreen()
                        2 -> FloradexScreen()
                    }
                    NavigationBar(
                        modifier = Modifier.align(Alignment.BottomCenter)
                    ) {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = selectedItem == index,
                                onClick = { selectedItem = index },
                                icon = {
                                    val iconId =
                                        if (selectedItem == index) item.selectedIconId else item.iconId
                                    Icon(
                                        painterResource(id = iconId),
                                        contentDescription = item.title
                                    )
                                },
                                label = { Text(item.title) }
                            )
                        }
                    }
                }
            }
        }
    }

    data class NavigationItem(
        val title: String,
        @DrawableRes val iconId: Int,
        @DrawableRes val selectedIconId: Int
    )


    @Composable
    fun AtlasScreen() {
        val singapore = LatLng(1.35, 103.87)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(singapore, 10f)
        }
        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            // TODO
        }
    }

    @Composable
    fun IvyScreen() {
        Text(text = "Ivy")
    }

    @Composable
    fun FloradexScreen() {
        Text(text = "Floradex")
    }
    @Preview(showBackground = true)
    @Composable
    fun Preview() {
        // TODO
    }
}