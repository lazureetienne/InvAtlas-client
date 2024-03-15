package com.example.invatlas.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.invatlas.Plant
import com.example.invatlas.PlantAPI
import com.example.invatlas.RetrofitClient
import kotlinx.coroutines.launch

class PlantViewModel : ViewModel() {
    private var _plantList = mutableStateListOf<Plant>()
    var errorMessage: String by mutableStateOf("")
    val plantList: List<Plant>
        get() = _plantList

    fun getAllPlants() {
        viewModelScope.launch {
            try {
                val retrofit = RetrofitClient.getClient()
                val userApi = retrofit.create(PlantAPI::class.java)
                val plantResponse = userApi.getAllPlants().execute()
                _plantList.addAll(plantResponse.body().orEmpty())

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}
