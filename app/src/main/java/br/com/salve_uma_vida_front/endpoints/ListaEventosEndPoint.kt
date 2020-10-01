package br.com.salve_uma_vida_front.endpoints

import br.com.salve_uma_vida_front.dto.AuthorizationRequestDto
import br.com.salve_uma_vida_front.dto.AuthorizationResponseDto
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ListaEventosEndPoint {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0ZUBnbWFpbC5jb20iLCJleHAiOjE2MDE1NzU0MzksImlhdCI6MTYwMTU3MTgzOX0.3Mt4fpzqCCN67mg3gsOTn8VaKJxjVqTpODMfe6UId_Pr9OXMJyZ5wQuTm3l9jxTD2104BJfP6aP_4utNvMHJGw")
    @GET("/event/4")
    fun getEventos(): Call<ResponseDto<EventoDto>>
}