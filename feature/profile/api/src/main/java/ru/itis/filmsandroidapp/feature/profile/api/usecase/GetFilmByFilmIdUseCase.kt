package ru.itis.filmsandroidapp.feature.profile.api.usecase

import ru.itis.filmsandroidapp.feature.profile.api.model.ShortFilmInfoModel

interface GetFilmByFilmIdUseCase {

    suspend operator fun invoke(filmId: Int): ShortFilmInfoModel
}