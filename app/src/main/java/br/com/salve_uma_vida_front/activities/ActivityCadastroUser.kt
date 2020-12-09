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
import br.com.salve_uma_vida_front.models.DialogUrl
import com.squareup.picasso.Picasso


class ActivityCadastroUser : AppCompatActivity(), DialogUrl.DialogUrlListener {

    private lateinit var binding: ActivityCadastroUserBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroUserBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
//        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        configuraObservers()
        configuraRadioGroup()

        configuraListenerUserFoto()

        configuraBotaoCriar()
    }

    private fun configuraBotaoCriar() {
        binding.criar.setOnClickListener {
            if (validate()) {
                val nome = binding.nome.text.toString()
                val detalhes = binding.detalhes.text.toString()
                val email = binding.email.text.toString()
                val senha = binding.senha.text.toString()
                val imagem = binding.urlImagem.toString()
                val tipo = if (getRadioSelected() == binding.UserComum) UserType.COMMON.toString() else UserType.NGO.toString()
                if (getRadioSelected() == binding.UserOng) {
                    val endereco: String = getEnderecoFormatado()
                    val (latitude, longitude) = retornaLatitudeLongitude(endereco)
                    signup(
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
                    signup(nome, email, senha, detalhes, tipo, imagem)
                }
            }


        }
    }

    private fun signup(
        name: String,
        email: String,
        password: String,
        detail: String,
        type: String,
        image: String,
        address: String = "",
        addressLatitude: Float = 0.0F,
        addressLongitude: Float = 0.0F
    ) {
        viewModel.signup(
            name,
            email,
            password,
            detail,
            type,
            image,
            address,
            addressLatitude,
            addressLongitude
        )
    }

    private fun retornaLatitudeLongitude(endereco: String): Pair<Float, Float> {
        val adressToLatLong =
            AdressToLatLong(
                endereco,
                applicationContext
            )
        val latitude = adressToLatLong.get(0)
        val longitude = adressToLatLong.get(1)
        return Pair(latitude, longitude)
    }

    private fun configuraListenerUserFoto() {
        binding.userFoto.setOnClickListener {
            val urlDialog = DialogUrl(this)
            urlDialog.show(supportFragmentManager, "Url Dialog")
        }
    }

    private fun configuraRadioGroup() {
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
    }

    private fun configuraObservers() {
        viewModel.cadastro.observe(this, Observer {
            if (it == Responses.SUCESS) {
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

    private fun validate(): Boolean {
//        if (TextUtils.isEmpty(binding.urlImagem.text)) {
//            binding.urlImagem.setError("Imagem é necessária para fazer o cadastro")
//            return false
//        }
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

    override fun passaUrl(url: String) {
        Picasso.get()
            .load(url)
            .resize(110.dp, 110.dp)
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(binding.userFoto)
        binding.urlImagem.text = url
    }


}