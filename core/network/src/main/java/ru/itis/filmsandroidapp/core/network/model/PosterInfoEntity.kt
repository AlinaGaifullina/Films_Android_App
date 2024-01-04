package ru.itis.filmsandroidapp.core.network.model

data class PosterInfoEntity (
    val posterUrl: String,
    val posterPreviewUrl: String,
)

fun PosterInfo.mapPosterInfoEntity() : PosterInfoEntity {
    return PosterInfoEntity(
        posterUrl = this.posterUrl ?: " ",
        posterPreviewUrl = this.posterPreviewUrl ?: " "
    )
}