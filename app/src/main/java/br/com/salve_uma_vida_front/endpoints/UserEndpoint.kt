package br.com.salve_uma_vida_front.endpoints

import br.com.salve_uma_vida_front.dto.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserEndpoint {

    @Headers("Content-Type: application/json")
    @POST("/authenticate")
    fun login(@Body body: AuthorizationRequestDto): Call<ResponseDto<AuthorizationResponseDto>>

    @Headers("Content-Type: application/json")
    @POST("/signup")
    fun signup(@Body body: UserDto): Call<ResponseDto<UserResponseDto>>
}