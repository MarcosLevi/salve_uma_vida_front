package br.com.salve_uma_vida_front.both.models

import br.com.salve_uma_vida_front.both.models.ItemCard

class CardPesquisa(
    var titulo: String = "",
    val timeStamp: String = "",
    var descricao: String = "",
    var listaDeItens: MutableList<ItemCard> = mutableListOf(),
    var imagem: String = "",
    var favorito: Boolean = false
) {
    var quantidadeDeItens: Int

    init {
        this.quantidadeDeItens = listaDeItens.size
    }
}