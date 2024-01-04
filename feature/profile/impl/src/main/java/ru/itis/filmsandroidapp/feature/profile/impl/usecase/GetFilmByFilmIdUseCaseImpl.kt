package ru.itis.filmsandroidapp.feature.profile.impl.usecase

import ru.itis.filmsandroidapp.feature.profile.api.model.ShortFilmInfoModel
import ru.itis.filmsandroidapp.feature.profile.api.repository.UserProfileRepository
import ru.itis.filmsandroidapp.feature.profile.api.usecase.GetFilmByFilmIdUseCase

class GetFilmByFilmIdUseCaseImpl (
    private val userProfileRepository: UserProfileRepository,
) : GetFilmByFilmIdUseCase {

    override suspend fun invoke(filmId: Int): ShortFilmInfoModel {
        return userProfileRepository.getFilmById(filmId)
    }

}