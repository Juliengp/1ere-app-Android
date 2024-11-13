package com.example.premiereappli

data class collection(
    val page: Int,
    val results: List<CollectionResult>,
    val total_pages: Int,
    val total_results: Int
)