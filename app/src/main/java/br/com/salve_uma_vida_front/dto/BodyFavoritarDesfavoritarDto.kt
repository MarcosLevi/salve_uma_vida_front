package br.com.salve_uma_vida_front.dto

import com.google.gson.annotations.SerializedName

data class BodyFavoritarDesfavoritarDto(
    @SerializedName("ngoId")
    var ngoId: Int
)
