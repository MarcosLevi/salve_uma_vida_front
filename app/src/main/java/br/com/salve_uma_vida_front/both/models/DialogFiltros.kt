package br.com.salve_uma_vida_front.both.models

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import br.com.salve_uma_vida_front.both.viewmodels.ProcurarFragmentViewModel
import br.com.salve_uma_vida_front.databinding.FragmentBothProcurarDialogBinding
import br.com.salve_uma_vida_front.dto.FiltroPesquisaDto

class DialogFiltros(procurarFragmentViewModel: ProcurarFragmentViewModel) : DialogFragment() {

    lateinit var binding: FragmentBothProcurarDialogBinding
    private var viewModel = procurarFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBothProcurarDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configuraBotaoCancelar()
        configuraBotaoFiltrar()
        configuraSeekBar()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun configuraBotaoCancelar() {
        binding.buttonCancelar.setOnClickListener {
            dismiss()
        }
    }

    private fun configuraSeekBar() {
        binding.seekBarDistancia.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.textViewFiltroKilometro.setText((progress + 1).toString())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }

    private fun configuraBotaoFiltrar() {
        binding.buttonFiltrar.setOnClickListener {
            val selectedId = binding.radioGroupTipo.checkedRadioButtonId
            var radio = binding.root.findViewById<RadioButton>(selectedId)
            val tipoFiltro = radio.text.toString()
            val distancia = binding.seekBarDistancia.progress
            viewModel.filtrar(FiltroPesquisaDto(tipoFiltro, distancia + 1))
            dismiss()
        }
    }

    override fun onStart() {
        dialog!!.getWindow()!!
            .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        super.onStart()
    }
}