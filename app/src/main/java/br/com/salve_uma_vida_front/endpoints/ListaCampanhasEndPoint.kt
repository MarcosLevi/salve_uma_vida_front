package br.com.salve_uma_vida_front.endpoints

import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import retrofit2.Call
import retrofit2.http.*

interface ListaCampanhasEndPoint {
    //precisa persistir o token
    @GET("/event/{id}")
    fun getCampanhaId(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<ResponseDto<CampanhaDto>>

    @GET("/campaigns")
    fun getCampanhasUserLogado(@Header("Authorization") token: String): Call<ResponseDto<List<CampanhaDto>>>

    @GET("/campaign/search")
    fun getCampanhas(
        @Header("Authorization") token: String,
        @Query("param") parametro: String
    ): Call<ResponseDto<List<CampanhaDto>>>
}