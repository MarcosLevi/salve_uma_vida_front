package br.com.salve_uma_vida_front.endpoints

import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import retrofit2.Call
import retrofit2.http.*

interface ListaEventosEndPoint {
    //precisa persistir o token
    @GET("/event/{id}")
    fun getEvento(@Path("id") id: Int, @Header("Authorization") token: String): Call<ResponseDto<EventoDto>>
}