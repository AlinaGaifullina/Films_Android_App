package ru.itis.filmsandroidapp.feature.home.api.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import ru.itis.filmsandroidapp.core.navigation.NavigationDestination

object HomeDestination : NavigationDestination {

    override fun route(): String = "home/{$AUTH_USERNAME_PARAM}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(AUTH_USERNAME_PARAM) { type = NavType.StringType },
        )

    const val AUTH_USERNAME_PARAM = "Гость"

    private const val HOME_ROUTE = "home"
    fun createRoute(username: String) = "$HOME_ROUTE/${username}"

}