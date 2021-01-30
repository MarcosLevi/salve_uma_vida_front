package br.com.salve_uma_vida_front.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.salve_uma_vida_front.dto.*
import br.com.salve_uma_vida_front.models.Responses
import br.com.salve_uma_vida_front.models.UserType
import br.com.salve_uma_vida_front.repository.CampanhaRepository
import br.com.salve_uma_vida_front.repository.UserRepository
import br.com.salve_uma_vida_front.repository.NetworkUtil
import br.com.salve_uma_vida_front.sharedpreferences.MyPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val myPreferences = MyPreferences(context)
    private val token = "Bearer " + myPreferences.getToken()
    private val _navigate = MutableLiveData<UserType>()
    val navigate: LiveData<UserType> = _navigate
    private val _cadastro = MutableLiveData<Responses>()
    val cadastro: LiveData<Responses> = _cadastro
    private val _atualiza = MutableLiveData<Responses>()
    val atualiza: LiveData<Responses> = _atualiza
    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> = _loginError

    private val _findUserById = MutableLiveData<UserDto>()
    val findUserById: LiveData<UserDto> = _findUserById


    fun doLogin(username: String, password: String) {
        val callback = UserRepository().login(username, password)
        callback.enqueue(object: Callback<ResponseDto<AuthorizationResponseDto>> {
            override fun onFailure(call: Call<ResponseDto<AuthorizationResponseDto>>, t: Throwable) {
                loginError()
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
        type: String,
        image: String,
        address: String="",
        addressLatitude: Float = 0.0F,
        addressLongitude: Float = 0.0F
    ) {
        var user = UserDto(name,email,password,detail,address,addressLatitude,addressLongitude,type,image)
        val callback = UserRepository().signup(user)
        callback.enqueue(object : Callback<ResponseDto<UserResponseDto>>{
            override fun onFailure(call: Call<ResponseDto<UserResponseDto>>, t: Throwable) {
                Log.d("UserViewModel", "Requisição falhou")
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

    fun getUserById(id: Int) {
        val callback = UserRepository().getUserById(token, id)
        callback.enqueue(object : Callback<ResponseDto<UserDto>> {
            override fun onFailure(call: Call<ResponseDto<UserDto>>, t: Throwable) {
                Log.d("SearchViewModel", "Requisição falhou")
            }

            override fun onResponse(
                call: Call<ResponseDto<UserDto>>,
                response: Response<ResponseDto<UserDto>>
            ) {
                val user = response.body()?.data
                _findUserById.value = user
            }
        })
    }



    fun atualizar(){

    }


    fun loginOk(response: AuthorizationResponseDto) {
        myPreferences.setToken(response.token)
        _navigate.value = response.userType
    }

    fun cadastroOk() {
        Log.d("UserViewModel", "Requisição com sucesso")
        _cadastro.value = Responses.SUCESS
    }
    fun cadastroNotOk() {
        Log.d("UserViewModel", "Requisição falhou")
        _cadastro.value = Responses.FAILED
    }

    fun loginError() {
        _loginError.value = "Requisição falhou, tente novamente mais tarde"
    }
}