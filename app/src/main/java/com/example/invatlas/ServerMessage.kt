package com.example.invatlas

import com.fasterxml.jackson.annotation.JsonProperty

data class ServerMessage(
    @JsonProperty("message") val message: String,
    @JsonProperty("sender") val sender: String
)
