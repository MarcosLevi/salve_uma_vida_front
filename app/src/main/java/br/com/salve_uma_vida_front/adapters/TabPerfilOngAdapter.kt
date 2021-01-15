package br.com.salve_uma_vida_front.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import br.com.salve_uma_vida_front.fragments.PerfilOngCampanhasFragment
import br.com.salve_uma_vida_front.fragments.PerfilOngEventosFragment
import br.com.salve_uma_vida_front.fragments.PerfilOngGaleriaFragment
import br.com.salve_uma_vida_front.fragments.PerfilOngInfoFragment
import br.com.salve_uma_vida_front.models.DialogGallery
import br.com.salve_uma_vida_front.models.ScaleType
import com.squareup.picasso.Picasso

class TabPerfilOngAdapter(fm: FragmentManager, numeroDeTabs: Int) : FragmentPagerAdapter(fm) {
    private var numeroDeTabs = numeroDeTabs

    override fun getItem(position: Int): Fragment {
        return when(position){
            1 -> PerfilOngCampanhasFragment.newInstance()
            2 -> PerfilOngEventosFragment.newInstance()
            3 -> PerfilOngGaleriaFragment.newInstance()
            else -> PerfilOngInfoFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return numeroDeTabs
    }
}