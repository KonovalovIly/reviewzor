package ru.ssau.reviewzor.data.nw

import retrofit2.http.Body
import retrofit2.http.POST
import ru.ssau.reviewzor.data.entity.Authentication
import ru.ssau.reviewzor.data.entity.AuthenticationResponse
import ru.ssau.reviewzor.data.entity.Register
import ru.ssau.reviewzor.data.entity.RegisterResponse

interface NetworkServiceApi {

    @POST("api/auth/signin")
    suspend fun signIn(@Body auth: Authentication): AuthenticationResponse

    @POST("api/auth/signup")
    suspend fun signUp(@Body register: Register): RegisterResponse
}