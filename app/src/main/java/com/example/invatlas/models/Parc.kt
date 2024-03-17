package com.example.invatlas.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Parc(
    @JsonProperty("lat") val lat: Double,
    @JsonProperty("long") val long: Double,
)
