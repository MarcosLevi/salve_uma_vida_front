package br.com.salve_uma_vida_front.sharedpreferences

import android.content.Context

class MyPreferences(context: Context) {
    val PREFERENCE_NAME = "SharedPreferences"
    val PREFERENCE_TOKEN = "Token"

    val preference = context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)

    fun getToken(): String
    {
        return preference.getString(PREFERENCE_TOKEN,"").toString()
    }

    fun setToken(token: String){
        val editor = preference.edit()
        editor.putString(PREFERENCE_TOKEN,token)
        editor.apply()
    }
}