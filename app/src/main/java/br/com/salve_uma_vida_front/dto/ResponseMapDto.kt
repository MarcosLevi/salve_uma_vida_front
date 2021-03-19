package br.com.salve_uma_vida_front.dto

import br.com.salve_uma_vida_front.models.MapPinType
import com.google.gson.annotations.SerializedName

data class ResponseMapDto(
    @SerializedName("type")
    var type: MapPinType,
    @SerializedName("latitude")
    var latitude: Float,
    @SerializedName("longitude")
    var longitude: Float,
    @SerializedName("description")
    var description: String,
    @SerializedName("id")
    var id: Int
)