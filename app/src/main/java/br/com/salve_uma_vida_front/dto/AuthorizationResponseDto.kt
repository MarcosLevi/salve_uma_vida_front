package br.com.salve_uma_vida_front.dto

import br.com.salve_uma_vida_front.both.models.UserType
import com.google.gson.annotations.SerializedName

data class AuthorizationResponseDto (
    @SerializedName("token")
    var token: String,
    @SerializedName("userType")
    var userType: UserType
){
}