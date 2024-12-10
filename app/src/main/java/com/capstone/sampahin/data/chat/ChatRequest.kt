package com.capstone.sampahin.data.chat

import com.google.gson.annotations.SerializedName

data class ChatRequest(
    @field: SerializedName("topic")
    val topic: String,

    @field: SerializedName("question")
    val question: String? = null
)