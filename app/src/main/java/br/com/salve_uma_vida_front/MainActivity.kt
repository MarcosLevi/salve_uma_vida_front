package br.com.salve_uma_vida_front

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import br.com.salve_uma_vida_front.doador.activities.DoadorMainActivity
import br.com.salve_uma_vida_front.ongs.activities.OngMainActivity


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val buttonOng = findViewById<Button>(R.id.mainButtonOngs)
        val buttonDoador = findViewById<Button>(R.id.mainButtonDoador)
        buttonOng.setOnClickListener {
            viewModel.doLogin("teste@gmail.com", "teste")
//            val myIntent = Intent(this@MainActivity, OngMainActivity::class.java)
//            startActivity(myIntent)
        }
        buttonDoador.setOnClickListener {
            val myIntent = Intent(this@MainActivity, DoadorMainActivity::class.java)
            startActivity(myIntent)
        }

        //Fazer esquema de login e tals
    }
}