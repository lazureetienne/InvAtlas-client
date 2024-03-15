package com.example.invatlas

import retrofit2.Call
import retrofit2.http.GET

interface PlantAPI {
    @GET("allplants")
    fun getAllPlants(): Call<List<Plant>>
}