package com.example.premiereappli

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("trending/movie/week")
    suspend fun lastmovies(@Query("api_key") api_key: String,
                           @Query("language") language: String):TmdbMovieResult


    @GET("search/movie")
    suspend fun searchmovies(@Query("api_key") api_key: String,
                             @Query("query") searchtext: String): TmdbMovieResult

    @GET("search/tv")
    suspend fun searchseries(@Query("api_key") api_key: String,
                             @Query("query") searchtext: String): TmdbSeriesResult

    @GET("search/person")
    suspend fun searchactors(@Query("api_key") api_key: String,
                             @Query("query") searchtext: String): TmdbActorResult


    @GET("movie/{movie_id}")
    suspend fun movieDetails(@Path("movie_id") movieId: String,
                             @Query("api_key") api_key: String,
                             @Query("language") language: String): TmdbMovie
    @GET("trending/person/week")
    suspend fun lastActors(@Query("api_key") api_key: String,
                          @Query("language") language: String): TmdbActorResult

    @GET("trending/tv/week")
    suspend fun lastSeries(@Query("api_key") api_key: String,
                          @Query("language") language: String): TmdbSeriesResult

    @GET("tv/{tv_id}")
    suspend fun seriesDetails(@Path("tv_id") tvId: String,
                          @Query("api_key") api_key: String,
                          @Query("language") language: String): TmdbSeries

    @GET("person/{person_id}")
    suspend fun actorDetails(@Path("person_id") personId: String,
                            @Query("api_key") api_key: String,
                            @Query("language") language: String): TmdbActor

    @GET("movie/{movie_id}/credits")
    suspend fun movieActors(@Path("movie_id") movieId: String,
                            @Query("api_key") api_key: String,
                            @Query("language") language: String): MovieCreditsResponse

    @GET("tv/{tv_id}/credits")
    suspend fun seriesActors(@Path("tv_id") tvId: String,
                            @Query("api_key") api_key: String,
                            @Query("language") language: String): SeriesCreditsResponse

    @GET("person/{person_id}/movie_credits")
    suspend fun actorMovies(@Path("person_id") personId: String,
                            @Query("api_key") api_key: String,
                            @Query("language") language: String): ActorMoviesResponse

    @GET("person/{person_id}/tv_credits")
    suspend fun actorSeries(@Path("person_id") personId: String,
                            @Query("api_key") api_key: String,
                            @Query("language") language: String): ActorSeriesResponse

    @GET("search/collection")
    suspend fun searchCollections(@Query("api_key") api_key: String,
                                  @Query("query") searchtext: String): CollectionResults

}