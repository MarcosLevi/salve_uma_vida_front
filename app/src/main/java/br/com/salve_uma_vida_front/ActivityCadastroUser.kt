package br.com.salve_uma_vida_front

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import br.com.salve_uma_vida_front.both.dp
import br.com.salve_uma_vida_front.databinding.ActivityCadastroUserBinding
import br.com.salve_uma_vida_front.dto.FiltroPesquisaDto
import com.squareup.picasso.Picasso

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
                binding.labelEndereco.visibility = View.VISIBLE
                binding.endereco.visibility = View.VISIBLE
            } else {
                binding.labelEndereco.visibility = View.INVISIBLE
                binding.endereco.visibility = View.INVISIBLE
                binding.endereco.text.clear()
            }

        }
        binding.imagem.setOnFocusChangeListener(View.OnFocusChangeListener{view, hasFocus ->
            if (!hasFocus){
                Picasso.get()
                    .load(binding.imagem.text.toString())
                    .resize(110.dp, 110.dp)
                    .centerCrop()
                    .placeholder(R.drawable.ic_dafault_photo)
                    .error(R.drawable.ic_baseline_report_problem_24)
                    .into(binding.userFoto)
            }

        })

    }

    private fun getRadioSelected(): RadioButton? {
        val selectedId = binding.radioGroupTipo.checkedRadioButtonId
        return binding.root.findViewById(selectedId)
    }
}