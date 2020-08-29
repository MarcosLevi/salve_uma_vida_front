package br.com.salve_uma_vida_front.ongs.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.ongs.fragments.CampanhasFragment
import br.com.salve_uma_vida_front.ongs.fragments.FavoritosFragment
import br.com.salve_uma_vida_front.ongs.fragments.MensagensFragment
import br.com.salve_uma_vida_front.ongs.fragments.ProcurarFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class OngMainActivity : AppCompatActivity() {
    lateinit var procurarFragment: ProcurarFragment
    lateinit var campanhasFragment: CampanhasFragment
    lateinit var favoritosFragment: FavoritosFragment
    lateinit var mensagensFragment: MensagensFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ong_navigation)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_messages -> {
                    mensagensFragment = MensagensFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_ong, mensagensFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.navigation_procurar -> {
                    procurarFragment = ProcurarFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_ong, procurarFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.navigation_favoritos -> {
                    favoritosFragment = FavoritosFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_ong, favoritosFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.navigation_campanhas -> {
                    campanhasFragment = CampanhasFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment_ong, campanhasFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }
            true
        }
    }
}