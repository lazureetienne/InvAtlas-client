package com.example.invatlas.models

data class IdentifyRequestBody(
    val user: String,
    val long: Double,
    val lat: Double,
    val file: String
)