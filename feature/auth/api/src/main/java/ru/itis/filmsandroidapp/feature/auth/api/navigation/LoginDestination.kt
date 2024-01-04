package ru.itis.filmsandroidapp.feature.auth.api.navigation

import androidx.navigation.NamedNavArgument
import ru.itis.filmsandroidapp.core.navigation.NavigationDestination

object LoginDestination : NavigationDestination {

    override fun route(): String = "login"

    override val arguments: List<NamedNavArgument>
        get() = listOf()

    const val AUTH_ID_PARAM = ""

    private const val LOGIN_ROUTE = "login"
    fun createLoginRoute() = LOGIN_ROUTE
}