package com.example.invatlas.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.invatlas.viewmodels.PlantViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FloradexScreen(vm: PlantViewModel) {
    LaunchedEffect(Unit, block = {
        vm.getAllPlants()
    })

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 192.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, bottom = 90.dp)
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
                        .align(Alignment.CenterHorizontally),
                    contentScale = ContentScale.Crop,
                )
                Text(
                    text = vm.plantList[plant].name,
                    modifier = Modifier
                        .padding(4.dp),
                    textAlign = TextAlign.Center,
                )

            }

        }
    }

}