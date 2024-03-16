package com.example.invatlas

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.invatlas.views.InvAtlasApp
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass

class MainActivity : ComponentActivity() {
    private val INITIAL_PERMS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.CAMERA
    )

    private val INITIAL_REQUEST = 1337

    private fun notHasPermissions(): Boolean {
        return INITIAL_PERMS.any {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, INITIAL_PERMS, INITIAL_REQUEST)
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        // TODO: Fetch user from server.
        if (notHasPermissions()) {
            requestPermissions() // TODO: handle permissions BEFORE showing the map.
        }
        setContent {
            InvAtlasApp()

        }
    }
}