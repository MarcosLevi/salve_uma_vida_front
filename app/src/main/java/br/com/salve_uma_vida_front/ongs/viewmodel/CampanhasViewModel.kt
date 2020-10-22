package br.com.salve_uma_vida_front.ongs.viewmodel

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

class CampanhasViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val myPreferences = MyPreferences(context)
    private val token = "Bearer " + myPreferences.getToken()
    private val _listaEventos = MutableLiveData<MutableList<EventoDto>>()
    val listaEventos: LiveData<MutableList<EventoDto>> = _listaEventos
    private val _evento = MutableLiveData<EventoDto>()
    val evento: LiveData<EventoDto> = _evento
    private val _campanha = MutableLiveData<List<CampanhaDto>>()
    val campanha: LiveData<List<CampanhaDto>> = _campanha
    private val _campanhaOuEvento = MutableLiveData<String>()
    val campanhaOuEvento: LiveData<String> = _campanhaOuEvento

    fun getEvento(id: Int) {
        val callback = EventoRepository().getEvento(id, token)
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

    fun getCampanhasUserLogado() {
        val callback = CampanhaRepository().getCampanhasUserLogado(token)
        callback.enqueue(object : Callback<ResponseDto<List<CampanhaDto>>> {
            override fun onFailure(call: Call<ResponseDto<List<CampanhaDto>>>, t: Throwable) {
                Log.d("SearchViewModel", "Requisição falhou")
            }

            override fun onResponse(
                call: Call<ResponseDto<List<CampanhaDto>>>,
                response: Response<ResponseDto<List<CampanhaDto>>>
            ) {
                val campanha = response.body()!!.data
                _campanha.value = campanha

            }

        })
    }

    fun createDialog(fragmentManager: FragmentManager?) {

        var dialog = DialogFiltros()
        if (fragmentManager != null) {
            dialog.show(fragmentManager, "FiltroDialog")
        }

    }

    fun filtrar(filtro: FiltroPesquisaDto) {
        //nova lista de elementos
        //fazer a filtragem
        var teste = filtro
        _campanhaOuEvento.value = filtro.tipoFiltro
    }
}