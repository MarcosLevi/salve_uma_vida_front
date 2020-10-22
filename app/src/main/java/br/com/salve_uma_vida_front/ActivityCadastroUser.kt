package br.com.salve_uma_vida_front

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import br.com.salve_uma_vida_front.databinding.ActivityCadastroUserBinding
import br.com.salve_uma_vida_front.dto.FiltroPesquisaDto

class ActivityCadastroUser : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroUserBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
//        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.radioGroupTipo.setOnCheckedChangeListener { group, selectedId ->

            var radio = binding.root.findViewById<RadioButton>(selectedId)
            if (radio == binding.UserOng) {
                binding.labelEndereco.visibility = View.VISIBLE
                binding.endereco.visibility = View.VISIBLE
            } else {
                binding.labelEndereco.visibility = View.INVISIBLE
                binding.endereco.visibility = View.INVISIBLE
            }


        }

    }
}