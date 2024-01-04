package ru.itis.filmsandroidapp.core.network.model

data class RatingInfoEntity (
    val ratingKp: String,
    val ratingImdb: String,
    val ratingFilmCritics: String,
    val ratingRussianFilmCritics: String,
    val ratingAwait: String,
)

fun RatingInfo.mapRatingInfoEntity() : RatingInfoEntity {
    return RatingInfoEntity(
        ratingKp = this.ratingKp ?: " ",
        ratingImdb = this.ratingImdb ?: " ",
        ratingFilmCritics = this.ratingFilmCritics ?: " ",
        ratingRussianFilmCritics = this.ratingRussianFilmCritics ?: " ",
        ratingAwait = this.ratingAwait ?: " ",
    )
}