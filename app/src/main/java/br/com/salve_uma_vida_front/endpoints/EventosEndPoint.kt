package br.com.salve_uma_vida_front.endpoints

import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import retrofit2.Call
import retrofit2.http.*

interface EventosEndPoint {
    //precisa persistir o token
    @GET("/event/{id}")
    fun getEvento(@Path("id") id: Int, @Header("Authorization") token: String): Call<ResponseDto<EventoDto>>

    @GET("/event/search")
    fun getEventos(
        @Header("Authorization") token: String,
        @Query("param") parametro: String
    ): Call<ResponseDto<List<EventoDto>>>
}