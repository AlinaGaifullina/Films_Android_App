package ru.itis.filmsandroidapp.feature.film_details.api.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import ru.itis.filmsandroidapp.core.navigation.NavigationDestination

object FilmDetailsDestination : NavigationDestination {

    override fun route(): String = "film_details/{$FILM_ID_PARAM}/{$USERNAME_PARAM}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(
            navArgument(FILM_ID_PARAM) { type = NavType.IntType },
            navArgument(USERNAME_PARAM) { type = NavType.StringType },
        )

    const val FILM_ID_PARAM = "0"
    const val USERNAME_PARAM = "Гость"

    private const val FILM_DETAILS_ROUTE = "film_details"
    fun createRoute(filmId: String, username: String) = "$FILM_DETAILS_ROUTE/${filmId}/${username}"

}