package ru.ssau.reviewzor.data

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.ssau.reviewzor.data.entity.Authentication
import ru.ssau.reviewzor.data.entity.ImageResponse
import ru.ssau.reviewzor.data.entity.Register
import ru.ssau.reviewzor.data.nw.NetworkServiceApi
import java.io.File

class NetworkRepository(private val api: NetworkServiceApi) {

    suspend fun registerUser(username: String, password: String) =
        api.signUp(Register(username, password))

    suspend fun signIn(username: String, password: String) =
        api.signIn(Authentication(username, password))

    suspend fun uploadPhoto(file: File, token: String?): String {
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("*/*"), file)
        val fileToUpload: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", file.getName(), requestBody)
        val request = "Bearer $token"
        return api.uploadPhoto(fileToUpload, request)
    }
}