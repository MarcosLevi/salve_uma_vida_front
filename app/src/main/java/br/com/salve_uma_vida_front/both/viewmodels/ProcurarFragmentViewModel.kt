package br.com.salve_uma_vida_front.both.viewmodels

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.salve_uma_vida_front.both.models.DialogFiltros
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.dto.FiltroPesquisaDto
import br.com.salve_uma_vida_front.dto.ResponseDto
import br.com.salve_uma_vida_front.repository.EventoRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProcurarFragmentViewModel : ViewModel() {

    private val _listaEventos = MutableLiveData<EventoDto>()
    val listaEventos: LiveData<EventoDto> = _listaEventos

    fun getEvento(id: Int) {
        val callback = EventoRepository().getEvento(id)
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

    fun createDialog(fragmentManager: FragmentManager?) {

        var dialog = DialogFiltros()
        if (fragmentManager != null) {
            dialog.show(fragmentManager,"FiltroDialog")
        }

    }

    fun filtrar(filtro: FiltroPesquisaDto){
        //nova lista de elementos
        var teste = filtro
    }
}