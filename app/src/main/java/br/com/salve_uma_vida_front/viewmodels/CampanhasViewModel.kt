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

class CampanhasViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val myPreferences = MyPreferences(context)
    private val token = "Bearer " + myPreferences.getToken()

    private val _minhasCampanhas = MutableLiveData<List<CampanhaDto>>()
    val minhasCampanhas: LiveData<List<CampanhaDto>> = _minhasCampanhas

    private val _campanhas = MutableLiveData<List<CampanhaDto>>()
    val campanhas: LiveData<List<CampanhaDto>> = _campanhas


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
}