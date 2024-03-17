package com.example.invatlas.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.invatlas.PlantAPI
import com.example.invatlas.RetrofitClient
import com.example.invatlas.models.AskRequestBody
import com.example.invatlas.models.IdentifyRequestBody
import com.example.invatlas.models.Plant
import com.example.invatlas.models.User
import com.example.invatlas.models.UserPlant
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class PlantViewModel : ViewModel() {
    private var _plantList = mutableStateListOf<Plant>()
    private var _userPlant: UserPlant? = null
    private var _userPlants = mutableStateListOf<UserPlant>()
    private var _user: User? = null
    private var _randomPlant: Plant? = null
    private var _askResponse: String? = null

    var errorMessage: String by mutableStateOf("")
    val plantList: List<Plant>
        get() = _plantList
    var userPlant : UserPlant? = null
        get() = _userPlant
    val userPlants: List<UserPlant>
        get() = _userPlants
    var sessionUser: User? = null
        get() = _user
    var randomPlant: Plant? = null
        get() = _randomPlant
    var askResponse: String? = null
        get() = _askResponse

    fun getAllPlants() {
        viewModelScope.launch {
            try {
                val retrofit = RetrofitClient.getClient()
                val userApi = retrofit.create(PlantAPI::class.java)
                val plantResponse = userApi.getAllPlants().execute()
                _plantList.clear()
                _plantList.addAll(plantResponse.body().orEmpty())

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getUserPlants() {
        if (sessionUser?.name == null) {
            throw Exception("Unable to authenticate")
        }
        viewModelScope.launch {
            try {
                val retrofit = RetrofitClient.getClient()
                val userApi = retrofit.create(PlantAPI::class.java)
                val plantResponse = userApi.getUserPlants(sessionUser?.name!!).execute()
                _userPlants.clear()
                _userPlants.addAll(plantResponse.body().orEmpty())

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun authenticate(user: String) {
        viewModelScope.launch {
            try {
                val retrofit = RetrofitClient.getClient()
                val userApi = retrofit.create(PlantAPI::class.java)
                val plantResponse = userApi.authenticate(user!!).execute()
                _user = plantResponse.body()

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun identifyPlant(lat: Double, long: Double, file: String) {
        if (sessionUser?.name == null) {
            throw Exception("Unable to authenticate")
        }
        viewModelScope.launch {
            try {
                val retrofit = RetrofitClient.getClient()
                val userApi = retrofit.create(PlantAPI::class.java)
                val plantResponse = userApi.identify(IdentifyRequestBody(sessionUser!!.name, lat, long, file)).execute()
                _userPlant = plantResponse.body()

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getRandomPlant() {
        viewModelScope.launch {
            try {
                val retrofit = RetrofitClient.getClient()
                val userApi = retrofit.create(PlantAPI::class.java)
                val plantResponse = userApi.randomPlant().execute()
                _randomPlant = plantResponse.body()

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun ask(plantCode: String, prompt: String) {
        viewModelScope.launch {
            try {
                val retrofit = RetrofitClient.getClient()
                val userApi = retrofit.create(PlantAPI::class.java)
                val plantResponse = userApi.ask(AskRequestBody(plantCode, prompt)).execute()
                _askResponse = plantResponse.body()
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }


}
