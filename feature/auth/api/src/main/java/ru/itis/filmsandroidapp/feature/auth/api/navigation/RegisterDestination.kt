package ru.itis.filmsandroidapp.feature.auth.api.navigation

import androidx.navigation.NamedNavArgument
import ru.itis.filmsandroidapp.core.navigation.NavigationDestination

object RegisterDestination : NavigationDestination {

    override fun route(): String = "register"

    override val arguments: List<NamedNavArgument>
        get() = listOf()

    const val AUTH_ID_PARAM = ""

    private const val REGISTER_ROUTE = "register"
    fun createRegisterRoute() = REGISTER_ROUTE

}