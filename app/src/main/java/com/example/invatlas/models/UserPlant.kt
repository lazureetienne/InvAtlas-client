package com.example.invatlas.models

import com.fasterxml.jackson.annotation.JsonProperty

data class UserPlant(
    @JsonProperty("id") val id: Int,
    @JsonProperty("user") val user: String,
    @JsonProperty("plant") val plant: String,
    @JsonProperty("img_path") val img: String,
    @JsonProperty("long") val longitude: Double,
    @JsonProperty("lat") val latitude: Double,
    @JsonProperty("name") val plantName: String,
    @JsonProperty("img_path_1") val referencePath: String,
    @JsonProperty("code") val code: String,
    @JsonProperty("is_invasive") val isInvasive: Boolean,
)
