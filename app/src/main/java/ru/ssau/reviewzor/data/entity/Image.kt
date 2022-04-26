package ru.ssau.reviewzor.data.entity

import okhttp3.MultipartBody

data class Image (
    val image: MultipartBody.Part,
    val token: String
)