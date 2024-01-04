package ru.itis.filmsandroidapp.core.navigation

import androidx.navigation.NavOptionsBuilder

sealed class NavigatorEvent {
    object NavigateUp : NavigatorEvent()
    class Directions(
        val destination: String,
        val builder: NavOptionsBuilder.() -> Unit
    ) : NavigatorEvent()

    object PopBackStack : NavigatorEvent()
}