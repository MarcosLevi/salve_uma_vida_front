package br.com.salve_uma_vida_front.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.*

data class CampanhaItemDto(
    @SerializedName("id")
    var id: Int,
    @SerializedName("description")
    var descricao: String,
    @SerializedName("goal")
    var maximo:Float,
    @SerializedName("progress")
    var progresso: Float,
    @SerializedName("unit")
    var unidade: String
)