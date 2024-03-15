package com.example.invatlas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun IvyScreen() {
    var text by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<Message>()) }
    val user = User("testUser", "testUser", 0) // TODO: fetch user from auth service

    val onTextSubmit: (String) -> Unit = { submittedText ->
        // TODO: Send to server
        messages =
            messages + Message(submittedText, "testUser") // TODO: fetch user from auth service
        // TODO: Get response from server (async)
//        listenToServer(user) { response ->
//            messages = messages + Message(response, "Ivy")
//        }
        text = "" // Clear the text
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp, bottom = 90.dp)
    ) {
        BoxWithConstraints(modifier = Modifier.align(Alignment.TopCenter)) {
            Box(
                modifier = Modifier
                    .widthIn(max = maxWidth * 0.80f, min = maxWidth * 0.80f)
                    .height(150.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(5.dp),
            ) {
                Text(
                    text = "Parle avec Ivy !",
                    modifier = Modifier.align(Alignment.TopCenter),
                    fontWeight = FontWeight(700),
                    color = MaterialTheme.colorScheme.onTertiary
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 5.dp, top = 28.dp)
                ) {
                    Text(
                        text = "- « Quels sont les impacts du Nerprun bourdaine sur la biodiversité ? »",
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                    Text(
                        modifier = Modifier.padding(top = 50.dp),
                        text = "- « Quel est le nom scientifique de l'alliaire officinale ? »",
                        color = MaterialTheme.colorScheme.onTertiary
                    )
                }

            }

        }
        LazyColumn(modifier = Modifier.padding(top = 160.dp, bottom = 65.dp)) {
            items(messages.size) {
                ChatBubble(messages[it])
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.65f),
                value = text,
                onValueChange = { text = it },
                label = { Text("Clavarder") },
                singleLine = false,
                maxLines = 4,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { onTextSubmit(text) })
            )
            FilledTonalButton(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clip(RoundedCornerShape(10.dp)),
                onClick = { onTextSubmit(text) },
            ) {
                Text("Envoyer")
            }
        }
    }
}

// inspired by https://medium.com/@meytataliti/building-a-simple-chat-app-with-jetpack-compose-883a240592d4
@Composable
fun ChatBubble(message: Message) {
    Column(
        horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start,
    ) {
        Text(
            modifier = Modifier
                .align(if (message.isFromUser) Alignment.End else Alignment.Start)
                .padding(horizontal = 15.dp),
            text = message.author,
            style = MaterialTheme.typography.bodySmall
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start
        ) {
            BoxWithConstraints {
                Box(
                    modifier = Modifier
                        .widthIn(max = maxWidth * 0.5f)
                        .clip(
                            RoundedCornerShape(
                                topStart = 48f,
                                topEnd = 48f,
                                bottomStart = if (message.isFromUser) 48f else 0f,
                                bottomEnd = if (message.isFromUser) 0f else 48f
                            )
                        )
                        .background(if (message.isFromUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
                        .padding(16.dp)
                ) {
                    Text(text = message.text)
                }
            }
        }
    }

}