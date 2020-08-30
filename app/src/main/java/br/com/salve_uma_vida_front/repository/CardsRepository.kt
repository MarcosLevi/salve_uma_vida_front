package br.com.salve_uma_vida_front.repository

import br.com.salve_uma_vida_front.both.models.CardPesquisa
import br.com.salve_uma_vida_front.both.models.ItemCard

private var listaCards: MutableList<CardPesquisa> = mutableListOf()

//salva no banco
fun addCardNaLista(
    titulo: String,
    timestamp: String,
    descricao: String,
    listaItens: MutableList<ItemCard>,
    urlImagem: String
) {
    listaCards.add(
        CardPesquisa(
            titulo,
            timestamp,
            descricao,
            listaItens,
            urlImagem
        )
    )
}

//get do banco
fun getListaCards(): MutableList<CardPesquisa> {
    return listaCards
}