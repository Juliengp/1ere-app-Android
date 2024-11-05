package com.example.premiereappli

class TmdbMovieResult(
    var page: Int = 0,
    val results: List<TmdbMovie> = listOf())

class Genre {
    val id: Int = 0
    val name: String = ""
}

class TmdbMovie(
    var overview: String = "",
    val release_date: String = "",
    val id: String = "",
    val title: String = "",
    val original_title: String = "",
    val backdrop_path: String? = "",
    val genre_ids: List<Int> = listOf(),
    val poster_path: String? = "",
    val genres: List<Genre> = listOf()
)


class TmdbActorResult(
    val results: List<TmdbActor> = listOf()
)

class TmdbActor(
    val id: Int,
    val name: String,
    val original_name: String,
    val profile_path: String?,
    val known_for: List<TmdbMovie> = listOf(),
    val biography: String = "",
    val birthday: String = "",
    val deathday: String = ""
)

class TmdbSeriesResult(
    val results: List<TmdbSeries> = listOf()
)

class TmdbSeries(
    val id: String,
    val name: String,
    val poster_path: String?,
    val first_air_date: String,
    val overview: String = "",
    val backdrop_path: String? = "",
    val genres: List<Genre> = listOf()
)

class TmdbCrew(
    val id: Int,
    val name: String,
    val job: String,
    val profile_path: String?
)

class MovieCreditsResponse(
    val id: Int,
    val cast: List<TmdbActor>,
    val crew: List<TmdbCrew>
)

class SeriesCreditsResponse(
    val id: Int,
    val cast: List<TmdbActor>,
    val crew: List<TmdbCrew>
)

class ActorMoviesResponse(
    val id: Int,
    val cast: List<TmdbMovie>,
    val crew: List<TmdbCrew>
)

class ActorSeriesResponse(
    val id: Int,
    val cast: List<TmdbSeries>,
    val crew: List<TmdbCrew>
)


