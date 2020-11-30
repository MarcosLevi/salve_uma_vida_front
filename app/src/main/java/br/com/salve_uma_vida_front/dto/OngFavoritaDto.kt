package br.com.salve_uma_vida_front.dto

import com.google.gson.annotations.SerializedName

data class OngFavoritaDto(
    @SerializedName("name")
    var name: String,
    @SerializedName("image")
    var image: String
) {

}