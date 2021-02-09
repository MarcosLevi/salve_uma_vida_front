package br.com.salve_uma_vida_front.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import br.com.salve_uma_vida_front.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class OngMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ong_navigation)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view_ong)
        val navController = findNavController(R.id.nav_host_fragment_ong)
        val toolbar = findViewById<Toolbar>(R.id.ongToolbar)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.bothProcurarFragment, R.id.bothFavoritosFragment, R.id.ongCampanhasFragment, R.id.bothMapaFragment, R.id.bothUserLogadoFragment))
        toolbar.setupWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
    }
}