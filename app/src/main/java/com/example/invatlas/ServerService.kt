package com.example.invatlas

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ServerService {
    suspend fun listenToServer(): Message? {
        val url = URL("http://localhost:8080/") // TODO: change to actual server

        return withContext(Dispatchers.IO) {
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val streamReader = InputStreamReader(connection.inputStream)
                val reader = BufferedReader(streamReader)

                val response = reader.use { it.readText() }

                val json = Json { ignoreUnknownKeys = true }
                val jsonObject = json.parseToJsonElement(response).jsonObject

                val text = jsonObject["text"]?.jsonPrimitive?.content
                val author = jsonObject["author"]?.jsonPrimitive?.content

                if (text != null && author != null) {
                    return@withContext Message(text, author)
                }
            }

            return@withContext null
        }
    }
}