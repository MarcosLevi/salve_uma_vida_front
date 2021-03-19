package br.com.salve_uma_vida_front.repository

import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import br.com.salve_uma_vida_front.endpoints.CampanhasEndPoint
import br.com.salve_uma_vida_front.utils.NetworkUtils
import retrofit2.Call

class CampanhaRepository {

    fun getCampanhaId(token: String, id: Int): Call<ResponseDto<CampanhaDto>> {
        var endpoint = NetworkUtils.getRetrofitInstance().create(CampanhasEndPoint::class.java)
        return endpoint.getCampanhaId(token, id)
    }

    fun getCampanhasUserLogado(token: String,parametro: String): Call<ResponseDto<List<CampanhaDto>>> {
        var endpoint = NetworkUtils.getRetrofitInstance().create(CampanhasEndPoint::class.java)
        return endpoint.getCampanhasUserLogado(token,parametro)
    }

    fun getCampanhas(
        token: String,
        parametro: String
    ): Call<ResponseDto<List<CampanhaDto>>> {
        var endpoint = NetworkUtils.getRetrofitInstance().create(CampanhasEndPoint::class.java)
        return endpoint.getCampanhas(token, parametro)
    }

    fun getCampanhasDeUmUserPeloId(
        token: String,
        id: Int
    ): Call<ResponseDto<List<CampanhaDto>>> {
        var endpoint = NetworkUtils.getRetrofitInstance().create(CampanhasEndPoint::class.java)
        return endpoint.getCampanhasDeUmUserPeloId(token, id)
    }

    fun novaCampanha(token: String, campanha: CampanhaDto): Call<ResponseDto<String>> {
        var endpoint = NetworkUtils.getRetrofitInstance().create(CampanhasEndPoint::class.java)
        return endpoint.novaCampanha(token, campanha)
    }

    fun updateCampanha(token: String, campanha: CampanhaDto): Call<ResponseDto<String>> {
        var endpoint = NetworkUtils.getRetrofitInstance().create(CampanhasEndPoint::class.java)
        return endpoint.updateCampanha(token, campanha)
    }
}