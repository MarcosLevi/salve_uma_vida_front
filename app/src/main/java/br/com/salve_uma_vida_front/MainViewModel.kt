package br.com.salve_uma_vida_front

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.salve_uma_vida_front.both.models.UserType
import br.com.salve_uma_vida_front.dto.AuthorizationResponseDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import br.com.salve_uma_vida_front.repository.LoginRepository
import br.com.salve_uma_vida_front.repository.NetworkUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _navigate = MutableLiveData<UserType>()
    val navigate: LiveData<UserType> = _navigate

    fun doLogin(username: String, password: String) {
        val callback = LoginRepository().login(username, password)
        callback.enqueue(object: Callback<ResponseDto<AuthorizationResponseDto>> {
            override fun onFailure(call: Call<ResponseDto<AuthorizationResponseDto>>, t: Throwable) {
                Log.d("MainViewModel", "Requisição falhou")
            }

            override fun onResponse(call: Call<ResponseDto<AuthorizationResponseDto>>, response: Response<ResponseDto<AuthorizationResponseDto>>) {
                val code = response.code()
                when (code) {
                    NetworkUtil.RESPONSE_OK -> loginOk(response.body()?.data as AuthorizationResponseDto)
                    else -> loginError()
                }
            }

        })
    }

    fun loginOk(response: AuthorizationResponseDto) {
        _navigate.value = response.userType
    }

    fun loginError() {

    }
}