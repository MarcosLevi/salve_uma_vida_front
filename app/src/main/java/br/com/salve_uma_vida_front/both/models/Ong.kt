package br.com.salve_uma_vida_front.both.models

import br.com.salve_uma_vida_front.dto.CampanhaDto

class Ong(
    var nomeDaOng: String,
    var imagemPrincipal: String,
    var galeriaDeImagens: MutableList<String>,
    var endereco: String,
    var telefone: String,
    var listaCampanhaDtos: MutableList<CampanhaDto>
) {
}