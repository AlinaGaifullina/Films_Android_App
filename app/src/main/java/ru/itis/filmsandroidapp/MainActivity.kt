package ru.itis.filmsandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.itis.filmsandroidapp.core.designsystem.FilmsAndroidAppTheme
import ru.itis.filmsandroidapp.core.navigation.NavigationDestination
import ru.itis.filmsandroidapp.core.navigation.Navigator
import ru.itis.filmsandroidapp.core.navigation.NavigatorEvent
import ru.itis.filmsandroidapp.feature.auth.api.navigation.LoginDestination
import ru.itis.filmsandroidapp.feature.auth.api.navigation.RegisterDestination
import ru.itis.filmsandroidapp.feature.auth.impl.presentation.login.LoginScreen
import javax.inject.Inject
import ru.itis.filmsandroidapp.feature.auth.impl.presentation.register.RegisterScreen
import ru.itis.filmsandroidapp.feature.film_details.api.navigation.FilmDetailsDestination
import ru.itis.filmsandroidapp.feature.film_details.impl.presentation.FilmDetailsScreen
import ru.itis.filmsandroidapp.feature.home.api.navigation.HomeDestination
import ru.itis.filmsandroidapp.feature.home.impl.presentation.HomeScreen
import ru.itis.filmsandroidapp.feature.profile.api.navigation.ProfileDestination
import ru.itis.filmsandroidapp.feature.profile.impl.presentation.profile.ProfileScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FilmsAndroidAppTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    LaunchedEffect(navController) {
                        navigator.destinations.collect {
                            when (val event = it) {
                                is NavigatorEvent.NavigateUp -> {
                                    navController.navigateUp()
                                }

                                is NavigatorEvent.Directions -> navController.navigate(
                                    event.destination,
                                    event.builder
                                )

                                NavigatorEvent.PopBackStack -> {
                                    navController.popBackStack()
                                }
                            }
                        }
                    }
                    val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> =
                        mapOf(
                            RegisterDestination to { RegisterScreen() },
                            LoginDestination to { LoginScreen() },
                            HomeDestination to { HomeScreen() },
                            ProfileDestination to { ProfileScreen() },
                            FilmDetailsDestination to { FilmDetailsScreen() }
                        )

                    fun NavGraphBuilder.addComposableDestinations(navController: NavHostController) {
                        composableDestinations.forEach { entry ->
                            val destination = entry.key
                            composable(destination.route(), destination.arguments) {
                                entry.value()
                            }
                        }
                    }

                    Box(Modifier.fillMaxSize()) {

                        NavHost(
                            modifier = Modifier,
                            navController = navController,
                            startDestination = RegisterDestination.route(),
                            enterTransition = { fadeIn(animationSpec = tween(0)) },
                            exitTransition = { fadeOut(animationSpec = tween(0)) },
                        ) {
                            addComposableDestinations(navController)
                        }
                    }
                }
            }
        }
    }
}