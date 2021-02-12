package br.com.salve_uma_vida_front.endpoints

import br.com.salve_uma_vida_front.dto.*
import retrofit2.Call
import retrofit2.http.*

interface UserEndpoint {

    @Headers("Content-Type: application/json")
    @POST("/authenticate")
    fun login(@Body body: AuthorizationRequestDto): Call<ResponseDto<AuthorizationResponseDto>>

    @Headers("Content-Type: application/json")
    @POST("/signup")
    fun signup(@Body body: UserDto): Call<ResponseDto<UserResponseDto>>

    @Headers("Content-Type: application/json")
    @POST("/user/favorites")
    fun favoritarOngPorId(
        @Header("Authorization") token: String,
        @Body body: BodyFavoritarDesfavoritarDto
    ): Call<ResponseDto<String>>

    @HTTP(method = "DELETE", path = "/user/favorites", hasBody = true)
    fun desfavoritarOngPorId(
        @Header("Authorization") token: String,
        @Body body: BodyFavoritarDesfavoritarDto,
        @Header("Content-Type") content_type: String = "application/json"
    ): Call<ResponseDto<String>>

    @Headers("Content-Type: application/json")
    @GET("/user/favorites")
    fun getOngsfavoritas(
        @Header("Authorization") token: String
    ): Call<ResponseDto<List<OngFavoritaDto>>>

    @GET("/user/{id}")
    fun getUserById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<ResponseDto<UserDto>>
}