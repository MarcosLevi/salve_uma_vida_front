package br.com.salve_uma_vida_front.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable
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
    var data: String? = null,
    @SerializedName("userId")
    var userId: Int? = null
) : Serializable {
    companion object {
        @JvmStatic
        fun newInstance(
            evento: EventoDto
        ) = EventoDto(evento.id, evento.titulo, evento.endereco, evento.latitude, evento.longitude, evento.imagem, evento.descricao, evento.data)
    }
}