package br.com.salve_uma_vida_front.endpoints

import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import br.com.salve_uma_vida_front.dto.UserDto
import retrofit2.Call
import retrofit2.http.*

interface EventosEndPoint {
    //precisa persistir o token
    @GET("/event/{id}")
    fun getEventoId(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Call<ResponseDto<EventoDto>>

    @GET("/events")
    fun getEventosUserLogado(
        @Header("Authorization") token: String,
        @Query("param") parametro: String
    ): Call<ResponseDto<List<EventoDto>>>

    @GET("/event/search")
    fun getEventos(
        @Header("Authorization") token: String,
        @Query("param") parametro: String
    ): Call<ResponseDto<List<EventoDto>>>

    @GET("/user/{id}/events")
    fun getEventosDeUmUserPeloId(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<ResponseDto<List<EventoDto>>>

    @Headers("Content-Type: application/json")
    @POST("/event")
    fun novoEvento(
        @Header("Authorization") token: String,
        @Body body: EventoDto
    ): Call<ResponseDto<String>>

    @Headers("Content-Type: application/json")
    @PUT("/event")
    fun updateEvento(
        @Header("Authorization") token: String,
        @Body body: EventoDto
    ): Call<ResponseDto<String>>

    @Headers("Content-Type: application/json")
    @GET("event/close/{id}")
    fun closeEventoId(
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Call<ResponseDto<String>>
}