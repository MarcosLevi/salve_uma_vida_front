package br.com.salve_uma_vida_front.dto

import com.google.gson.annotations.SerializedName

data class CampanhaItemDto(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("description")
    var descricao: String = "",
    @SerializedName("goal")
    var maximo: Float = 0F,
    @SerializedName("progress")
    var progresso: Float = 0F,
    @SerializedName("unit")
    var unidade: String = ""
)