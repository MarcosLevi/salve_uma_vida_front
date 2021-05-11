package br.com.salve_uma_vida_front.repository

import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import br.com.salve_uma_vida_front.endpoints.EventosEndPoint
import br.com.salve_uma_vida_front.utils.NetworkUtils
import retrofit2.Call

class EventoRepository {

    fun getEventoId(id: Int, token: String): Call<ResponseDto<EventoDto>> {
        val endpoint = NetworkUtils.getRetrofitInstance().create(EventosEndPoint::class.java)
        return endpoint.getEventoId(id, token)
    }

    fun getEventos(
        token: String,
        parametro: String
    ): Call<ResponseDto<List<EventoDto>>> {
        val endpoint = NetworkUtils.getRetrofitInstance().create(EventosEndPoint::class.java)
        return endpoint.getEventos(token, parametro)
    }

    fun getEventosDeUmUserPeloId(
        token: String,
        id: Int
    ): Call<ResponseDto<List<EventoDto>>> {
        val endpoint = NetworkUtils.getRetrofitInstance().create(EventosEndPoint::class.java)
        return endpoint.getEventosDeUmUserPeloId(token, id)
    }

    fun getEventosUserLogado(token: String, parametro: String): Call<ResponseDto<List<EventoDto>>>{
        val endpoint = NetworkUtils.getRetrofitInstance().create(EventosEndPoint::class.java)
        return endpoint.getEventosUserLogado(token, parametro)
    }

    fun novoEvento(token: String, evento: EventoDto): Call<ResponseDto<String>> {
        val endpoint = NetworkUtils.getRetrofitInstance().create(EventosEndPoint::class.java)
        return endpoint.novoEvento(token, evento)
    }

    fun updateEvento(token: String, evento: EventoDto): Call<ResponseDto<String>> {
        val endpoint = NetworkUtils.getRetrofitInstance().create(EventosEndPoint::class.java)
        return endpoint.updateEvento(token, evento)
    }

    fun closeEventoId(id: Int, token: String): Call<ResponseDto<String>> {
        val endpoint = NetworkUtils.getRetrofitInstance().create(EventosEndPoint::class.java)
        return endpoint.closeEventoId(id, token)
    }
}