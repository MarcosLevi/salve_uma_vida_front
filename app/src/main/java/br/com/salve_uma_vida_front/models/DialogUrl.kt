package br.com.salve_uma_vida_front.models

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.salve_uma_vida_front.databinding.FragmentDialogUrlImagemBinding


class DialogUrl : DialogFragment() {
    lateinit var binding: FragmentDialogUrlImagemBinding
    private lateinit var urlListener: DialogUrlListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            urlListener = context as DialogUrlListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "Tem que implementar o listener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogUrlImagemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fragmentDialogUrlImagemClose.setOnClickListener {
            dismiss()
        }
        binding.fragmentDialogUrlImagemConfirmar.setOnClickListener {
            urlListener.passaUrl(binding.fragmentDialogUrlImagemEditTextUrl.text.toString())
            dismiss()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        dialog!!.getWindow()!!
            .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        super.onStart()
    }

    interface DialogUrlListener {
        fun passaUrl(url: String)
    }
}