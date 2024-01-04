package ru.itis.filmsandroidapp.feature.auth.api.actions_results

sealed interface RegisterResult {
    data class SuccessRegister(val username: String) : RegisterResult
    data class FailRegister(val errorMessage: String? = null) : RegisterResult
}