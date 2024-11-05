package com.example.premiereappli

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ActeursScreen(navController: NavHostController, viewModel: MainViewModel = viewModel()) {
    val actors = viewModel.actors.collectAsState().value
    val configuration = LocalConfiguration.current
    val searchQuery = viewModel.actorsserachQuery.collectAsState().value
    val compact = configuration.screenWidthDp < configuration.screenHeightDp
    val nbcolumn = if (compact) 2 else 4

    LaunchedEffect(Unit){
        viewModel.getActors()
    }

    Column {
        TextField(
            value = searchQuery,
            onValueChange = { query ->
                viewModel.actorsserachQuery.value = query
            },
            label = { Text("Rechercher un acteur") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

    LazyVerticalGrid(
        columns = GridCells.Fixed(nbcolumn),
        modifier = Modifier.fillMaxSize().padding(8.dp)
    ) {
        items(actors.size) { index ->
            val actor = actors[index]
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable { navController.navigate("actorDetails/${actor.id}") }
                    .then(if (nbcolumn == 4) Modifier.size(width = 100.dp, height = 200.dp) else Modifier.fillMaxSize()),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier
                          .padding(8.dp)
                          .align(Alignment.CenterHorizontally)
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${actor.profile_path ?: ""}",
                        contentDescription = actor.name,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.CenterHorizontally)
                            .then(if (nbcolumn == 4) Modifier.size(150.dp) else Modifier.fillMaxSize())
                    )
                    Text(text = actor.name, modifier = Modifier.weight(1f) .align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}}