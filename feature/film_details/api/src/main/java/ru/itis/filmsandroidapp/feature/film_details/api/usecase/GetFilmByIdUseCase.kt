package ru.itis.filmsandroidapp.feature.film_details.api.usecase

import ru.itis.filmsandroidapp.core.network.model.FilmInfoEntity

interface GetFilmByIdUseCase {

    suspend operator fun invoke(filmId: Int): FilmInfoEntity
}