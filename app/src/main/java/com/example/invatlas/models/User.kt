package com.example.invatlas.models

import com.fasterxml.jackson.annotation.JsonProperty

data class User(
    @JsonProperty("name") val name: String,
    @JsonProperty("xp") val xp: Int,
)
