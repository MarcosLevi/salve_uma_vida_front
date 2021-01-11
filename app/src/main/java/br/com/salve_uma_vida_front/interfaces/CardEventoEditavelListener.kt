package br.com.salve_uma_vida_front.interfaces

import br.com.salve_uma_vida_front.dto.EventoDto

interface CardEventoEditavelListener{
    fun onEditaClicked(evento: EventoDto)
    fun onArquivaClicked(evento: EventoDto)
    fun onFinalizaClicked(evento: EventoDto)
}