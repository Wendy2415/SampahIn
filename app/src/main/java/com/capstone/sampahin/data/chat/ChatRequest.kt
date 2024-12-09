package com.capstone.sampahin.data.chat

import com.google.gson.annotations.SerializedName

class ChatRequest {
    @SerializedName("topic")
    val topic: String? = null

    @SerializedName("question")
    val question: String? = null
}