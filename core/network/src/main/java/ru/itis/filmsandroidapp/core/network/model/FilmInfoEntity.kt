package ru.itis.filmsandroidapp.core.network.model

data class FilmInfoEntity(
    val filmId: Int,
    val filmName: String?,
    val filmYear: String?,
    val posterInfo: PosterInfoEntity,
    val shortDescription: String?,
    val description: String?,
    val rating: RatingInfoEntity,
    val genres: List<GenreInfoEntity>
)

fun FilmInfo.mapFilmInfoEntity() : FilmInfoEntity {
    return FilmInfoEntity(
        filmId = (this.filmId),
        filmName = this.filmName ?: " ",
        filmYear = this.filmYear ?: " ",
        posterInfo = this.posterInfo.mapPosterInfoEntity(),
        shortDescription = this.shortDescription ?: " ",
        description = this.description ?: " ",
        rating = this.rating.mapRatingInfoEntity(),
        genres = this.genres?.map { it.mapGenreInfoEntity() } ?: emptyList()
    )
}
