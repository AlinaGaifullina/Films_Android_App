//package ru.itis.filmsandroidapp.feature.auth.api.service
//
//import ru.itis.filmsandroidapp.feature.auth.api.actions_results.LoginResult
//import ru.itis.filmsandroidapp.feature.auth.api.actions_results.RegisterResult
//
//interface UserService {
//
//    suspend fun register(
//        firstName: String,
//        lastName: String,
//        username: String,
//        password: String
//    ): RegisterResult
//
//    suspend fun login(username: String, password: String): LoginResult
//
//    suspend fun updatePassword(newPassword: String): Boolean
//
//    fun signOut()
//}