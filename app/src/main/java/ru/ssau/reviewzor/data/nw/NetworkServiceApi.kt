package ru.ssau.reviewzor.data.nw

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import ru.ssau.reviewzor.data.entity.*

interface NetworkServiceApi {

    @POST("api/auth/signin")
    suspend fun signIn(@Body auth: Authentication): AuthenticationResponse

    @POST("api/auth/signup")
    suspend fun signUp(@Body register: Register): RegisterResponse

    @Multipart
    @POST("api/files/upload")
    suspend fun uploadPhoto(@Part file: MultipartBody.Part, @Header("Authorization") token: String): String
}