package ru.ssau.reviewzor.data.entity

data class AuthenticationResponse(
    val token: String,
    val type: String,
    val userId: Int,
    val email: String,
    val roles: List<String>
)