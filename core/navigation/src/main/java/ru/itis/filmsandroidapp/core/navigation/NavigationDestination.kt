package ru.itis.filmsandroidapp.core.navigation

import androidx.navigation.NamedNavArgument

fun interface NavigationDestination {

    fun route(): String
    val arguments: List<NamedNavArgument>
        get() = emptyList()
}