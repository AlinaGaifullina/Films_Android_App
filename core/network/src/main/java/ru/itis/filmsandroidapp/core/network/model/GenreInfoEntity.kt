package ru.itis.filmsandroidapp.core.network.model

data class GenreInfoEntity(
    val genreName: String
)

fun GenreInfo.mapGenreInfoEntity() : GenreInfoEntity {
    return GenreInfoEntity(
        genreName = this.genreName ?: " "
    )
}
