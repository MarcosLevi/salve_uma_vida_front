package br.com.salve_uma_vida_front.interfaces

import br.com.salve_uma_vida_front.dto.CampanhaDto

interface CardOngFavoritaListener{
    fun abrePerfilOng(id: Int)
    fun desfavoritarOngPorId(id: Int)
}