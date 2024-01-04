package ru.itis.filmsandroidapp.feature.home.impl.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.filmsandroidapp.feature.auth.api.repository.UserRepository
import ru.itis.filmsandroidapp.feature.home.api.repository.FilmsRepository
import ru.itis.filmsandroidapp.feature.home.api.usecase.GetFilmsUseCase
import ru.itis.filmsandroidapp.feature.home.impl.repository.FilmsRepositoryImpl
import ru.itis.filmsandroidapp.feature.home.impl.usecase.GetFilmsUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
class HomeProvidesModule {

    @Provides
    fun getFilmsUseCase(filmsRepository: FilmsRepository): GetFilmsUseCase =
        GetFilmsUseCaseImpl(filmsRepository)
}

@Module
@InstallIn(SingletonComponent::class)

abstract class HomeBindsModule {

    @Binds
    abstract fun bindFilmsRepository(impl: FilmsRepositoryImpl): FilmsRepository

}

