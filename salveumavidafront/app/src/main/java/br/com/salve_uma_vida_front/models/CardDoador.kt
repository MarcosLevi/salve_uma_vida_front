package br.com.salve_uma_vida_front.models

class CardDoador {
    var titulo: String
    val timeStamp: String
    var descricao: Int
    var listaDeItens: List<ItemCardDoador>
    var quantidadeDeItens: Int
    var imagem: String

    constructor(
        titulo: String,
        timeStamp: String,
        descricao: Int,
        listaDeItens: List<ItemCardDoador>,
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