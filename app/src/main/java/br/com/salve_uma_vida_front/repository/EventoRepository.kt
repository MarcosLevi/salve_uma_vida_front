package br.com.salve_uma_vida_front.repository

import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import br.com.salve_uma_vida_front.endpoints.CampanhasEndPoint
import br.com.salve_uma_vida_front.endpoints.EventosEndPoint
import retrofit2.Call

class EventoRepository {

    fun getEventoId(id: Int, token: String): Call<ResponseDto<EventoDto>> {
        val endpoint = NetworkUtil.getRetrofitInstance().create(EventosEndPoint::class.java)
        return endpoint.getEventoId(id, token)
    }

    fun getEventos(
        token: String,
        parametro: String
    ): Call<ResponseDto<List<EventoDto>>> {
        val endpoint = NetworkUtil.getRetrofitInstance().create(EventosEndPoint::class.java)
        return endpoint.getEventos(token, parametro)
    }

    fun getEventosDeUmUserPeloId(
        token: String,
        id: Int
    ): Call<ResponseDto<List<EventoDto>>> {
        val endpoint = NetworkUtil.getRetrofitInstance().create(EventosEndPoint::class.java)
        return endpoint.getEventosDeUmUserPeloId(token, id)
    }

    fun getEventosUserLogado(token: String, parametro: String): Call<ResponseDto<List<EventoDto>>>{
        val endpoint = NetworkUtil.getRetrofitInstance().create(EventosEndPoint::class.java)
        return endpoint.getEventosUserLogado(token, parametro)
    }

    fun novoEvento(token: String, evento: EventoDto): Call<ResponseDto<String>> {
        val endpoint = NetworkUtil.getRetrofitInstance().create(EventosEndPoint::class.java)
        return endpoint.novoEvento(token, evento)
    }

    fun updateEvento(token: String, evento: EventoDto): Call<ResponseDto<String>> {
        val endpoint = NetworkUtil.getRetrofitInstance().create(EventosEndPoint::class.java)
        return endpoint.updateEvento(token, evento)
    }
}