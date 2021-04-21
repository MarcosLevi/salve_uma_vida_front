package br.com.salve_uma_vida_front.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.salve_uma_vida_front.dto.*
import br.com.salve_uma_vida_front.models.Responses
import br.com.salve_uma_vida_front.models.UserType
import br.com.salve_uma_vida_front.repository.UserRepository
import br.com.salve_uma_vida_front.utils.NetworkUtils
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
    private val _respostaDoBancoAoFavoritar = MutableLiveData<String>()
    val respostaDoBancoAoFavoritar: LiveData<String> = _respostaDoBancoAoFavoritar
    private val _respostaDoBancoAoDesfavoritar = MutableLiveData<Boolean>()
    val respostaDoBancoAoDesfavoritar: LiveData<Boolean> = _respostaDoBancoAoDesfavoritar
    private val _ongsFavoritasDoUserLogado = MutableLiveData<List<OngFavoritaDto>>()
    val ongsFavoritasDoUserLogado: LiveData<List<OngFavoritaDto>> = _ongsFavoritasDoUserLogado

    private val _findUserById = MutableLiveData<UserDto>()
    val findUserById: LiveData<UserDto> = _findUserById

    private val _profile = MutableLiveData<UserDto>()
    val profile: LiveData<UserDto> = _profile


    fun doLogin(username: String, password: String) {
        val callback = UserRepository().login(username, password)
        callback.enqueue(object: Callback<ResponseDto<AuthorizationResponseDto>> {
            override fun onFailure(call: Call<ResponseDto<AuthorizationResponseDto>>, t: Throwable) {
                loginError()
            }

            override fun onResponse(call: Call<ResponseDto<AuthorizationResponseDto>>, response: Response<ResponseDto<AuthorizationResponseDto>>) {
                val code = response.code()
                when (code) {
                    NetworkUtils.RESPONSE_OK -> loginOk(response.body()?.data as AuthorizationResponseDto)
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
        type: UserType,
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
                    NetworkUtils.RESPONSE_OK -> cadastroOk()
                    else -> cadastroNotOk()
                }
            }

        })
    }

    fun favoritarOngPorId(id: Int) {
        val callback = UserRepository().favoritarOngPorId(token,id)
        callback.enqueue(object : Callback<ResponseDto<String>>{
            override fun onFailure(call: Call<ResponseDto<String>>, t: Throwable) {
                Log.d("UserViewModel", "Requisição falhou")
            }

            override fun onResponse(
                call: Call<ResponseDto<String>>,
                response: Response<ResponseDto<String>>
            ) {
                val code = response.code()
                when (code) {
                    NetworkUtils.RESPONSE_OK -> _respostaDoBancoAoFavoritar.value= response.body()?.data
                    else -> _respostaDoBancoAoFavoritar.value = "Falha na requisição"
                }
            }

        })
    }

    fun getOngsFavororitasDoUserLogado() {
        val callback = UserRepository().getOngsFavororitasDoUserLogado(token)
        callback.enqueue(object : Callback<ResponseDto<List<OngFavoritaDto>>>{
            override fun onFailure(call: Call<ResponseDto<List<OngFavoritaDto>>>, t: Throwable) {
                Log.d("UserViewModel", "Requisição falhou")
            }

            override fun onResponse(
                call: Call<ResponseDto<List<OngFavoritaDto>>>,
                response: Response<ResponseDto<List<OngFavoritaDto>>>
            ) {
                val code = response.code()
                when (code) {
                    NetworkUtils.RESPONSE_OK -> _ongsFavoritasDoUserLogado.value= response.body()?.data
                    else -> _ongsFavoritasDoUserLogado.value = null
                }
            }

        })
    }

    fun desfavoritarOngPorId(id: Int) {
        val callback = UserRepository().desfavoritarOngPorId(token,id)
        callback.enqueue(object : Callback<ResponseDto<String>>{
            override fun onFailure(call: Call<ResponseDto<String>>, t: Throwable) {
                Log.d("UserViewModel", "Requisição falhou")
            }

            override fun onResponse(
                call: Call<ResponseDto<String>>,
                response: Response<ResponseDto<String>>
            ) {
                val code = response.code()
                when (code) {
                    NetworkUtils.RESPONSE_OK -> _respostaDoBancoAoDesfavoritar.value= true
                    else -> _respostaDoBancoAoDesfavoritar.value = false
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

    fun getProfile() {
        val callback = UserRepository().getProfile(token)
        callback.enqueue(object : Callback<ResponseDto<UserDto>> {
            override fun onFailure(call: Call<ResponseDto<UserDto>>, t: Throwable) {
                Log.d("SearchViewModel", "Requisição falhou")
            }

            override fun onResponse(
                call: Call<ResponseDto<UserDto>>,
                response: Response<ResponseDto<UserDto>>
            ) {
                val user = response.body()?.data
                _profile.value = user
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