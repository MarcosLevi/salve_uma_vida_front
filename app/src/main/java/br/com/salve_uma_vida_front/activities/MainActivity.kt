package br.com.salve_uma_vida_front.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.closeLoading
import br.com.salve_uma_vida_front.databinding.ActivityMainBinding
import br.com.salve_uma_vida_front.models.UserType
import br.com.salve_uma_vida_front.startLoading
import br.com.salve_uma_vida_front.viewmodels.UserViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: UserViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)


        binding.loginButton.setOnClickListener {
            val username = binding.usernameText.text.toString()
            val password = binding.passwordText.text.toString()
            viewModel.doLogin(username, password)
            startLoading(this, R.id.mainLoading)
//            val myIntent = Intent(this@MainActivity, OngMainActivity::class.java)
//            startActivity(myIntent)
        }
        binding.signupButton.setOnClickListener {
            Log.d("CadastroCampanha", "Cliquei")
            startActivity(Intent(this@MainActivity, ActivityCadastroUser::class.java))

        }
//        binding.signupButton.setOnClickListener {
//            val myIntent = Intent(this@MainActivity, DoadorMainActivity::class.java)
//            startActivity(myIntent)
//        }

        viewModel.navigate.observe(this, Observer {
            closeLoading(this, R.id.mainLoading)
            when (it) {
                UserType.COMMON -> startActivity(
                    Intent(
                        this@MainActivity,
                        DoadorMainActivity::class.java
                    )
                )
                else -> startActivity(Intent(this@MainActivity, OngMainActivity::class.java))
            }
        })
    }


}