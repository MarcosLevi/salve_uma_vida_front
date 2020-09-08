package br.com.salve_uma_vida_front.both.models

import java.time.LocalDate
import java.util.*

class Campanha(
    var ong: Ong,
    var titulo: String = "",
    val timeStamp: Calendar,
    var descricao: String = "",
    var listaDeItens: MutableList<ItemCampanha>,
    var favorito: Boolean = false
) {

    var quantidadeDeItens: Int
    var imagemCampanha: String

    init {
        ong.listaCampanhas.add(this)
        this.quantidadeDeItens = listaDeItens.size
        this.imagemCampanha = ong.imagemPrincipal
    }

}