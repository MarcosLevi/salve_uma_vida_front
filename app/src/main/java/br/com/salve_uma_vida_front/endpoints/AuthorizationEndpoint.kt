package br.com.salve_uma_vida_front.endpoints

import br.com.salve_uma_vida_front.dto.AuthorizationRequestDto
import br.com.salve_uma_vida_front.dto.AuthorizationResponseDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthorizationEndpoint {

    @Headers("Content-Type: application/json")
    @POST("/authenticate")
    fun login(@Body body: AuthorizationRequestDto): Call<ResponseDto<AuthorizationResponseDto>>
}