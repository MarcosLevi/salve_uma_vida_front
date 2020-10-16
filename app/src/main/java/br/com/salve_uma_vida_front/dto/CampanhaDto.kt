package br.com.salve_uma_vida_front.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.*

data class CampanhaDto(
    @SerializedName("id")
    var id: Int,
    @SerializedName("limitDate")
    var data:String,
    @SerializedName("title")
    var titulo: String,
    @SerializedName("description")
    var descricao: String,
    @SerializedName("items")
    var itens: List<CampanhaItemDto>,
    var favorito: Boolean
)