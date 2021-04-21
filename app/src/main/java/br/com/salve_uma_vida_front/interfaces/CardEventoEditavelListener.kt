package br.com.salve_uma_vida_front.interfaces

import br.com.salve_uma_vida_front.dto.EventoDto

interface CardEventoEditavelListener{
    fun onArquivaClicked(evento: EventoDto)
}