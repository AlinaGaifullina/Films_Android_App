package ru.itis.filmsandroidapp.feature.auth.impl.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ru.itis.filmsandroidapp.core.widget.textfield.AuthBottomText
import ru.itis.filmsandroidapp.core.widget.textfield.AuthButton
import ru.itis.filmsandroidapp.core.widget.textfield.AuthPasswordField
import ru.itis.filmsandroidapp.core.widget.textfield.AuthTextField
import ru.itis.filmsandroidapp.feature.auth.api.navigation.LoginDestination
import ru.itis.filmsandroidapp.feature.auth.api.navigation.RegisterDestination
import ru.itis.filmsandroidapp.feature.auth.impl.R
import ru.itis.filmsandroidapp.feature.auth.impl.presentation.register.RegisterEvent
import ru.itis.filmsandroidapp.feature.home.api.navigation.HomeDestination

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val eventHandler = viewModel::event
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    LaunchedEffect(action) {
        when (action) {
            LoginSideEffect.NavigateHome -> viewModel.navigate(HomeDestination.createRoute(state.username))
            LoginSideEffect.NavigateRegister -> viewModel.navigate(RegisterDestination.createRegisterRoute())
            else -> Unit
        }
    }

    LoginMainContent(state = state, eventHandler = eventHandler)
}

@Composable
fun LoginMainContent(state: LoginState, eventHandler: (LoginEvent) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                text = stringResource(id = R.string.sign_in),
                modifier = Modifier,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
            if(state.showLoginError){
                Text(
                    text = state.loginError,
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        Column(

        ) {
            AuthTextField(stringResource(id = R.string.username), state.username) {
                eventHandler.invoke(LoginEvent.OnUsernameChange(it))
            }

            Spacer(modifier = Modifier.height(20.dp))
            AuthPasswordField(
                stringResource(id = R.string.password), state.password,
                onChange = { eventHandler.invoke(LoginEvent.OnPasswordChange(it)) },
                state.passwordVisible,
                onVisibleChange = { eventHandler.invoke(LoginEvent.OnPasswordVisibilityChange) }
            )

        }

        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthButton(
                onClick = { eventHandler.invoke(LoginEvent.OnLoginButtonClick) },
                text = stringResource(id = R.string.sign_in_btn),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally)
            )
            AuthBottomText(
                stringResource(id = R.string.no_acc),
                stringResource(id = R.string.sign_up_btn)
            ) { eventHandler.invoke(LoginEvent.OnRegisterButtonCLick) }
        }
    }
}

