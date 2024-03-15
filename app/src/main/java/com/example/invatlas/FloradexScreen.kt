package com.example.invatlas

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.invatlas.viewmodels.PlantViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FloradexScreen(vm: PlantViewModel) {
    Text(text = "Floradex")

    LaunchedEffect(Unit, block = {
        vm.getAllPlants()
    })

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(vm.plantList.size) { plant ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .size(width = 240.dp, height = 100.dp)
            ) {
                Text(
                    text = vm.plantList[plant].name,
                    modifier = Modifier
                        .padding(4.dp),
                    textAlign = TextAlign.Center,
                )
                println(vm.plantList[plant].imgPath)
                AsyncImage(
                    model = "http://10.0.2.2:5000/reference/" + vm.plantList[plant].imgPath,
                    contentDescription = "Hello guys",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .requiredSize(200.dp)
                )
            }

        }
    }

}