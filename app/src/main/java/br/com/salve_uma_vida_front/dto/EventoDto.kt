package br.com.salve_uma_vida_front.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class EventoDto(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("title")
    var titulo: String? = null,
    @SerializedName("address")
    var endereco: String? = null,
    @SerializedName("addressLatitude")
    var latitude: Float? = null,
    @SerializedName("addressLongitude")
    var longitude: Float? = null,
    @SerializedName("image")
    var imagem: String? = null,
    @SerializedName("description")
    var descricao: String? = null,
    @SerializedName("date")
    var data:String? = null
)