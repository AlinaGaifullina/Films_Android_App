package ru.itis.filmsandroidapp.feature.profile.impl.usecase

import ru.itis.filmsandroidapp.feature.profile.api.model.UserProfile
import ru.itis.filmsandroidapp.feature.profile.api.repository.UserProfileRepository
import ru.itis.filmsandroidapp.feature.profile.api.usecase.GetUserByUsernameUseCase

internal class GetUserByUsernameUseCaseImpl(
    private val userProfileRepository: UserProfileRepository,
) : GetUserByUsernameUseCase {

    override suspend fun invoke(username: String): UserProfile? {
        return try {
            userProfileRepository.getUserByUsername(username)
        } catch (e: Exception) {
            UserProfile(
                "",
                "",
                "",
                ""
            )
        }
    }
}
