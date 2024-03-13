package com.example.invatlas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.invatlas.ui.theme.InvAtlasTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Fetch user from server.
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

    private val levels = createLevels()

    private val currentUser = createDummyUser()
    private fun createLevels(): List<Level> {
        val levels = mutableListOf<Level>()
        for (i in 1..100) {
            levels.add(Level(i, 10 * i))
        }
        return levels
    }

    private fun createDummyUser(): User {
        return User(
            "testAuth",
            "testUser",
            5
        )
    }

    private fun getUserLevel(user: User): Int {
        val userLevel = levels.find { it.xpCap > user.xp } ?: levels.last()
        return userLevel.level
    }

    private fun xpCap(userLevel: Int): Int {
        return levels.find { it.level == userLevel }?.xpCap ?: 0
    }

    @Composable
    fun AtlasScreen() {
        val singapore = LatLng(1.35, 103.87)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(singapore, 10f)
        }
        Box(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(end = 15.dp),
                    text = "Niveau ${getUserLevel(currentUser)}"
                )
                LinearProgressIndicator(
                    progress = currentUser.xp.toFloat() / xpCap(getUserLevel(currentUser)),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = "${currentUser.xp}/${xpCap(getUserLevel(currentUser))} xp"
                )
            }
        }
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp, top = 40.dp),
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