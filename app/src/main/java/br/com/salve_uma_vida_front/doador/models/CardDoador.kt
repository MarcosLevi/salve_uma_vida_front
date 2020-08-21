package br.com.salve_uma_vida_front.doador.models

import br.com.salve_uma_vida_front.both.models.ItemCard

class CardDoador {
    var titulo: String
    val timeStamp: String
    var descricao: String
    var listaDeItens: List<ItemCard>
    var quantidadeDeItens: Int
    var imagem: String

    constructor(
        titulo: String,
        timeStamp: String,
        descricao: String,
        listaDeItens: List<ItemCard>,
        imagem: String
    ) {
        this.titulo = titulo
        this.timeStamp = timeStamp
        this.descricao = descricao
        this.listaDeItens = listaDeItens
        this.quantidadeDeItens = listaDeItens.size
        this.imagem = imagem
    }
}