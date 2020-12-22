package br.com.salve_uma_vida_front.dto

import br.com.salve_uma_vida_front.models.SearchType
import java.io.Serializable

class FiltroPesquisaDto (
    var tipoFiltro: SearchType = SearchType.CAMPANHAS,
    var distancia: Int = 0
): Serializable