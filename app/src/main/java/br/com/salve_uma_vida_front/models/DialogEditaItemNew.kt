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
        binding.cadastroCampanhaItemConfirma.setOnClickListener {
            if (validate()) {
                val itemCampanha = ItemCampanha()
                itemCampanha.titulo=binding.cadastroCampanhaItemNome.text.toString()
                itemCampanha.unidadeMedida = binding.cadastroCampanhaItemUnidadesMedida.selectedItem.toString()
                itemCampanha.quantidadeMaxima = binding.cadastroCampanhaItemQuantidade.text.toString().toInt()
                editaItemListener.passaItem(itemCampanha)
                dismiss()
            }
        }

    }

    private fun validate(): Boolean {
        if (TextUtils.isEmpty(binding.cadastroCampanhaItemNome.text)) {
            binding.cadastroCampanhaItemNome.setError("É necessário o nome do item para fazer o cadastro")
            return false
        }
        if (TextUtils.isEmpty(binding.cadastroCampanhaItemQuantidade.text)) {
            binding.cadastroCampanhaItemQuantidade.setError("É necessária a quantidade do item para fazer o cadastro")
            return false
        }
        return true
    }

    private fun configuraBotaoCancelar() {
        binding.cadastroCampanhaItemClose.setOnClickListener {
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