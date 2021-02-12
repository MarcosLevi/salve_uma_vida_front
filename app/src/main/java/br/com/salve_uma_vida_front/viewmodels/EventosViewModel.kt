package br.com.salve_uma_vida_front.viewmodels

import android.app.Application
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.salve_uma_vida_front.models.DialogFiltros
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.FiltroPesquisaDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import br.com.salve_uma_vida_front.models.SearchType
import br.com.salve_uma_vida_front.repository.CampanhaRepository
import br.com.salve_uma_vida_front.repository.EventoRepository
import br.com.salve_uma_vida_front.sharedpreferences.MyPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventosViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val myPreferences = MyPreferences(context)
    private val token = "Bearer " + myPreferences.getToken()

    private val _listaEventos = MutableLiveData<MutableList<EventoDto>>()
    val listaEventos: LiveData<MutableList<EventoDto>> = _listaEventos

    private val _evento = MutableLiveData<EventoDto>()
    val evento: LiveData<EventoDto> = _evento

    private val _eventos = MutableLiveData<List<EventoDto>>()
    val eventos: LiveData<List<EventoDto>> = _eventos

    private val _eventosDeUmUser = MutableLiveData<List<EventoDto>>()
    val eventosDeUmUser: LiveData<List<EventoDto>> = _eventosDeUmUser

    private val _meusEventos = MutableLiveData<List<EventoDto>>()
    val meusEventos: LiveData<List<EventoDto>> = _meusEventos

    private val _novoEvento = MutableLiveData<String>()
    val novoEvento: LiveData<String> = _novoEvento

    private val _updateEvento = MutableLiveData<String>()
    val updateEvento: LiveData<String> = _updateEvento


    fun getEventoId(id: Int) {
        val callback = EventoRepository().getEventoId(id, token)
        callback.enqueue(object : Callback<ResponseDto<EventoDto>> {
            override fun onFailure(call: Call<ResponseDto<EventoDto>>, t: Throwable) {
                Log.d("SearchViewModel", "Requisição falhou")
            }

            override fun onResponse(
                call: Call<ResponseDto<EventoDto>>,
                response: Response<ResponseDto<EventoDto>>
            ) {
                val evento = response.body()!!.data
                _evento.value = evento

            }

        })
    }

    fun getEventos(parametro: String) {
        val callback = EventoRepository().getEventos(token, parametro)
        callback.enqueue(object : Callback<ResponseDto<List<EventoDto>>> {
            override fun onFailure(call: Call<ResponseDto<List<EventoDto>>>, t: Throwable) {
                Log.d("SearchViewModel", "Requisição falhou")
            }

            override fun onResponse(
                call: Call<ResponseDto<List<EventoDto>>>,
                response: Response<ResponseDto<List<EventoDto>>>
            ) {
                val eventos = response.body()?.data
                _eventos.value = eventos

            }

        })
    }

    fun getEventosDeUmUserPeloId(id: Int) {
        val callback = EventoRepository().getEventosDeUmUserPeloId(token, id)
        callback.enqueue(object : Callback<ResponseDto<List<EventoDto>>> {
            override fun onFailure(call: Call<ResponseDto<List<EventoDto>>>, t: Throwable) {
                Log.d("SearchViewModel", "Requisição falhou")
            }

            override fun onResponse(
                call: Call<ResponseDto<List<EventoDto>>>,
                response: Response<ResponseDto<List<EventoDto>>>
            ) {
                val eventos = response.body()?.data
                _eventosDeUmUser.value = eventos
            }
        })
    }

    fun getEventosUserLogado(parametro: String = "") {
        val callback = EventoRepository().getEventosUserLogado(token, parametro)
        callback.enqueue(object : Callback<ResponseDto<List<EventoDto>>> {
            override fun onFailure(call: Call<ResponseDto<List<EventoDto>>>, t: Throwable) {
                Log.d("SearchViewModel", "Requisição falhou")
            }

            override fun onResponse(
                call: Call<ResponseDto<List<EventoDto>>>,
                response: Response<ResponseDto<List<EventoDto>>>
            ) {
                val eventos = response.body()?.data
                _meusEventos.value = eventos

            }

        })
    }

    fun novoEvento(evento: EventoDto) {
        val callback = EventoRepository().novoEvento(token, evento)
        callback.enqueue(object : Callback<ResponseDto<String>> {
            override fun onFailure(call: Call<ResponseDto<String>>, t: Throwable) {
                Log.d("SearchViewModel", "Requisição falhou")
            }

            override fun onResponse(
                call: Call<ResponseDto<String>>,
                response: Response<ResponseDto<String>>
            ) {
                val resposta = response.body()?.data
                _novoEvento.value = resposta
            }
        })
    }

    fun updateEvento(evento: EventoDto) {
        val callback = EventoRepository().updateEvento(token, evento)
        callback.enqueue(object : Callback<ResponseDto<String>> {
            override fun onFailure(call: Call<ResponseDto<String>>, t: Throwable) {
                Log.d("SearchViewModel", "Requisição falhou")
            }

            override fun onResponse(
                call: Call<ResponseDto<String>>,
                response: Response<ResponseDto<String>>
            ) {
                val resposta = response.body()?.data
                _updateEvento.value = resposta
            }
        })
    }
}