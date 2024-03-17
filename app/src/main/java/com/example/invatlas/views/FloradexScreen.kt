package com.example.invatlas.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.invatlas.viewmodels.PlantViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FloradexScreen(vm: PlantViewModel, nav: NavController) {
    LaunchedEffect(Unit, block = {
        vm.getAllPlants()
        vm.getUserPlants()
    })
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Plantes découvertes - ${vm.userPlants.size} / ${vm.plantList.size}",
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            fontWeight = FontWeight(700),
            fontSize = 20.sp
        )
        if (vm.userPlants.isEmpty()) {
            Text(
                text = "Temps d'aller explorer",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 192.dp),
                modifier = Modifier
                    .padding(5.dp)
            ) {
                items(vm.userPlants.size) { plant ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        ),
                        modifier = Modifier
                            .size(width = 240.dp, height = 128.dp)
                            .padding(5.dp),
                        onClick = {
                            nav.navigate("chat/${vm.userPlants[plant].code}/${vm.userPlants[plant].plant}")
                        }
                    ) {
                        AsyncImage(
                            model = "http://10.0.2.2:5000/reference/" + vm.userPlants[plant].img,
                            contentDescription = "Hello guys",
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.75f)
                                .align(Alignment.CenterHorizontally),
                            contentScale = ContentScale.Crop,
                        )
                        Text(
                            text = vm.userPlants[plant].plant,
                            modifier = Modifier
                                .padding(4.dp),
                            textAlign = TextAlign.Center,
                        )

                    }
                }
            }
        }
        Text(
            text = "Plantes à découvrir - ${vm.plantList.size - vm.userPlants.size} / ${vm.plantList.size}",
            fontWeight = FontWeight(700),
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 192.dp),
            modifier = Modifier
                .padding(5.dp)
        ) {
            items(vm.plantList.size) { plant ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    modifier = Modifier
                        .size(width = 240.dp, height = 128.dp)
                        .padding(5.dp)
                ) {
                    AsyncImage(
                        model = "http://10.0.2.2:5000/reference/" + vm.plantList[plant].imgPath,
                        contentDescription = "Hello guys",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.75f)
                            .align(Alignment.CenterHorizontally)
                            .blur(
                                radiusX = 10.dp,
                                radiusY = 10.dp,
                                edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))
                            ),
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        text = "Non découvert",
                        modifier = Modifier
                            .padding(4.dp),
                        textAlign = TextAlign.Center,
                    )

                }
            }
        }
    }

}
