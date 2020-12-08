package br.com.salve_uma_vida_front.models

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.salve_uma_vida_front.databinding.FragmentCadastroCampanhaItemBinding
import br.com.salve_uma_vida_front.viewmodels.CampanhasEEventosViewModel

class DialogEditaItemNew(private val editaItemListener: DialogEditaItemListener) : DialogFragment() {

    lateinit var binding: FragmentCadastroCampanhaItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCadastroCampanhaItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configuraBotaoCancelar()
        configuraBotaoConfirmar()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun configuraBotaoConfirmar() {
        binding.cadastroCampanhaSubmitNovoItem.setOnClickListener {
            if (validate()) {
                val itemCampanha = ItemCampanha()
                itemCampanha.titulo=binding.cadastroCampanhaEditTextNomeItem.text.toString()
                itemCampanha.unidadeMedida = binding.cadastroCampanhaSpinnerUnidadesMedida.selectedItem.toString()
                itemCampanha.quantidadeMaxima = binding.cadastroCampanhaEditTextQuantidadeItem.text.toString().toInt()
                editaItemListener.passaItem(itemCampanha)
                dismiss()
            }
        }

    }

    private fun validate(): Boolean {
        if (TextUtils.isEmpty(binding.cadastroCampanhaEditTextNomeItem.text)) {
            binding.cadastroCampanhaEditTextNomeItem.setError("É necessário o nome do item para fazer o cadastro")
            return false
        }
        if (TextUtils.isEmpty(binding.cadastroCampanhaEditTextQuantidadeItem.text)) {
            binding.cadastroCampanhaEditTextQuantidadeItem.setError("É necessária a quantidade do item para fazer o cadastro")
            return false
        }
        return true
    }

    private fun configuraBotaoCancelar() {
        binding.cadastroCampanhaClose.setOnClickListener {
            dismiss()
        }
    }

    interface DialogEditaItemListener {
        fun passaItem(item: ItemCampanha)
    }


    override fun onStart() {
        dialog!!.getWindow()!!
            .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        super.onStart()
    }
}