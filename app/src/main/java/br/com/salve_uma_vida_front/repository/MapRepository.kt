package br.com.salve_uma_vida_front.repository

import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import br.com.salve_uma_vida_front.dto.ResponseMapDto
import br.com.salve_uma_vida_front.endpoints.EventosEndPoint
import br.com.salve_uma_vida_front.endpoints.MapEndpoint
import br.com.salve_uma_vida_front.utils.NetworkUtils
import retrofit2.Call

class MapRepository {

    fun getMapInfo(token: String): Call<ResponseDto<List<ResponseMapDto>>> {
        val endpoint = NetworkUtils.getRetrofitInstance().create(MapEndpoint::class.java)
        return endpoint.getMapInfo(token)
    }
}