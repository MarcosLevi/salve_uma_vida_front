package br.com.salve_uma_vida_front.ongs.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.salve_uma_vida_front.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class OngMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ong_navigation)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view_ong)
        val navController = findNavController(R.id.nav_host_fragment_ong)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.procurarFragment, R.id.favoritosFragment, R.id.campanhasFragment, R.id.mensagensFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
    }
}