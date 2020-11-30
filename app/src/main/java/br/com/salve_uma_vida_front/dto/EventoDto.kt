package br.com.salve_uma_vida_front.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class EventoDto(
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var titulo: String,
    @SerializedName("address")
    var endereco: String,
    @SerializedName("addressLatitude")
    var latitude: Float,
    @SerializedName("addressLongitude")
    var longitude: Float,
    @SerializedName("image")
    var imagem: String,
    @SerializedName("description")
    var descricao: String,
    @SerializedName("date")
    var data:String
)