package ru.itis.filmsandroidapp.feature.film_details.impl.usecase

import ru.itis.filmsandroidapp.core.network.model.FilmInfoEntity
import ru.itis.filmsandroidapp.feature.film_details.api.repository.FilmDetailsRepository
import ru.itis.filmsandroidapp.feature.film_details.api.usecase.GetFilmByIdUseCase

internal class GetFilmByIdUseCaseImpl (
    private val filmsDetailsRepository: FilmDetailsRepository,
) : GetFilmByIdUseCase {

    override suspend fun invoke(filmId: Int): FilmInfoEntity {
        return filmsDetailsRepository.getFilmById(filmId)
    }

}