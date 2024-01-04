package ru.itis.filmsandroidapp.feature.auth.impl.presentation.login

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.itis.filmsandroidapp.core.navigation.Navigator
import ru.itis.filmsandroidapp.feature.auth.api.actions_results.LoginResult
import ru.itis.filmsandroidapp.feature.auth.api.usecase.LoginUserUseCase
import javax.inject.Inject

@Immutable
data class LoginState(
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val showLoadingProgressBar: Boolean = false,
    val showErrorDialog: Boolean = false,
    val showLoginError: Boolean = false,
    val loginError: String = "",
    val errors: List<String> = listOf()
)

sealed interface LoginSideEffect {
    object NavigateHome : LoginSideEffect
    object NavigateRegister : LoginSideEffect
}

sealed interface LoginEvent {
    object OnLoginButtonClick : LoginEvent
    object OnRegisterButtonCLick : LoginEvent
    object OnPasswordVisibilityChange : LoginEvent
    data class OnUsernameChange(val value: String) : LoginEvent
    data class OnPasswordChange(val value: String) : LoginEvent
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navigator: Navigator,
    private val login: LoginUserUseCase,
) : ViewModel(), Navigator by navigator {
    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _action = MutableSharedFlow<LoginSideEffect?>()
    val action: SharedFlow<LoginSideEffect?>
        get() = _action.asSharedFlow()

    fun event(event: LoginEvent) {
        when (event) {
            LoginEvent.OnPasswordVisibilityChange -> onPasswordVisibilityChange()
            is LoginEvent.OnUsernameChange -> onUsernameChange(event.value)
            is LoginEvent.OnPasswordChange -> onPasswordChange(event.value)
            LoginEvent.OnLoginButtonClick -> onLoginButtonClick()
            LoginEvent.OnRegisterButtonCLick -> onRegisterButtonCLick()
        }
    }

    private fun onUsernameChange(username: String) {
        _state.tryEmit(_state.value.copy(username = username, showLoginError = false))
    }

    private fun onPasswordChange(password: String) {
        _state.tryEmit(_state.value.copy(password = password, showLoginError = false))
    }

    private fun onPasswordVisibilityChange() {
        _state.tryEmit(_state.value.copy(passwordVisible = !_state.value.passwordVisible))
    }

    private fun onRegisterButtonCLick() {
        viewModelScope.launch { _action.emit(LoginSideEffect.NavigateRegister) }
    }

    private fun onLoginButtonClick() {
        var loginError = ""
        viewModelScope.launch {
            val errors = mutableListOf<String>()
            _state.emit(_state.value.copy(showLoadingProgressBar = true))
            val result = if (validateFields(errors)) with(state.value) {
                login(username, password)
            }
            else LoginResult.FailLogin()
            _state.emit(_state.value.copy(showLoadingProgressBar = false))

            when (result) {
                is LoginResult.SuccessLogin -> {
                    _action.emit(LoginSideEffect.NavigateHome)
                }

                is LoginResult.FailLogin -> {
                    result.errorMessage?.let { loginError = it }
                    _state.emit(_state.value.copy(showLoginError = true, loginError = loginError))
                }
            }
        }
    }

    private fun validateFields(errors: MutableList<String>): Boolean {

        var passValidate = true
        val password = state.value.password
        if (!password.matches(Regex(".*[a-z].*"))) {
            errors.add("Пароль должен содержать хотя бы одну маленькую букву.")
            passValidate = false
        }

        if (!password.matches(Regex(".*[A-Z].*"))) {
            errors.add("Пароль должен содержать хотя бы одну большую букву.")
            passValidate = false
        }

        if (!password.matches(Regex(".*\\d.*"))) {
            passValidate = false
            errors.add("Пароль должен содержать хотя бы одну цифру.")
        }

        if (password.length < 8) {
            passValidate = false
            errors.add("Пароль должен содержать минимум 8 символов.")
        }

        return passValidate
    }
}