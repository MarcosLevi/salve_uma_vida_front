package br.com.salve_uma_vida_front.endpoints

import br.com.salve_uma_vida_front.dto.AuthorizationRequestDto
import br.com.salve_uma_vida_front.dto.AuthorizationResponseDto
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import retrofit2.Call
import retrofit2.http.*

interface ListaEventosEndPoint {
    //precisa persistir o token
    @Headers("Authorization: Bearer ${"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0ZUBnbWFpbC5jb20iLCJleHAiOjE2MDIwODE3OTUsImlhdCI6MTYwMjA3ODE5NX0.DvH0wbXPM45gMIIf7T4dkuFauoYKrN3pyZ3INlhHCDjlJ31cLYLD3I6OpJd9uoe8BwlnhYd2-fOqk89ORIB1oA"}")
    @GET("/event/{id}")
    fun getEvento(@Path("id") id: Int): Call<ResponseDto<EventoDto>>
}