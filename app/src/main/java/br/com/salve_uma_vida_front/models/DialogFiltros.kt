package br.com.salve_uma_vida_front.models

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.SeekBar
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.salve_uma_vida_front.viewmodels.CampanhasViewModel
import br.com.salve_uma_vida_front.databinding.FragmentProcurarDialogBinding
import br.com.salve_uma_vida_front.dto.FiltroPesquisaDto

class DialogFiltros(private val dialogFiltroListener: DialogFiltroListener) : DialogFragment() {

    lateinit var binding: FragmentProcurarDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProcurarDialogBinding.inflate(inflater, container, false)
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
                binding.textViewFiltroKilometro.text = (progress + 1).toString()
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
            val radio = binding.root.findViewById<RadioButton>(selectedId)
            val tipoFiltro = radio.text.toString()
            val searchType: SearchType
            searchType = if (tipoFiltro == "Campanhas")
                SearchType.CAMPANHAS
            else
                SearchType.EVENTOS
            val distancia = binding.seekBarDistancia.progress
            dialogFiltroListener.passaFiltro(FiltroPesquisaDto(searchType, distancia + 1))
            dismiss()
        }
    }

    interface DialogFiltroListener{
        fun passaFiltro(filtro: FiltroPesquisaDto)
    }

    override fun onStart() {
        dialog!!.window!!
            .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        super.onStart()
    }
}