package ru.itis.filmsandroidapp.feature.profile.api.model

import ru.itis.filmsandroidapp.core.network.model.FilmInfoEntity

data class ShortFilmInfoModel(
    val filmId: Int,
    val filmName: String,
    val filmYear: String,
    val shortDescription: String,
    val rating: String,
    val posterInfo: String
)

fun FilmInfoEntity.mapShortFilmInfoModel(): ShortFilmInfoModel {
    return ShortFilmInfoModel(
        filmId = this.filmId,
        filmName = this.filmName ?: " ",
        filmYear = this.filmYear ?: " ",
        shortDescription = this.shortDescription ?: "",
        rating = this.rating.ratingKp,
        posterInfo = this.posterInfo.posterPreviewUrl
    )
}