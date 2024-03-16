package com.example.invatlas

import com.example.invatlas.models.IdentifyRequestBody
import com.example.invatlas.models.Plant
import com.example.invatlas.models.User
import com.example.invatlas.models.UserPlant
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query


interface PlantAPI {
    @GET("allplants")
    fun getAllPlants(): Call<List<Plant>>

    @GET("userplants")
    fun getUserPlants(@Query("user") user: String): Call<List<UserPlant>>

    @GET("authenticate")
    fun authenticate(@Query("user") user: String): Call<User>

    @GET("randomplant")
    fun randomPlant(): Call<Plant>

    @Headers("Content-Type: application/json")
    @POST("identify")
    fun identify(@Body body: IdentifyRequestBody): Call<UserPlant>
}