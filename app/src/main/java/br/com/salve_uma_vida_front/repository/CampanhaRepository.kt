package br.com.salve_uma_vida_front.repository

import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import br.com.salve_uma_vida_front.endpoints.CampanhasEndPoint
import retrofit2.Call

class CampanhaRepository {

    fun getCampanhaId(id: Int, token: String): Call<ResponseDto<CampanhaDto>> {
        var endpoint = NetworkUtil.getRetrofitInstance().create(CampanhasEndPoint::class.java)
        return endpoint.getCampanhaId(token, id)
    }

    fun getCampanhasUserLogado(token: String): Call<ResponseDto<List<CampanhaDto>>> {
        var endpoint = NetworkUtil.getRetrofitInstance().create(CampanhasEndPoint::class.java)
        return endpoint.getCampanhasUserLogado(token)
    }

    fun getCampanhas(
        token: String,
        parametro: String
    ): Call<ResponseDto<List<CampanhaDto>>> {
        var endpoint = NetworkUtil.getRetrofitInstance().create(CampanhasEndPoint::class.java)
        return endpoint.getCampanhas(token, parametro)
    }
}