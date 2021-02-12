package br.com.salve_uma_vida_front.endpoints

import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import retrofit2.Call
import retrofit2.http.*

interface CampanhasEndPoint {
    //Ainda não tem
    @GET("/campaign/{id}")
    fun getCampanhaId(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<ResponseDto<CampanhaDto>>

    @GET("/campaigns")
    fun getCampanhasUserLogado(
        @Header("Authorization") token: String,
        @Query("param") parametro: String
    ): Call<ResponseDto<List<CampanhaDto>>>

    @GET("/campaign/search")
    fun getCampanhas(
        @Header("Authorization") token: String,
        @Query("param") parametro: String
    ): Call<ResponseDto<List<CampanhaDto>>>

    @GET("/user/{id}/campaigns")
    fun getCampanhasDeUmUserPeloId(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<ResponseDto<List<CampanhaDto>>>

    @Headers("Content-Type: application/json")
    @POST("/campaign")
    fun novaCampanha(
        @Header("Authorization") token: String,
        @Body body: CampanhaDto
    ): Call<ResponseDto<String>>

    @Headers("Content-Type: application/json")
    @PUT("/campaign")
    fun updateCampanha(
        @Header("Authorization") token: String,
        @Body body: CampanhaDto
    ): Call<ResponseDto<String>>
}