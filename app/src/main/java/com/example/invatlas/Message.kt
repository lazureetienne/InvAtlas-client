package com.example.invatlas

data class Message (val text: String, val author : String) {
    val isFromUser: Boolean
        get() = author == "testUser" // TODO: change this to include user from Auth service singleton
}
