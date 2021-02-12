package br.com.salve_uma_vida_front.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import br.com.salve_uma_vida_front.dto.UserDto
import br.com.salve_uma_vida_front.fragments.PerfilOngCampanhasFragment
import br.com.salve_uma_vida_front.fragments.PerfilOngEventosFragment
import br.com.salve_uma_vida_front.fragments.PerfilOngGaleriaFragment
import br.com.salve_uma_vida_front.fragments.PerfilOngInfoFragment

class TabPerfilOngAdapter(val numeroDeTabs: Int, val user: UserDto, val idOng: Int, fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    private val campanha = PerfilOngCampanhasFragment.newInstance(user,idOng)
    private val evento = PerfilOngEventosFragment.newInstance(user,idOng)
    private val galeria = PerfilOngGaleriaFragment.newInstance(user)
    private val info = PerfilOngInfoFragment.newInstance(user)

    override fun getItemCount(): Int {
        return numeroDeTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> info
            1 -> campanha
            2 -> evento
            else -> galeria
        }
    }
}