package br.com.salve_uma_vida_front.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.salve_uma_vida_front.fragments.PerfilOngCampanhasFragment
import br.com.salve_uma_vida_front.fragments.PerfilOngEventosFragment
import br.com.salve_uma_vida_front.fragments.PerfilOngGaleriaFragment
import br.com.salve_uma_vida_front.fragments.PerfilOngInfoFragment

class TabPerfilOngAdapter(fm: FragmentManager, numeroDeTabs: Int) : FragmentPagerAdapter(fm) {
    private var numeroDeTabs = numeroDeTabs

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> PerfilOngCampanhasFragment.newInstance()
            1 -> PerfilOngEventosFragment.newInstance()
            2 -> PerfilOngGaleriaFragment.newInstance()
            else -> PerfilOngInfoFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Campanhas"
            1 -> "Eventos"
            2 -> "Galeria"
            else -> "Info"
        }
    }

    override fun getCount(): Int {
        return numeroDeTabs
    }
}