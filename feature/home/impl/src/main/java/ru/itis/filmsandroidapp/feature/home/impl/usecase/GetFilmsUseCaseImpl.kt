package ru.itis.filmsandroidapp.feature.home.impl.usecase

import ru.itis.filmsandroidapp.feature.home.api.model.ShortFilmResponseModel
import ru.itis.filmsandroidapp.feature.home.api.repository.FilmsRepository
import ru.itis.filmsandroidapp.feature.home.api.usecase.GetFilmsUseCase

internal class GetFilmsUseCaseImpl(
    private val filmsRepository: FilmsRepository,
) : GetFilmsUseCase {

    override suspend fun invoke(
        numberPage: Int,
        numberFilmsOnPage: Int,
        selectFields: List<String>
    ): ShortFilmResponseModel {

        return filmsRepository.getFilms(numberPage, numberFilmsOnPage, selectFields)
    }
}