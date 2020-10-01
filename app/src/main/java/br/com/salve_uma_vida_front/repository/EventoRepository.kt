package br.com.salve_uma_vida_front.repository

import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import br.com.salve_uma_vida_front.endpoints.ListaEventosEndPoint
import retrofit2.Call

class EventoRepository {

    fun getEventos(): Call<ResponseDto<EventoDto>> {
        var endpoint = NetworkUtil.getRetrofitInstance().create(ListaEventosEndPoint::class.java)
        return endpoint.getEventos()
    }
}