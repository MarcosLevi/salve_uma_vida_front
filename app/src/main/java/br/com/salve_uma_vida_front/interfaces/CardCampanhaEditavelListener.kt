package br.com.salve_uma_vida_front.interfaces

import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto

interface CardCampanhaEditavelListener{
    fun onEditaClicked(campanha: CampanhaDto)
    fun onArquivaClicked(campanha: CampanhaDto)
    fun onFinalizaClicked(campanha: CampanhaDto)
}