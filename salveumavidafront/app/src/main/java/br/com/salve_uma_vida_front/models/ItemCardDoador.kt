package br.com.salve_uma_vida_front.models

class ItemCardDoador(
    var titulo: String,
    val unidadeMedida: String,
    var quantidadeMaxima: Int,
    var quantidadeAtual: Int
) {
}