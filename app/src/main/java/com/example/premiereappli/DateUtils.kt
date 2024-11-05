package com.example.premiereappli

import java.text.SimpleDateFormat
import java.util.Locale

fun DateFormat(dateString: String?): String {
    if (dateString.isNullOrEmpty()) {
        return "Unknown date"
    }
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        outputFormat.format(date)
    } catch (e: Exception) {
        "Invalid date"
    }
}