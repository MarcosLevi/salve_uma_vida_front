package br.com.salve_uma_vida_front.both.viewmodels

import android.app.Application
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.salve_uma_vida_front.both.models.DialogFiltros
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.FiltroPesquisaDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import br.com.salve_uma_vida_front.repository.CampanhaRepository
import br.com.salve_uma_vida_front.repository.EventoRepository
import br.com.salve_uma_vida_front.sharedpreferences.MyPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CampanhasEEventosViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val myPreferences = MyPreferences(context)
    private val token = "Bearer " + myPreferences.getToken()

    private val _listaEventos = MutableLiveData<MutableList<EventoDto>>()
    val listaEventos: LiveData<MutableList<EventoDto>> = _listaEventos

    private val _evento = MutableLiveData<EventoDto>()
    val evento: LiveData<EventoDto> = _evento

    private val _eventos = MutableLiveData<List<EventoDto>>()
    val eventos: LiveData<List<EventoDto>> = _eventos

    private val _meusEventos = MutableLiveData<List<EventoDto>>()
    val meusEventos: LiveData<List<EventoDto>> = _meusEventos

    private val _minhasCampanhas = MutableLiveData<List<CampanhaDto>>()
    val minhasCampanhas: LiveData<List<CampanhaDto>> = _minhasCampanhas

    private val _campanhas = MutableLiveData<List<CampanhaDto>>()
    val campanhas: LiveData<List<CampanhaDto>> = _campanhas

    private val _campanhaOuEvento = MutableLiveData<String>()
    val campanhaOuEvento: LiveData<String> = _campanhaOuEvento

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

    fun getCampanhasUserLogado(parametro: String) {
        val callback = CampanhaRepository().getCampanhasUserLogado(token,parametro)
        callback.enqueue(object : Callback<ResponseDto<List<CampanhaDto>>> {
            override fun onFailure(call: Call<ResponseDto<List<CampanhaDto>>>, t: Throwable) {
                Log.d("SearchViewModel", "Requisição falhou")
            }

            override fun onResponse(
                call: Call<ResponseDto<List<CampanhaDto>>>,
                response: Response<ResponseDto<List<CampanhaDto>>>
            ) {
                val campanha = response.body()?.data
                _minhasCampanhas.value = campanha

            }

        })
    }

    fun getEventosUserLogado(parametro: String) {
        val callback = EventoRepository().getEventosUserLogado(token,parametro)
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

    fun getCampanhas(parametro: String) {
        val callback = CampanhaRepository().getCampanhas(token, parametro)
        callback.enqueue(object : Callback<ResponseDto<List<CampanhaDto>>> {
            override fun onFailure(call: Call<ResponseDto<List<CampanhaDto>>>, t: Throwable) {
                Log.d("SearchViewModel", "Requisição falhou")
            }

            override fun onResponse(
                call: Call<ResponseDto<List<CampanhaDto>>>,
                response: Response<ResponseDto<List<CampanhaDto>>>
            ) {
                val campanhas = response.body()?.data
                _campanhas.value = campanhas

            }

        })
    }

    fun createDialog(fragmentManager: FragmentManager?) {

        var dialog = DialogFiltros(this)
        if (fragmentManager != null) {
            dialog.show(fragmentManager, "FiltroDialog")
        }

    }

    fun filtrar(filtro: FiltroPesquisaDto) {
        //nova lista de elementos
        //fazer a filtragem
        _campanhaOuEvento.value = filtro.tipoFiltro
    }
}