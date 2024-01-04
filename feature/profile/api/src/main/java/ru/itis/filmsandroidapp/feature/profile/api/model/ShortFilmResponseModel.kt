package ru.itis.filmsandroidapp.feature.profile.api.model

import ru.itis.filmsandroidapp.core.network.model.FilmResponseEntity

data class ShortFilmResponseModel (
    val numberPage: Int,
    val numberFilmsOnPage: Int,
    val total: Int,
    val filmsList: List<ShortFilmInfoModel>,
)

fun FilmResponseEntity.mapShortFilmResponseModel(): ShortFilmResponseModel {
    return ShortFilmResponseModel(
        numberPage = this.numberPage,
        numberFilmsOnPage = this.numberFilmsOnPage,
        total = this.total,
        filmsList = this.filmsList?.map {it.mapShortFilmInfoModel()} ?: emptyList()
    )
}