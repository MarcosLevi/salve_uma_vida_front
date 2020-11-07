package br.com.salve_uma_vida_front.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("name")
    var name: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("detail")
    var detail: String,
    @SerializedName("address")
    var address: String,
    @SerializedName("addressLatitude")
    var addressLatitude: Float,
    @SerializedName("addressLongitude")
    var addressLongitude: Float,
    @SerializedName("type")
    var type: String,
    @SerializedName("image")
    var image: String
) {

}