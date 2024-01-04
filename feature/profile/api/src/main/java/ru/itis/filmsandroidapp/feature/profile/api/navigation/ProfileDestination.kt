package ru.itis.filmsandroidapp.feature.profile.api.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import ru.itis.filmsandroidapp.core.navigation.NavigationDestination

object ProfileDestination : NavigationDestination {

    override fun route(): String = "profile/{$USERNAME_PARAM}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(USERNAME_PARAM) { type = NavType.StringType },
        )

    const val USERNAME_PARAM = "Гость"

    private const val PROFILE_ROUTE = "profile"
    fun createRoute(username: String) = "$PROFILE_ROUTE/${username}"

}