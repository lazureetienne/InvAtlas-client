package com.example.invatlas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
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
    }

    data class NavigationItem(
        val title: String,
        @DrawableRes val iconId: Int,
        @DrawableRes val selectedIconId: Int
    )

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        // TODO
    }
}