package br.com.salve_uma_vida_front

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.salve_uma_vida_front.both.models.Responses
import br.com.salve_uma_vida_front.both.models.UserType
import br.com.salve_uma_vida_front.dto.AuthorizationResponseDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import br.com.salve_uma_vida_front.dto.UserDto
import br.com.salve_uma_vida_front.dto.UserResponseDto
import br.com.salve_uma_vida_front.repository.UserRepository
import br.com.salve_uma_vida_front.repository.NetworkUtil
import br.com.salve_uma_vida_front.sharedpreferences.MyPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val myPreferences = MyPreferences(context)
    private val _navigate = MutableLiveData<UserType>()
    val navigate: LiveData<UserType> = _navigate
    private val _cadastro = MutableLiveData<Responses>()
    val cadastro: LiveData<Responses> = _cadastro

    fun doLogin(username: String, password: String) {
        val callback = UserRepository().login(username, password)
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

    fun signup(
        name: String,
        email: String,
        password: String,
        detail: String,
        address: String,
        type: String,
        image: String,
        addressLatitude: Float,
        addressLongitude: Float
    ) {
        var user = UserDto(name,email,password,detail,address,addressLatitude,addressLongitude,type,image)
        val callback = UserRepository().signup(user)
        callback.enqueue(object : Callback<ResponseDto<UserResponseDto>>{
            override fun onFailure(call: Call<ResponseDto<UserResponseDto>>, t: Throwable) {
                Log.d("MainViewModel", "Requisição falhou")
            }

            override fun onResponse(
                call: Call<ResponseDto<UserResponseDto>>,
                response: Response<ResponseDto<UserResponseDto>>
            ) {
                val code = response.code()
                when (code) {
                    NetworkUtil.RESPONSE_OK -> cadastroOk()
                    else -> cadastroNotOk()
                }
            }

        })
    }


    fun loginOk(response: AuthorizationResponseDto) {
        myPreferences.setToken(response.token)
        _navigate.value = response.userType
    }

    fun cadastroOk() {
        _cadastro.value = Responses.SUCESS
    }
    fun cadastroNotOk() {
        _cadastro.value = Responses.FAILED
    }

    fun loginError() {

    }
}