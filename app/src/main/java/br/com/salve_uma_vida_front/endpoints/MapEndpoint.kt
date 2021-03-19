package br.com.salve_uma_vida_front.endpoints

import br.com.salve_uma_vida_front.dto.*
import retrofit2.Call
import retrofit2.http.*

interface MapEndpoint {
    @Headers("Content-Type: application/json")
    @GET("/map")
    fun getMapInfo(
        @Header("Authorization") token: String
    ): Call<ResponseDto<List<ResponseMapDto>>>
}