package br.com.salve_uma_vida_front.both.viewmodels

import android.app.Application
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.salve_uma_vida_front.both.models.DialogFiltros
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.FiltroPesquisaDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import br.com.salve_uma_vida_front.repository.EventoRepository
import br.com.salve_uma_vida_front.sharedpreferences.MyPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProcurarFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val myPreferences = MyPreferences(context)
    private val token = "Bearer " + myPreferences.getToken()
    private val _listaEventos = MutableLiveData<MutableList<EventoDto>>()
    val listaEventos: LiveData<MutableList<EventoDto>> = _listaEventos
    private val _evento = MutableLiveData<EventoDto>()
    val evento: LiveData<EventoDto> = _evento

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

    fun createDialog(fragmentManager: FragmentManager?) {

        var dialog = DialogFiltros()
        if (fragmentManager != null) {
            dialog.show(fragmentManager, "FiltroDialog")
        }

    }

    fun filtrar(filtro: FiltroPesquisaDto) {
        //nova lista de elementos
        var teste = filtro
    }
}