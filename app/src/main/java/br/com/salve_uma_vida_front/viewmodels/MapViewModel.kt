package br.com.salve_uma_vida_front.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.salve_uma_vida_front.dto.*
import br.com.salve_uma_vida_front.repository.MapRepository
import br.com.salve_uma_vida_front.sharedpreferences.MyPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext
    private val myPreferences = MyPreferences(context)
    private val token = "Bearer " + myPreferences.getToken()

    private val _mapInfo = MutableLiveData<List<ResponseMapDto>>()
    val mapInfo: LiveData<List<ResponseMapDto>> = _mapInfo

    fun getMapInfo() {
        val callback = MapRepository().getMapInfo(token)
        callback.enqueue(object : Callback<ResponseDto<List<ResponseMapDto>>> {
            override fun onFailure(call: Call<ResponseDto<List<ResponseMapDto>>>, t: Throwable) {
                Log.d("MapViewModel", "Requisição falhou")
            }
            override fun onResponse(
                call: Call<ResponseDto<List<ResponseMapDto>>>,
                response: Response<ResponseDto<List<ResponseMapDto>>>
            ) {
                val mapInfo = response.body()?.data
                _mapInfo.value = mapInfo
            }
        })
    }
}