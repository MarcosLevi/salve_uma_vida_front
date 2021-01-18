package br.com.salve_uma_vida_front.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CampanhaDto(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("limitDate")
    var data: String = "",
    @SerializedName("title")
    var titulo: String = "",
    @SerializedName("description")
    var descricao: String = "",
    @SerializedName("items")
    var itens: MutableList<CampanhaItemDto> = mutableListOf(),
    @SerializedName("userImage")
    var userImage: String? = null,
    @SerializedName("userId")
    var userId: Int? = null
) : Serializable {
    companion object {
        @JvmStatic
        fun newInstance(
            campanha: CampanhaDto
        ) = CampanhaDto(campanha.id, campanha.data, campanha.titulo, campanha.descricao, campanha.itens, campanha.userImage, campanha.userId)
    }
}