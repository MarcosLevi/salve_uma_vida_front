package br.com.salve_uma_vida_front.repository

import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import br.com.salve_uma_vida_front.endpoints.ListaCampanhasEndPoint
import br.com.salve_uma_vida_front.endpoints.ListaEventosEndPoint
import retrofit2.Call

class EventoRepository {

    fun getEvento(id: Int, token: String): Call<ResponseDto<EventoDto>> {
        var endpoint = NetworkUtil.getRetrofitInstance().create(ListaEventosEndPoint::class.java)
        return endpoint.getEvento(id, token)
    }

    fun getEventos(
        token: String,
        parametro: String
    ): Call<ResponseDto<List<EventoDto>>> {
        var endpoint = NetworkUtil.getRetrofitInstance().create(ListaEventosEndPoint::class.java)
        return endpoint.getEventos(token, parametro)
    }
}