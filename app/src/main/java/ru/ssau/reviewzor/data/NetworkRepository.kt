package ru.ssau.reviewzor.data

import ru.ssau.reviewzor.data.entity.Authentication
import ru.ssau.reviewzor.data.entity.Register
import ru.ssau.reviewzor.data.nw.NetworkServiceApi

class NetworkRepository(private val api: NetworkServiceApi) {

    suspend fun registerUser(username: String, password: String) = api.signUp(Register(username, password))

    suspend fun signIn(username: String, password: String) = api.signIn(Authentication(username, password))

}