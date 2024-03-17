package com.example.invatlas.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Parcs(
    @JsonProperty("parcs") val parcs: List<Parc>
) {
}
