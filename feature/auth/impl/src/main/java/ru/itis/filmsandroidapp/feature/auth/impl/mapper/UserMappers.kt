package ru.itis.filmsandroidapp.feature.auth.impl.mapper

import ru.itis.filmsandroidapp.core.db.model.UserCore
import ru.itis.filmsandroidapp.feature.auth.api.model.User

internal fun User.toUserCore(): UserCore =
    UserCore(firstName, lastName, username, password)

internal fun UserCore.toUser(): User =
    User(firstName, lastName, username, password)
