package com.example.premiereappli

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState

@Composable
fun HorreurScreen(viewModel: MainViewModel) {
    val collectionResults = viewModel.collection.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getCollection("horror")
    }

    LazyColumn{
        collectionResults?.results?.let { collections ->
            items(collections) { collection ->
                Text(text = collection.name)
            }
        }
            ?:item {
                Text("No collection found")
        }
    }

}