package com.example.premiereappli

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {
    val movies = MutableStateFlow<List<TmdbMovie>>(listOf())
    val movieDetails = MutableStateFlow<TmdbMovie?>(null)
    val actors = MutableStateFlow<List<TmdbActor>>(listOf())
    val series = MutableStateFlow<List<TmdbSeries>>(listOf())
    val serieDetails = MutableStateFlow<TmdbSeries?>(null)
    val actorDetails = MutableStateFlow<TmdbActor?>(null)
    val actorMovies = MutableStateFlow<List<TmdbMovie>>(listOf())
    val actorSeries = MutableStateFlow<List<TmdbSeries>>(listOf())
    val moviessearchQuery = MutableStateFlow("")
    val seriessearchQuery = MutableStateFlow("")
    val actorsserachQuery = MutableStateFlow("")
    private val api: Api
    private val api_key = "317519a83cc36ab9367ba50e5aa75b40"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
        getMovies()
        getActors()
        getSeries()

        viewModelScope.launch {
            moviessearchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotEmpty()) {
                        try {
                            searchMovies(query)
                        } catch (e: Exception) {
                            Log.e("MainViewModel", "searchMovies: ${e.message}")
                            movies.value = emptyList()
                        }
                    } else {
                        getMovies()
                    }
                }
        }

        viewModelScope.launch {
            seriessearchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotEmpty()) {
                        try {
                            searchSeries(query)
                        } catch (e: Exception) {
                            Log.e("MainViewModel", "searchSeries: ${e.message}")
                            series.value = emptyList()
                        }
                    } else {
                        getSeries()
                    }
                }
        }

        viewModelScope.launch {
            actorsserachQuery
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotEmpty()) {
                        try {
                            searchActors(query)
                        } catch (e: Exception) {
                            Log.e("MainViewModel", "searchActors: ${e.message}")
                            actors.value = emptyList()
                        }
                    } else {
                        getActors()
                    }
                }
        }
    }

    fun resetActors() {
        viewModelScope.launch {
            actors.value = api.lastActors(api_key, "fr").results
        }
    }

    fun getMovies(language: String = "fr") {
        viewModelScope.launch {
            val response = api.lastmovies(api_key, language)
            movies.value = response.results
        }
    }

    fun getActors(language: String = "fr") {
        viewModelScope.launch {
            actors.value = api.lastActors(api_key, language).results
        }
    }

    fun getSeries(language: String = "fr") {
        viewModelScope.launch {
            series.value = api.lastSeries(api_key, language).results
        }
    }

    fun searchMovies(searchtext: String) {
        viewModelScope.launch {
            try {
                val response = api.searchmovies(api_key, searchtext)
                movies.value = response.results
            } catch (e: Exception) {
                Log.e("MainViewModel", "searchMovies: ${e.message}")
                movies.value = emptyList()
            }
        }
    }

    fun searchSeries(searchtext: String) {
        viewModelScope.launch {
            try {
                val response = api.searchseries(api_key, searchtext)
                series.value = response.results
            } catch (e: Exception) {
                Log.e("MainViewModel", "searchSeries: ${e.message}")
                series.value = emptyList()
            }
        }
    }

    fun searchActors(searchtext: String) {
        viewModelScope.launch {
            try {
                val response = api.searchactors(api_key, searchtext)
                actors.value = response.results
            } catch (e: Exception) {
                Log.e("MainViewModel", "searchActors: ${e.message}")
                actors.value = emptyList()
            }
        }
    }

    fun getMovieDetails(movieId: String, language: String = "fr") {
        viewModelScope.launch {
            val movie = api.movieDetails(movieId, api_key, language)
            movieDetails.value = movie
        }
    }

    fun getSerieDetails(serieId: String, language: String = "fr") {
        viewModelScope.launch {
            val serie = api.seriesDetails(serieId, api_key, language)
            serieDetails.value = serie
        }
    }

    fun getActorDetails(actorId: Int, language: String = "fr") {
        viewModelScope.launch {
            val actor = api.actorDetails(actorId.toString(), api_key, language)
            actorDetails.value = actor
        }
    }

    fun getMovieActors(movieId: String, language: String = "fr") {
        viewModelScope.launch {
            val response = api.movieActors(movieId, api_key, language)
            actors.value = response.cast
        }
    }

    fun getSeriesActors(tvId: String, language: String = "fr") {
        viewModelScope.launch {
            val response = api.seriesActors(tvId, api_key, language)
            actors.value = response.cast
        }
    }

    fun getActorMovies(actorId: Int, language: String = "fr") {
        viewModelScope.launch {
            val response = api.actorMovies(actorId.toString(), api_key, language)
            actorMovies.value = response.cast
        }
    }

    fun getActorSeries(actorId: Int, language: String = "fr") {
        viewModelScope.launch {
            val response = api.actorSeries(actorId.toString(), api_key, language)
            actorSeries.value = response.cast
        }
    }
}