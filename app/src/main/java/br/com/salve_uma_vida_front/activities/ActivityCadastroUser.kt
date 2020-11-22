package br.com.salve_uma_vida_front.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.viewmodels.UserViewModel
import br.com.salve_uma_vida_front.AdressToLatLong
import br.com.salve_uma_vida_front.dp
import br.com.salve_uma_vida_front.models.Responses
import br.com.salve_uma_vida_front.models.UserType
import br.com.salve_uma_vida_front.databinding.ActivityCadastroUserBinding
import com.squareup.picasso.Picasso


class ActivityCadastroUser : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroUserBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroUserBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
//        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        configuraObservers()
        binding.radioGroupTipo.setOnCheckedChangeListener { group, selectedId ->

            val radio = binding.root.findViewById<RadioButton>(selectedId)
            if (radio == binding.UserOng) {
                binding.labelRua.visibility = View.VISIBLE
                binding.rua.visibility = View.VISIBLE
                binding.labelCidade.visibility = View.VISIBLE
                binding.cidade.visibility = View.VISIBLE
                binding.labelEstado.visibility = View.VISIBLE
                binding.estado.visibility = View.VISIBLE
                binding.labelNumero.visibility = View.VISIBLE
                binding.numero.visibility = View.VISIBLE
            } else {
                binding.labelRua.visibility = View.GONE
                binding.rua.visibility = View.GONE
                binding.rua.text.clear()
                binding.labelCidade.visibility = View.GONE
                binding.cidade.visibility = View.GONE
                binding.cidade.text.clear()
                binding.labelEstado.visibility = View.GONE
                binding.estado.visibility = View.GONE
                binding.estado.text.clear()
                binding.labelNumero.visibility = View.GONE
                binding.numero.visibility = View.GONE
                binding.numero.text.clear()
            }

        }
        binding.imagem.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                Picasso.get()
                    .load(binding.imagem.text.toString())
                    .resize(110.dp, 110.dp)
                    .centerCrop()
                    .placeholder(R.drawable.ic_dafault_photo)
                    .error(R.drawable.ic_baseline_report_problem_24)
                    .into(binding.userFoto)
            }

        }
        binding.criar.setOnClickListener {
            if (validade()) {
                val nome = binding.nome.text.toString()
                val detalhes = binding.detalhes.text.toString()
                val email = binding.email.text.toString()
                val senha = binding.senha.text.toString()
                val imagem = binding.imagem.text.toString()
                val tipo =
                    if (getRadioSelected() == binding.UserComum) UserType.COMMON.toString() else UserType.NGO.toString()
                if (getRadioSelected() == binding.UserOng) {
                    val endereco: String
                    endereco = getEnderecoFormatado()
                    val adressToLatLong =
                        AdressToLatLong(
                            endereco,
                            applicationContext
                        )
                    val latitude = adressToLatLong.get(0)
                    val longitude = adressToLatLong.get(1)
                    viewModel.signup(
                        nome,
                        email,
                        senha,
                        detalhes,
                        tipo,
                        imagem,
                        endereco,
                        latitude,
                        longitude
                    )
                } else {
                    viewModel.signup(nome, email, senha, detalhes, tipo, imagem)
                }
            }


        }
    }

    private fun configuraObservers() {
        viewModel.cadastro.observe(this, Observer {
            if (it==Responses.SUCESS) {
                startActivity(Intent(this@ActivityCadastroUser, MainActivity::class.java))
            }
        })
    }

    private fun getEnderecoFormatado(): String {
        return binding.rua.text.toString() + ", " + binding.cidade.text.toString() + " - " + binding.estado.text.toString() + ", " + binding.numero.text.toString()
    }

    private fun getRadioSelected(): RadioButton? {
        val selectedId = binding.radioGroupTipo.checkedRadioButtonId
        return binding.root.findViewById(selectedId)
    }

    private fun validade(): Boolean {
        if (TextUtils.isEmpty(binding.imagem.text)) {
            binding.imagem.setError("Imagem é necessária para fazer o cadastro")
            return false
        }
        if (TextUtils.isEmpty(binding.nome.text)) {
            binding.nome.setError("Nome é necessário para fazer o cadastro")
            return false
        }
        if (TextUtils.isEmpty(binding.detalhes.text)) {
            binding.detalhes.setError("Detalhes são necessários para fazer o cadastro")
            return false
        }
        if (TextUtils.isEmpty(binding.email.text)) {
            binding.email.setError("Email é necessário para fazer o cadastro")
            return false
        }
        if (TextUtils.isEmpty(binding.senha.text)) {
            binding.senha.setError("Senha é necessária para fazer o cadastro")
            return false
        }
        if (getRadioSelected() == binding.UserOng) {
            if (TextUtils.isEmpty(binding.rua.text)) {
                binding.rua.setError("Rua/Avenida é necessária para fazer o cadastro")
                return false
            }
            if (TextUtils.isEmpty(binding.cidade.text)) {
                binding.cidade.setError("Cidade é necessária para fazer o cadastro")
                return false
            }
            if (TextUtils.isEmpty(binding.estado.text)) {
                binding.estado.setError("Estado é necessário para fazer o cadastro")
                return false
            }
            if (TextUtils.isEmpty(binding.numero.text)) {
                binding.numero.setError("Numero é necessário para fazer o cadastro")
                return false
            }
        }
        return true
    }


}