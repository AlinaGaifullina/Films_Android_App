package ru.itis.filmsandroidapp.feature.home.api.usecase

import ru.itis.filmsandroidapp.feature.home.api.model.ShortFilmResponseModel

interface GetFilmsUseCase {

    suspend operator fun invoke(
        numberPage: Int,
        numberFilmsOnPage: Int,
        selectFields: List<String>
    ): ShortFilmResponseModel
}