package com.example.premiereappli

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@Composable
fun ActeursDetails(actorId: Int, viewModel: MainViewModel, navController: NavController) {
    viewModel.getActorDetails(actorId)
    viewModel.getActorMovies(actorId)
    viewModel.getActorSeries(actorId)
    val actor = viewModel.actorDetails.collectAsState().value
    val movies = viewModel.actorMovies.collectAsState().value
    val series = viewModel.actorSeries.collectAsState().value
    val configuration = LocalConfiguration.current
    val compact = configuration.screenWidthDp < configuration.screenHeightDp
    val nbcolumn = if (compact) 2 else 4

    actor?.let {
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${it.profile_path ?: ""}",
                        contentDescription = it.name,
                        modifier = Modifier
                            .size(200.dp)
                    )
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Text(
                        text = it.birthday ?: "Date de naissance inconnue",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = it.biography.ifEmpty{"Biographie non disponible"},
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Films",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            items(movies.chunked(nbcolumn)) { moviePair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    moviePair.forEach { movie ->
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                                .clickable { navController.navigate("filmDetails/${movie.id}")}
                                .then(if (nbcolumn == 4) Modifier.wrapContentHeight() else Modifier.fillMaxSize()),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w500${movie.poster_path ?: ""}",
                                    contentDescription = movie.title,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = movie.title ?: "Film inconnu",
                                        fontWeight = FontWeight.Bold,

                                        )
                                }
                            }
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Séries",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            items(series.chunked(nbcolumn)) { seriePair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    seriePair.forEach { serie ->
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                                .clickable {navController.navigate("serieDetails/${serie.id}")}
                                .then(if (nbcolumn == 4) Modifier.size(width = 100.dp, height = 200.dp) else Modifier.fillMaxSize()),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w500${serie.poster_path ?: ""}",
                                    contentDescription = serie.name,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = serie.name ?: "Série inconnue",
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