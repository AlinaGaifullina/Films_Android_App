package ru.itis.filmsandroidapp.core.network.model

data class FilmResponseEntity(
    val numberPage: Int,
    val numberFilmsOnPage: Int,
    val total: Int,
    val filmsList: List<FilmInfoEntity>,
)

fun FilmResponse.mapFilmResponseEntity(): FilmResponseEntity {
    return FilmResponseEntity(
        numberPage = this.numberPage,
        numberFilmsOnPage = this.numberFilmsOnPage,
        total = this.total,
        filmsList = this.filmsList?.map { it.mapFilmInfoEntity() } ?: emptyList()
    )
}
