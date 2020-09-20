package br.com.salve_uma_vida_front.repository

import br.com.salve_uma_vida_front.dto.AuthorizationRequestDto
import br.com.salve_uma_vida_front.dto.AuthorizationResponseDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import br.com.salve_uma_vida_front.endpoints.AuthorizationEndpoint
import retrofit2.Call

class LoginRepository {

    fun login(username: String, password: String): Call<ResponseDto<AuthorizationResponseDto>> {
        var endpoint = NetworkUtil.getRetrofitInstance().create(AuthorizationEndpoint::class.java)
        return endpoint.login(AuthorizationRequestDto(username, password))
    }
}