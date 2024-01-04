package ru.itis.filmsandroidapp.core.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.itis.filmsandroidapp.core.network.model.FilmInfo
import ru.itis.filmsandroidapp.core.network.model.FilmResponse

interface FilmsApiService {

    @GET("movie/{id}")
    suspend fun getFilmById(
        @Path("id") filmId: Int
    ): FilmInfo

    @GET("movie")
    suspend fun getAllFilms(
        @Query("page") numberPage: Int,
        @Query("limit") numberFilmsOnPage: Int,
        @Query("selectFields") selectFields: List<String>,
    ): FilmResponse
}