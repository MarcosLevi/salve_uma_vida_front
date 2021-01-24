package br.com.salve_uma_vida_front.interfaces

import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto

interface CardEventoFinalListener: CardFinalListener{
    fun abreEvento(evento: EventoDto)
}