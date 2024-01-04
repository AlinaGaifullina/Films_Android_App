package ru.itis.filmsandroidapp.core.network.model

import com.google.gson.annotations.SerializedName

data class FilmResponse (
    @SerializedName("page")
    val numberPage: Int,
    @SerializedName("limit")
    val numberFilmsOnPage: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("docs")
    val filmsList: List<FilmInfo>? = null,
)

data class FilmInfo(
    @SerializedName("id")
    val filmId: Int,
    @SerializedName("name")
    val filmName: String?,
    @SerializedName("year")
    val filmYear: String?,
    @SerializedName("poster")
    val posterInfo: PosterInfo,
    @SerializedName("shortDescription")
    val shortDescription: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("rating")
    val rating: RatingInfo,
    @SerializedName("genres")
    val genres: List<GenreInfo>?
)

data class PosterInfo(
    @SerializedName("url")
    val posterUrl: String?,
    @SerializedName("previewUrl")
    val posterPreviewUrl: String?,
)

data class GenreInfo(
    @SerializedName("name")
    val genreName: String?,
)

data class RatingInfo(
    @SerializedName("kp")
    val ratingKp: String?,
    @SerializedName("imdb")
    val ratingImdb: String?,
    @SerializedName("filmCritics")
    val ratingFilmCritics: String?,
    @SerializedName("russianFilmCritics")
    val ratingRussianFilmCritics: String?,
    @SerializedName("await")
    val ratingAwait: String?,
)
