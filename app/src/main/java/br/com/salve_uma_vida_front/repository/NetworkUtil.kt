package br.com.salve_uma_vida_front.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkUtil {

    companion object {

        val RESPONSE_OK = 200
        val RESPONSE_UNAUTHORIZED = 401

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://calm-basin-56722.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}