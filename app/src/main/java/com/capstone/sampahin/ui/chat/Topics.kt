package com.capstone.sampahin.ui.chat

import com.google.gson.annotations.SerializedName

/**
 *  Topics data class not used anymore in this project cause the data has been moved to the server
 *  Keep this data class for reference local JSON parsing
 */

data class Topics (
    @SerializedName("titles")
    private val titles: List<List<String>>,
    @SerializedName("contents")
    private val contents: List<List<String>>,
    @SerializedName("questions")
    val questions: List<List<String>>
) {
    fun getTitles(): List<String> {
        return titles.map { it[0] }
    }

    fun getContents(): List<String> {
        return contents.map { it[0] }
    }
}

