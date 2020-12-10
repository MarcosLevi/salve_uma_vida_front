package br.com.salve_uma_vida_front.models

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.databinding.FragmentCadastroCampanhaItemDialogBinding
import br.com.salve_uma_vida_front.dto.CampanhaItemDto


class DialogEditaItemNew(
    private val editaItemListener: DialogEditaItemListener,
    private val itemCampanha: CampanhaItemDto = CampanhaItemDto()
) : DialogFragment() {

    lateinit var binding: FragmentCadastroCampanhaItemDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCadastroCampanhaItemDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configuraBotaoCancelar()
        configuraBotaoConfirmar()
        setValoresIniciais()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setValoresIniciais() {
        binding.cadastroCampanhaItemNome.setText(itemCampanha.descricao)
        binding.cadastroCampanhaItemQuantidade.setText(itemCampanha.maximo.toString())
        val unidadesMedida = resources.getStringArray(R.array.unidades_medida)
        if (itemCampanha.unidade != "") {
            for (position in unidadesMedida.indices) {
                if (itemCampanha.unidade == unidadesMedida[position]) {
                    binding.cadastroCampanhaItemUnidadesMedida.setSelection(position)
                }
            }
        }
    }

    private fun configuraBotaoConfirmar() {
        binding.cadastroCampanhaItemConfirma.setOnClickListener {
            if (validate()) {
                itemCampanha.descricao = binding.cadastroCampanhaItemNome.text.toString()
                itemCampanha.unidade =
                    binding.cadastroCampanhaItemUnidadesMedida.selectedItem.toString()
                itemCampanha.maximo =
                    binding.cadastroCampanhaItemQuantidade.text.toString().toFloat()
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
            editaItemListener.onClose()
            dismiss()
        }
    }

    interface DialogEditaItemListener {
        fun passaItem(item: CampanhaItemDto)
        fun onClose()
    }


    override fun onStart() {
        dialog!!.getWindow()!!
            .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        super.onStart()
    }
}