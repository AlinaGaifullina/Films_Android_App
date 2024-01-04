package ru.itis.filmsandroidapp.feature.profile.api.mappers

import ru.itis.filmsandroidapp.core.db.model.UserCore
import ru.itis.filmsandroidapp.feature.profile.api.model.UserProfile

fun UserProfile.toUserCore(): UserCore =
    UserCore(firstName, lastName, username, "password")

fun UserCore.toUserProfile(): UserProfile =
    UserProfile(firstName, lastName, username, password)