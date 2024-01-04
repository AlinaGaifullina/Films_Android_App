package ru.itis.filmsandroidapp.feature.auth.impl.presentation.register

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
import ru.itis.filmsandroidapp.feature.auth.api.actions_results.RegisterResult
import ru.itis.filmsandroidapp.feature.auth.api.usecase.CheckUsernameUseCase
import ru.itis.filmsandroidapp.feature.auth.api.usecase.RegisterUserUseCase
import javax.inject.Inject


@Immutable
data class RegisterState(
    val username: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val passwordVisible: Boolean = false,
    val showLoadingProgressBar: Boolean = false,
    val showErrors: Boolean = false,
    val loginError: String = "",
    val errors: List<String> = listOf()
)

sealed interface RegisterSideEffect {
    object NavigateHome : RegisterSideEffect
    object NavigateLogin : RegisterSideEffect
}

sealed interface RegisterEvent {
    object OnRegisterButtonClick : RegisterEvent
    object OnLoginButtonCLick : RegisterEvent
    object OnPasswordVisibilityChange : RegisterEvent
    object OnDismissRegisterRequest : RegisterEvent
    object OnDismissErrorDialog : RegisterEvent
    data class OnUsernameChange(val value: String) : RegisterEvent
    data class OnFirstNameChange(val value: String) : RegisterEvent
    data class OnLastNameChange(val value: String) : RegisterEvent
    data class OnPasswordChange(val value: String) : RegisterEvent
    data class OnConfirmPasswordChange(val value: String) : RegisterEvent
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    //private val setCurrentUserUseCase: SetCurrentUserUseCase,
    private val isUsernameExist: CheckUsernameUseCase,
    private val register: RegisterUserUseCase,
    private val navigator: Navigator
) : ViewModel(), Navigator by navigator{
    private val _state: MutableStateFlow<RegisterState> = MutableStateFlow(
        RegisterState()
    )
    val state: StateFlow<RegisterState> = _state

    private val _action = MutableSharedFlow<RegisterSideEffect?>()
    val action: SharedFlow<RegisterSideEffect?>
        get() = _action.asSharedFlow()

//    private var currentJob: Job? = null
//
//    override fun onCleared() {
//        super.onCleared()
//        currentJob?.cancel()
//        currentJob = null
//    }

    fun event(event: RegisterEvent) {
        when (event) {
            RegisterEvent.OnRegisterButtonClick -> onRegisterButtonClick()
            RegisterEvent.OnLoginButtonCLick -> onLoginButtonClick()
            RegisterEvent.OnPasswordVisibilityChange -> onPasswordVisibilityChange()
            RegisterEvent.OnDismissErrorDialog -> onDismissErrorDialog()
            RegisterEvent.OnDismissRegisterRequest -> onDismissRegisterRequest()
            is RegisterEvent.OnUsernameChange -> onUsernameChange(event.value)
            is RegisterEvent.OnFirstNameChange -> onFirstNameChange(event.value)
            is RegisterEvent.OnLastNameChange -> onLastNameChange(event.value)
            is RegisterEvent.OnPasswordChange -> onPasswordChange(event.value)
            is RegisterEvent.OnConfirmPasswordChange -> onConfirmPasswordChange(event.value)
        }
    }

    private fun onUsernameChange(username: String) {
        _state.tryEmit(_state.value.copy(username = username))
    }

    private fun onFirstNameChange(firstName: String) {
        _state.tryEmit(_state.value.copy(firstName = firstName))
    }

    private fun onLastNameChange(lastName: String) {
        _state.tryEmit(_state.value.copy(lastName = lastName))
    }

    private fun onPasswordChange(password: String) {
        _state.tryEmit(_state.value.copy(password = password))
    }

    private fun onConfirmPasswordChange(password: String) {
        _state.tryEmit(_state.value.copy(confirmPassword = password))
    }

    private fun onPasswordVisibilityChange() {
        _state.tryEmit(_state.value.copy(passwordVisible = !_state.value.passwordVisible))
    }

    private fun onDismissErrorDialog() {
        //_state.tryEmit(_state.value.copy(showErrorDialog = false))
    }

    private fun onDismissRegisterRequest() {
        //currentJob?.cancel()
        _state.tryEmit(_state.value.copy(showLoadingProgressBar = false))
    }

    private fun onLoginButtonClick() {
        //currentJob?.cancel()
        viewModelScope.launch {
            _action.emit(RegisterSideEffect.NavigateLogin)
        }
    }

    private fun onRegisterButtonClick() {
        var loginError = ""
       // currentJob?.cancel()
        //currentJob =
        viewModelScope.launch {
            val errors = mutableListOf<String>()
            _state.emit(_state.value.copy(showLoadingProgressBar = true))
            val result =
                if (validateFields(errors)) with(state.value) {
                    register(firstName, lastName, username, password)
                }
                else RegisterResult.FailRegister()
            _state.emit(_state.value.copy(showLoadingProgressBar = false))

            when (result) {
                is RegisterResult.SuccessRegister -> {
                    //setCurrentUserUseCase(result.id)
                    _action.emit(RegisterSideEffect.NavigateHome)
                }

                is RegisterResult.FailRegister -> {
                    result.errorMessage?.let { errors.add(it) }
                    _state.emit(_state.value.copy(showErrors = true, errors = errors))
                }
            }
        }
    }


    private fun validateFields(errors: MutableList<String>): Boolean {
        if (state.value.username.length < 3) {
            errors.add("Имя пользователя должно состоять не менее чем из 3 символов.")
            return false
        }

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

        if (password != state.value.confirmPassword) {
            passValidate = false
            errors.add("Пароли не совпадают.")
        }

        return passValidate
    }
}