package br.com.salve_uma_vida_front.dto

import com.google.gson.annotations.SerializedName

data class AuthorizationRequestDto(
    @SerializedName("username")
    var username: String,
    @SerializedName("password")
    var password: String
) {
}