package br.com.salve_uma_vida_front.both

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun NewCalendar(dia: Int, mes: Int, ano: Int): Calendar {
    val calendar = Calendar.getInstance()
    calendar.set(ano, mes, dia)
    return calendar
}

fun DateToString(calendar: Calendar): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    return formatter.format(calendar.time)
}

fun StringToDate(string: String): MutableList<Int> {
    val diaMesAno = string.split("/")
    return mutableListOf<Int>(diaMesAno.get(0).toInt(), diaMesAno.get(1).toInt(), diaMesAno.get(2).toInt())
}

//@RequiresApi(Build.VERSION_CODES.O)
//fun FormatStringToDate(string: String): String{
//    var localDateTime = LocalDateTime.parse("2018-01-28T13:42:17.546")
//    return localDateTime.toString()
//}