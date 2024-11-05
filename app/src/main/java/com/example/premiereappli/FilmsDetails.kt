package com.example.premiereappli

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.unit.Dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextField
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import androidx.compose.ui.Alignment

@Composable
fun FilmsDetails(filmId: String, viewModel: MainViewModel, navController: NavController) {
    viewModel.getMovieDetails(filmId)
    viewModel.getMovieActors(filmId)
    val movie = viewModel.movieDetails.collectAsState().value
    val configuration = LocalConfiguration.current
    val actors = viewModel.actors.collectAsState().value
    val compact = configuration.screenWidthDp < configuration.screenHeightDp
    val nbcolumn = if (compact) 2 else 4


    movie?.let {
        val genreNames = it.genres.joinToString { genre -> genre.name }
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Box(modifier = Modifier
                        .size(400.dp)

                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${it.backdrop_path}",
                        contentDescription = "Background Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    )
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${it.poster_path}",
                        contentDescription = it.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(225.dp)
                            .height(300.dp)
                            .align(Alignment.Center)
                            .border(width = 4.dp, color = Color.White)
                    )
                }
                Text(
                    text = it.title,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(top = 16.dp)

                )
                Text(
                    text = genreNames,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(top = 8.dp)

                )
                Text(
                    text = it.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(top = 8.dp)

                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Acteurs",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                )
            }
            items(actors.chunked(nbcolumn)) { actorPair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    actorPair.forEach { actor ->
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                                .clickable {
                                    navController.navigate("actorDetails/${actor.id}")
                                },
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w500${actor.profile_path ?: ""}",
                                    contentDescription = actor.name,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = actor.name ?: "Acteur inconnu",
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}