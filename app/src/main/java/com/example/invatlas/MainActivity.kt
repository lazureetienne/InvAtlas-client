package com.example.invatlas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.invatlas.ui.theme.AppTheme
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
            AppTheme {
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
        LevelBar()
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp, top = 40.dp),
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

    @Composable
    private fun LevelBar() {
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
                    progress = { currentUser.xp.toFloat() / xpCap(getUserLevel(currentUser)) },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = "${currentUser.xp}/${xpCap(getUserLevel(currentUser))} xp"
                )
            }
        }
    }

    // inspired by https://medium.com/@meytataliti/building-a-simple-chat-app-with-jetpack-compose-883a240592d4
    @Composable
    fun ChatBubble(message: Message) {
        Column(
            horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start,
        ) {
            Text(
                modifier =  if (message.isFromUser) Modifier.padding(end = 135.dp) else Modifier.padding(start = 160.dp),
                text = message.author,
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start
            ) {
                BoxWithConstraints {
                    Box(
                        modifier = Modifier
                            .widthIn(max = maxWidth * 0.5f)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 48f,
                                    topEnd = 48f,
                                    bottomStart = if (message.isFromUser) 48f else 0f,
                                    bottomEnd = if (message.isFromUser) 0f else 48f
                                )
                            )
                            .background(if (message.isFromUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
                            .padding(16.dp)
                    ) {
                        Text(text = message.text)
                    }
                }
            }
        }

    }
    @Composable
    fun IvyScreen() {
        var text by remember { mutableStateOf("") }

        // Function to handle text submission
        val onTextSubmit: (String) -> Unit = { submittedText ->
            // TODO: Handle the submitted text
            println("Submitted text: $submittedText")
            text = "" // Clear the text
        }
        LevelBar()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, bottom = 90.dp)
        ) {
            BoxWithConstraints(modifier = Modifier.align(Alignment.TopCenter)) {
                Box(
                    modifier = Modifier
                        .widthIn(max = maxWidth * 0.80f, min = maxWidth * 0.80f)
                        .height(150.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(MaterialTheme.colorScheme.tertiary)
                        .padding(5.dp),
                ) {
                    Text(
                        text = "Parle avec Ivy !",
                        modifier = Modifier.align(Alignment.TopCenter),
                        fontWeight = FontWeight(700),
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 5.dp, top = 28.dp)
                    ) {
                        Text(
                            text = "- « Quels sont les impacts du Nerprun bourdaine sur la biodiversité ? »",
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                        Text(
                            modifier = Modifier.padding(top = 50.dp),
                            text = "- « Quel est le nom scientifique de l'alliaire officinale ? »",
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }

                }

            }
            LazyColumn(modifier = Modifier.padding(top = 160.dp, bottom = 65.dp)) {
                items(10) {
                    ChatBubble(Message("Hello, world from user!", "testUser"))
                    ChatBubble(Message("Hello, world from Ivy!", "Ivy"))
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.65f),
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Clavarder") },
                    singleLine = false,
                    maxLines = 4,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { onTextSubmit(text) })
                )
                FilledTonalButton(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    onClick = { onTextSubmit(text) },
                ) {
                    Text("Envoyer")
                }
            }
        }
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