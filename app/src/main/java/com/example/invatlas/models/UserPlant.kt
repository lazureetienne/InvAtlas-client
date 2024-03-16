package com.example.invatlas.models

data class UserPlant(
    var id: Int,
    var user: Int,
    var plant: Int,
    var img: String,
    var longitude: Double,
    var latitude: Double,
) {
    override fun toString(): String {
        return "Plante test" // TODO: Replace with actual plant names based on id
    }
}
