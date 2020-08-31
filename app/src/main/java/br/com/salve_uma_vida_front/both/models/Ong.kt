package br.com.salve_uma_vida_front.both.models

import java.nio.file.attribute.GroupPrincipal

class Ong(
    var nomeDaOng: String,
    var imagemPrincipal: String,
    var galeriaDeImagens: MutableList<String>,
    var endereco: String,
    var telefone: String
) {
}