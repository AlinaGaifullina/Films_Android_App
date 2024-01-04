package ru.itis.filmsandroidapp.feature.auth.impl.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.filmsandroidapp.feature.auth.api.repository.UserRepository
import ru.itis.filmsandroidapp.feature.auth.api.usecase.CheckUsernameUseCase
import ru.itis.filmsandroidapp.feature.auth.api.usecase.LoginUserUseCase
import ru.itis.filmsandroidapp.feature.auth.api.usecase.RegisterUserUseCase
import ru.itis.filmsandroidapp.feature.auth.impl.repository.UserRepositoryImpl
import ru.itis.filmsandroidapp.feature.auth.impl.usecase.CheckUsernameUseCaseImpl
import ru.itis.filmsandroidapp.feature.auth.impl.usecase.LoginUserUseCaseImpl
import ru.itis.filmsandroidapp.feature.auth.impl.usecase.RegisterUserUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthBindsModule {

    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

}


@Module
@InstallIn(SingletonComponent::class)
class AuthProvidesModule {
    @Provides
    fun registerUserUseCase(userRepository: UserRepository): RegisterUserUseCase =
        RegisterUserUseCaseImpl(userRepository)

    @Provides
    fun loginUserUseCase(userRepository: UserRepository): LoginUserUseCase =
        LoginUserUseCaseImpl(userRepository)

    @Provides
    fun checkUsernameUseCase(userRepository: UserRepository): CheckUsernameUseCase =
        CheckUsernameUseCaseImpl(userRepository)
}