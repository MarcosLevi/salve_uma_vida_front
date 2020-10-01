package br.com.salve_uma_vida_front.both.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.salve_uma_vida_front.both.models.Campanha
import br.com.salve_uma_vida_front.both.models.UserType
import br.com.salve_uma_vida_front.dto.AuthorizationResponseDto
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import br.com.salve_uma_vida_front.repository.EventoRepository
import br.com.salve_uma_vida_front.repository.LoginRepository
import br.com.salve_uma_vida_front.repository.NetworkUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProcurarFragmentViewModel: ViewModel() {

    private val _listaEventos = MutableLiveData<EventoDto>()
    val listaEventos: LiveData<EventoDto> = _listaEventos

    fun getEventos(){
        val callback = EventoRepository().getEventos()
        callback.enqueue(object : Callback<ResponseDto<EventoDto>> {
            override fun onFailure(call: Call<ResponseDto<EventoDto>>, t: Throwable) {
                Log.d("SearchViewModel", "Requisição falhou")
            }

            override fun onResponse(
                call: Call<ResponseDto<EventoDto>>,
                response: Response<ResponseDto<EventoDto>>
            ) {
                val teste = response
                _listaEventos.value = response.body()!!.data
            }

        })
    }
//
//    fun doLogin(username: String, password: String) {
//        val callback = LoginRepository().login(username, password)
//        callback.enqueue(object: Callback<ResponseDto<AuthorizationResponseDto>> {
//            override fun onFailure(call: Call<ResponseDto<AuthorizationResponseDto>>, t: Throwable) {
//                Log.d("MainViewModel", "Requisição falhou")
//            }
//
//            override fun onResponse(call: Call<ResponseDto<AuthorizationResponseDto>>, response: Response<ResponseDto<AuthorizationResponseDto>>) {
//                val code = response.code()
//                when (code) {
//                    NetworkUtil.RESPONSE_OK -> loginOk(response.body()?.data as AuthorizationResponseDto)
//                    else -> loginError()
//                }
//            }
//
//        })
//    }
//
//    fun loginOk(response: AuthorizationResponseDto) {
//        _navigate.value = response.userType
//    }
//
//    fun loginError() {
//
//    }

}