package br.com.salve_uma_vida_front.interfaces

import br.com.salve_uma_vida_front.dto.CampanhaDto

interface CardCampanhaFinalListener{
    fun abrePerfilOng(campanha: CampanhaDto)
    fun abreCampanha(campanha: CampanhaDto)
}