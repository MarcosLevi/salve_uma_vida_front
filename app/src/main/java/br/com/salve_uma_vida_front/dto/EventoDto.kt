package br.com.salve_uma_vida_front.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class EventoDto(
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var titulo: String = "",
    @SerializedName("address")
    var endereco: String,
    @SerializedName("addressLatitude")
    var latitude: Float,
    @SerializedName("addressLongitude")
    var longitude: Float

//    var listaIdParticipantes: List<Int>,
//    val data: Calendar,
//    var descricao: String = ""
)