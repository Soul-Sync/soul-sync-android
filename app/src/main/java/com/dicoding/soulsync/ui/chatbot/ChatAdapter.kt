package com.dicoding.soulsync.ui.chatbot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.soulsync.R

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private val chatList = mutableListOf<ChatItem>()

    fun addChat(chatItem: ChatItem) {
        chatList.add(chatItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_bubble, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(chatList[position])
    }

    override fun getItemCount(): Int = chatList.size

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textMessage: TextView = itemView.findViewById(R.id.textMessage)
        private val textAnswer: TextView = itemView.findViewById(R.id.textAnswer)

        fun bind(chatItem: ChatItem) {
            if (chatItem.isUser) {
                textMessage.visibility = View.VISIBLE
                textAnswer.visibility = View.GONE
                textMessage.text = chatItem.message
            } else {
                textMessage.visibility = View.GONE
                textAnswer.visibility = View.VISIBLE
                textAnswer.text = chatItem.message
            }
        }
    }
}
