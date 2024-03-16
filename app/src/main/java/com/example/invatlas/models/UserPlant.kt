package com.example.invatlas.models

import com.fasterxml.jackson.annotation.JsonProperty

data class UserPlant(
    @JsonProperty("id") val id: Int,
    @JsonProperty("user") val user: String,
    @JsonProperty("plant") val plant: String,
    @JsonProperty("img_path") val img: String,
    @JsonProperty("long") val longitude: Double,
    @JsonProperty("lat") val latitude: Double,
)
