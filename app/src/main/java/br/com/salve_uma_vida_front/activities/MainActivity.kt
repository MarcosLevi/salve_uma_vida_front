package br.com.salve_uma_vida_front.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.salve_uma_vida_front.viewmodels.UserViewModel
import br.com.salve_uma_vida_front.models.LoadingDialog
import br.com.salve_uma_vida_front.models.UserType
import br.com.salve_uma_vida_front.databinding.ActivityMainBinding


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
            startLoading()
//            val myIntent = Intent(this@MainActivity, OngMainActivity::class.java)
//            startActivity(myIntent)
        }
        binding.signupButton.setOnClickListener{
            startActivity(Intent(this@MainActivity, ActivityCadastroUser::class.java))

        }
//        binding.signupButton.setOnClickListener {
//            val myIntent = Intent(this@MainActivity, DoadorMainActivity::class.java)
//            startActivity(myIntent)
//        }

        viewModel.navigate.observe(this, Observer {
            closeLoading()
            when (it) {
                UserType.COMMON -> startActivity(Intent(this@MainActivity, DoadorMainActivity::class.java))
                else -> startActivity(Intent(this@MainActivity, OngMainActivity::class.java))
            }
        })
    }

    fun startLoading(){
        val loadingDialog = LoadingDialog()
        loadingDialog.show(supportFragmentManager,"Loading")
    }

    fun closeLoading(){
        val transaction = supportFragmentManager.beginTransaction()
        val loadingDialog = supportFragmentManager.findFragmentByTag("Loading") as LoadingDialog
        loadingDialog.dismiss()
        transaction.remove(loadingDialog)
    }
}