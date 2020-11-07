package br.com.salve_uma_vida_front.both

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.location.Geocoder
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
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

fun FormatStringToDate(string: String): String{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
    val date = dateFormat.parse(string)
    val formatter = SimpleDateFormat("dd/MMM/yyyy");
    return "Ocorrerá em "+formatter.format(date)
}

fun AdressToLatLong(adress: String, applicationContext: Context): MutableList<Double> {
    val geocoder = Geocoder(applicationContext, Locale.getDefault())
    var fromLocationName = geocoder.getFromLocationName(adress, 1)
    var latitude = fromLocationName[0].latitude
    var longitude = fromLocationName[0].longitude
    return mutableListOf(latitude,longitude)
}

fun LatLongToAdress(latitude: Double,longitude:Double, applicationContext: Context): String {
    val geocoder = Geocoder(applicationContext, Locale.getDefault())
    var fromLocation = geocoder.getFromLocation(latitude, longitude, 1)
    return fromLocation[0].getAddressLine(0)
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()