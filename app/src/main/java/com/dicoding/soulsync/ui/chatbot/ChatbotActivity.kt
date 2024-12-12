package com.dicoding.soulsync.ui.chatbot

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import com.dicoding.soulsync.R

class ChatbotActivity : AppCompatActivity() {

    private lateinit var chatAdapter: ChatAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextInput: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var chat: Chat

    // Letakkan API Key di sini
    private val apiKey = "YOU_API_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        recyclerView = findViewById(R.id.recyclerView)
        editTextInput = findViewById(R.id.editTextInput)
        sendButton = findViewById(R.id.btnSend)

        recyclerView.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter()
        recyclerView.adapter = chatAdapter

        initChat()

        sendButton.setOnClickListener {
            val userMessage = editTextInput.text.toString()
            if (userMessage.isNotEmpty()) {
                addChatToAdapter(userMessage, true)
                editTextInput.setText("")
                MainScope().launch {
                    val botResponse = sendMessageToGemini(userMessage)
                    addChatToAdapter(botResponse, false)
                }
            }
        }
    }

    private fun initChat() {
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = apiKey // Gunakan API key langsung
        )

        chat = generativeModel.startChat(
            history = listOf(
                content(role = "user") { text("Hello") },
                content(role = "model") { text("Hello! How can I assist you today?") }
            )
        )

        addChatToAdapter("Hello", true)
        addChatToAdapter("Hello! How can I assist you today?", false)
    }

    private suspend fun sendMessageToGemini(userMessage: String): String {
        return try {
            val response = chat.sendMessage(userMessage)
            response.text ?: "Sorry, I couldn't process your message."
        } catch (e: Exception) {
            "An error occurred: ${e.message}"
        }
    }

    private fun addChatToAdapter(message: String, isUser: Boolean) {
        val chatItem = ChatItem(message, isUser)
        chatAdapter.addChat(chatItem)
        recyclerView.scrollToPosition(chatAdapter.itemCount - 1)
    }
}
