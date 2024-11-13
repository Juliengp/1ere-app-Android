package com.example.premiereappli

data class CollectionResults(
    val page: Int,
    val results: List<Collection>,
    val total_pages: Int,
    val total_results: Int
)