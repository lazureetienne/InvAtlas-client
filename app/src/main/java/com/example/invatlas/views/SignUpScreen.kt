package com.example.invatlas.views


import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.Navigation.findNavController
import com.example.invatlas.navigation.InvAtlasDestinations
import com.example.invatlas.viewmodels.PlantViewModel

@Composable
fun SignUpScreen(vm: PlantViewModel, nav: NavHostController) {
    var user by remember { mutableStateOf("") }
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {

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
                Text("Sign in")
            }
        }
    }
}