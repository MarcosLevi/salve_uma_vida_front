package br.com.salve_uma_vida_front.repository

import br.com.salve_uma_vida_front.dto.*
import br.com.salve_uma_vida_front.endpoints.CampanhasEndPoint
import br.com.salve_uma_vida_front.endpoints.UserEndpoint
import retrofit2.Call

class UserRepository {

    fun login(username: String, password: String): Call<ResponseDto<AuthorizationResponseDto>> {
        var endpoint = NetworkUtil.getRetrofitInstance().create(UserEndpoint::class.java)
        return endpoint.login(AuthorizationRequestDto(username, password))
    }

    fun signup(user: UserDto): Call<ResponseDto<UserResponseDto>> {
        var endpoint = NetworkUtil.getRetrofitInstance().create(UserEndpoint::class.java)
        return endpoint.signup(user)
    }

    fun getUserById(token: String, id: Int): Call<ResponseDto<UserDto>> {
        var endpoint = NetworkUtil.getRetrofitInstance().create(UserEndpoint::class.java)
        return endpoint.getUserById(token, id)
    }
}