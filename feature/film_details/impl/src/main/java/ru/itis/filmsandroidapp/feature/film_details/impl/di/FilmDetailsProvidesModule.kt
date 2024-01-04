package ru.itis.filmsandroidapp.feature.film_details.impl.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.filmsandroidapp.feature.film_details.api.repository.FilmDetailsRepository
import ru.itis.filmsandroidapp.feature.film_details.api.usecase.AddFilmToFavouritesUseCase
import ru.itis.filmsandroidapp.feature.film_details.api.usecase.DeleteFilmFromFavouritesUseCase
import ru.itis.filmsandroidapp.feature.film_details.api.usecase.GetFavouritesByUsernameUseCase
import ru.itis.filmsandroidapp.feature.film_details.api.usecase.GetFilmByIdUseCase
import ru.itis.filmsandroidapp.feature.film_details.impl.repository.FilmDetailsRepositoryImpl
import ru.itis.filmsandroidapp.feature.film_details.impl.usecase.AddFilmToFavouritesUseCaseImpl
import ru.itis.filmsandroidapp.feature.film_details.impl.usecase.DeleteFilmFromFavouritesUseCaseImpl
import ru.itis.filmsandroidapp.feature.film_details.impl.usecase.GetFavouritesByUsernameUseCaseImpl
import ru.itis.filmsandroidapp.feature.film_details.impl.usecase.GetFilmByIdUseCaseImpl


@Module
@InstallIn(SingletonComponent::class)
class FilmDetailsProvidesModule {

    @Provides
    fun getFilmByIdUseCase(filmDetailsRepository: FilmDetailsRepository): GetFilmByIdUseCase =
        GetFilmByIdUseCaseImpl(filmDetailsRepository)

    @Provides
    fun addFilmToFavouritesUseCase(filmDetailsRepository: FilmDetailsRepository): AddFilmToFavouritesUseCase =
        AddFilmToFavouritesUseCaseImpl(filmDetailsRepository)

    @Provides
    fun deleteFilmFromFavouritesUseCase(filmDetailsRepository: FilmDetailsRepository): DeleteFilmFromFavouritesUseCase =
        DeleteFilmFromFavouritesUseCaseImpl(filmDetailsRepository)

    @Provides
    fun getFavouritesByUsernameUseCase(filmDetailsRepository: FilmDetailsRepository): GetFavouritesByUsernameUseCase =
        GetFavouritesByUsernameUseCaseImpl(filmDetailsRepository)
}



@Module
@InstallIn(SingletonComponent::class)

abstract class FilmDetailsBindsModule {

    @Binds
    abstract fun bindFilmDetailsRepository(impl: FilmDetailsRepositoryImpl): FilmDetailsRepository

}