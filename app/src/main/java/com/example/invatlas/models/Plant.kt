package com.example.invatlas.models;

import com.fasterxml.jackson.annotation.JsonProperty

data class Plant(
    @JsonProperty("name") val name: String,
    @JsonProperty("img_path") val imgPath: String,
    @JsonProperty("code") val code: String,
    @JsonProperty("is_invasive") val isInvasive: Boolean
)

