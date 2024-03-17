package com.example.invatlas.views


import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.Navigation.findNavController
import coil.compose.AsyncImage
import com.example.invatlas.navigation.InvAtlasDestinations
import com.example.invatlas.viewmodels.PlantViewModel
import kotlinx.coroutines.time.delay
import java.time.Duration

@Composable
fun SignUpScreen(vm: PlantViewModel, nav: NavHostController) {
    var user by remember { mutableStateOf("") }
    var path by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(Unit, block = {
        vm.getRandomPlant()
        path = vm.randomPlant?.imgPath ?: "images/reference/solidago_canadensis.jpg"
    })

    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                model = "http://10.0.2.2:5000/reference/$path",
                contentDescription = "Hello guys",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(50.dp)),

                )
            OutlinedTextField(
                value = user,
                onValueChange = { user = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    try {
                        vm.authenticate(user)
                        if (vm.sessionUser == null) {
                            throw Exception("User doesn't exist")
                        }
                        nav.navigate(InvAtlasDestinations.MAP_ROUTE)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Unable to sign in", Toast.LENGTH_SHORT).show()
                        println(e.message.toString())
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Sign in",
                )
            }
        }
    }
}