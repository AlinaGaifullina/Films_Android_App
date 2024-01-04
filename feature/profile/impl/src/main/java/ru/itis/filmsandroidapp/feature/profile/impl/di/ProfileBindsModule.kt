package ru.itis.filmsandroidapp.feature.profile.impl.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.filmsandroidapp.feature.profile.api.repository.UserProfileRepository
import ru.itis.filmsandroidapp.feature.profile.api.usecase.GetFavouritesByUsernameUseCase
import ru.itis.filmsandroidapp.feature.profile.api.usecase.GetFilmByFilmIdUseCase
import ru.itis.filmsandroidapp.feature.profile.api.usecase.GetUserByUsernameUseCase
import ru.itis.filmsandroidapp.feature.profile.impl.repository.UserProfileRepositoryImpl
import ru.itis.filmsandroidapp.feature.profile.impl.usecase.GetFavouritesByUsernameUseCaseImpl
import ru.itis.filmsandroidapp.feature.profile.impl.usecase.GetFilmByFilmIdUseCaseImpl
import ru.itis.filmsandroidapp.feature.profile.impl.usecase.GetUserByUsernameUseCaseImpl


@Module
@InstallIn(SingletonComponent::class)
internal abstract class ProfileBindsModule {

    @Binds
    abstract fun bindUserProfileRepository(impl: UserProfileRepositoryImpl): UserProfileRepository


}

@Module
@InstallIn(SingletonComponent::class)
internal class ProfileProvidesModule {

    @Provides
    fun getFilmByFilmIdUseCase(userProfileRepository: UserProfileRepository): GetFilmByFilmIdUseCase =
        GetFilmByFilmIdUseCaseImpl(userProfileRepository)


    @Provides
    fun getFavouritesByUsernameUseCase(userProfileRepository: UserProfileRepository): GetFavouritesByUsernameUseCase =
        GetFavouritesByUsernameUseCaseImpl(userProfileRepository)

    @Provides
    fun getUserByUsernameUseCase(userProfileRepository: UserProfileRepository): GetUserByUsernameUseCase =
        GetUserByUsernameUseCaseImpl(userProfileRepository)

}