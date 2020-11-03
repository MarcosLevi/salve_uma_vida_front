package br.com.salve_uma_vida_front.dto

import com.google.gson.annotations.SerializedName

data class UserResponseDto(
    @SerializedName("username")
    var name: String,
    @SerializedName("type")
    var type: String
) {

}