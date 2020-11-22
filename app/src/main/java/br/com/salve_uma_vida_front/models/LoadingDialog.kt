package br.com.salve_uma_vida_front.models

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.salve_uma_vida_front.databinding.FragmentLoadingDialogBinding

class LoadingDialog : DialogFragment() {
    lateinit var binding: FragmentLoadingDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadingDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

}