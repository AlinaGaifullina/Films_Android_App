package ru.itis.filmsandroidapp.feature.profile.api.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import ru.itis.filmsandroidapp.core.navigation.NavigationDestination

object EditProfileDestination : NavigationDestination {

    override fun route(): String = "edit_profile/{$AUTH_USERNAME_PARAM}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(AUTH_USERNAME_PARAM) { type = NavType.StringType },
        )

    const val AUTH_USERNAME_PARAM = "Гость"

    private const val PROFILE_ROUTE = "edit_profile"
    fun createRoute(username: String) = "$PROFILE_ROUTE/${username}"

}