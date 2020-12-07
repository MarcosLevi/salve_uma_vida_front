package br.com.salve_uma_vida_front.dto

import br.com.salve_uma_vida_front.models.SearchType

class FiltroPesquisaDto (
    var tipoFiltro: SearchType,
    var distancia: Int
)