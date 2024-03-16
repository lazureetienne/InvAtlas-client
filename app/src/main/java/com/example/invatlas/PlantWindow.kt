package com.example.invatlas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.invatlas.models.UserPlant

@Composable
fun PlantWindow(plant: UserPlant) {
    Card(modifier = Modifier.fillMaxSize(0.6f)) {
//        FloatingActionButton(
//            onClick = onClose,
//            modifier = Modifier
//                .align(Alignment.End)
//                .size(24.dp),
//            containerColor = MaterialTheme.colorScheme.secondary
//        ) {
//            Icon(
//                modifier = Modifier.align(Alignment.CenterHorizontally),
//                painter = painterResource(R.drawable.baseline_close_24),
//                contentDescription = "Close"
//            )
//        }
        Text(
            text = plant.plant.toString(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(10.dp))
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.alliaria_petiolata),
                contentDescription = "Plante test"
            )
        }
        Text(
            text = "Latitude: ${plant.latitude}",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp, start = 4.dp)
        )
        Text(
            text = "Longitude: ${plant.longitude}",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp, start = 4.dp)
        )
        Text(
            text = "User: ${plant.user}",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp, start = 4.dp)
        )
        Text(
            text = "ID: ${plant.id}",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp, start = 4.dp)
        )
        Text(
            text = "Terrain: urbain",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 16.dp, start = 4.dp)
        )
    }
}