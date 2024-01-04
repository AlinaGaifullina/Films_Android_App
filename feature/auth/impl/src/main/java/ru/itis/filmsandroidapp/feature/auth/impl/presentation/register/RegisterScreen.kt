package ru.itis.filmsandroidapp.feature.auth.impl.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.itis.filmsandroidapp.core.widget.textfield.AuthBottomText
import ru.itis.filmsandroidapp.core.widget.textfield.AuthButton
import ru.itis.filmsandroidapp.core.widget.textfield.AuthPasswordField
import ru.itis.filmsandroidapp.core.widget.textfield.AuthTextField
import ru.itis.filmsandroidapp.feature.auth.api.navigation.LoginDestination
import ru.itis.filmsandroidapp.feature.auth.api.navigation.RegisterDestination
import ru.itis.filmsandroidapp.feature.auth.impl.R
import ru.itis.filmsandroidapp.feature.home.api.navigation.HomeDestination

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val eventHandler = viewModel::event
    val action by viewModel.action.collectAsStateWithLifecycle(null)


//    LaunchedEffect(Unit) {
//        //settingsEventBus.updateDarkMode(false)
//    }
//
    LaunchedEffect(action) {
        when (action) {
            RegisterSideEffect.NavigateHome -> viewModel.navigate(HomeDestination.createRoute(state.username))
            RegisterSideEffect.NavigateLogin -> viewModel.navigate(LoginDestination.createLoginRoute())
            else -> Unit
        }
    }

    RegisterScreenContent(state = state, eventHandler = eventHandler)
    //Dialogs(state = state, eventHandler = eventHandler)

    /*DisposableEffect(Unit) {
        onDispose {
            settingsEventBus.updateDarkMode(originDarkMode)
        }
    }*/
}

@Composable
private fun RegisterScreenContent(state: RegisterState, eventHandler: (RegisterEvent) -> Unit) {


    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 40.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                text = stringResource(id = R.string.sign_up),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
        }

        // fields
        Column {
            AuthTextField(
                stringResource(id = R.string.username),
                state.username
            ) { eventHandler.invoke(RegisterEvent.OnUsernameChange(it)) }

            Spacer(modifier = Modifier.height(20.dp))
            AuthTextField(
                stringResource(id = R.string.first_name),
                state.firstName
            ) { eventHandler.invoke(RegisterEvent.OnFirstNameChange(it)) }

            Spacer(modifier = Modifier.height(20.dp))
            AuthTextField(
                stringResource(id = R.string.last_name),
                state.lastName
            ) { eventHandler.invoke(RegisterEvent.OnLastNameChange(it)) }

            Spacer(modifier = Modifier.height(20.dp))
            AuthPasswordField(
                stringResource(id = R.string.password),
                state.password,
                { eventHandler.invoke(RegisterEvent.OnPasswordChange(it)) },
                state.passwordVisible,
                { eventHandler.invoke(RegisterEvent.OnPasswordVisibilityChange) })

            Spacer(modifier = Modifier.height(20.dp))
            AuthPasswordField(
                stringResource(id = R.string.repeat_pass),
                state.confirmPassword,
                { eventHandler.invoke(RegisterEvent.OnConfirmPasswordChange(it)) },
                state.passwordVisible,
                { eventHandler.invoke(RegisterEvent.OnPasswordVisibilityChange) })
        }

        // Buttons
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthButton(
                onClick = { eventHandler.invoke(RegisterEvent.OnRegisterButtonClick) },
                text = stringResource(id = R.string.sign_up_btn),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(60.dp)

            )

            AuthBottomText(
                stringResource(id = R.string.have_acc),
                stringResource(id = R.string.sign_in)
            ) { eventHandler.invoke(RegisterEvent.OnLoginButtonCLick) }
        }
    }
}

/*
@Composable
private fun Dialogs(state: RegisterState, eventHandler: (RegisterEvent) -> Unit) {
    if (state.showLoadingProgressBar)
        LoadingDialog(
            stringResource(id = R.string.loading_reg),
            onDismiss = { eventHandler.invoke(RegisterEvent.OnDismissRegisterRequest) }
        )

    if (state.showErrorDialog)
        ErrorDialog(
            title = stringResource(id = R.string.reg_fail),
            errors = state.errors,
            onDismiss = { eventHandler.invoke(RegisterEvent.OnDismissErrorDialog) }
        )
}*/
