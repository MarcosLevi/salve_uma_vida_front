package br.com.salve_uma_vida_front

import android.location.Geocoder
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import br.com.salve_uma_vida_front.both.AdressToLatLong
import br.com.salve_uma_vida_front.both.dp
import br.com.salve_uma_vida_front.databinding.ActivityCadastroUserBinding
import br.com.salve_uma_vida_front.dto.FiltroPesquisaDto
import com.squareup.picasso.Picasso
import java.util.*

class ActivityCadastroUser : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroUserBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
//        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
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
                if (getRadioSelected() == binding.UserOng) {
                    var adressToLatLong =
                        AdressToLatLong(getEnderecoFormatado(), applicationContext)
                    var latitude = adressToLatLong.get(0)
                    var longitude = adressToLatLong.get(1)
                }
            }


        }
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