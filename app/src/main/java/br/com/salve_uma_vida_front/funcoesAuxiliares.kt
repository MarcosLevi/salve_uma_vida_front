package br.com.salve_uma_vida_front

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.location.Geocoder
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import java.text.SimpleDateFormat
import java.util.*

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, 0)
}

fun NewCalendar(dia: Int, mes: Int, ano: Int): Calendar {
    val calendar = Calendar.getInstance()
    calendar.set(ano, mes, dia)
    return calendar
}

fun DateToString(calendar: Calendar): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
    return formatter.format(calendar.time)
}

fun DateToStringBanco(calendar: Calendar): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale("pt", "BR"))
    return formatter.format(calendar.time)
}

fun FormatStringToDate(string: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
    val date = dateFormat.parse(string)
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(date)
}

fun adressToLatLong(adress: String, applicationContext: Context?): MutableList<Any> {
    val geocoder = Geocoder(applicationContext, Locale.getDefault())
    try {
        val fromLocationName = geocoder.getFromLocationName(adress, 1)
        val latitude = fromLocationName[0].latitude.toFloat()
        val longitude = fromLocationName[0].longitude.toFloat()
        val endereco = fromLocationName[0].getAddressLine(0)
        return mutableListOf(latitude, longitude, endereco)
    }catch (e: Exception){
        e.printStackTrace()
    }
    return mutableListOf("","","")
}



fun latLongToAdress(latitude: Float, longitude: Float, applicationContext: Context): String {
    val geocoder = Geocoder(applicationContext, Locale.getDefault())
    val fromLocation = geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
    return fromLocation[0].getAddressLine(0)
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun startLoading(activity: FragmentActivity?, id: Int) {
    activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    val progressBar = activity?.findViewById<ProgressBar>(id)
    progressBar?.visibility = View.VISIBLE
}

fun closeLoading(activity: FragmentActivity?, id: Int) {
    activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    val progressBar = activity?.findViewById<ProgressBar>(id)
    progressBar?.visibility = View.GONE
}

fun getToolbar(activity: FragmentActivity?): Toolbar? {
    val toolbar = activity?.findViewById<Toolbar>(R.id.ongToolbar)
    return toolbar
}

fun toolbarVazia(activity: FragmentActivity?): Toolbar? {
    val toolbar = activity?.findViewById<Toolbar>(R.id.ongToolbar)
    toolbar?.setBackgroundColor(activity.resources.getColor(R.color.corNeutra))
    toolbar?.menu?.clear()
    return toolbar
}

fun showText(context: Context?, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}