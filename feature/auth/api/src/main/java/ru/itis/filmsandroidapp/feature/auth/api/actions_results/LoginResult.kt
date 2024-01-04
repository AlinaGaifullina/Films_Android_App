package ru.itis.filmsandroidapp.feature.auth.api.actions_results

sealed interface LoginResult {
    data class SuccessLogin(val username: String) : LoginResult
    data class FailLogin(val errorMessage: String? = null) : LoginResult
}