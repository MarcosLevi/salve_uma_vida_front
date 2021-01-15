package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.databinding.FragmentPerfilOngEventosBinding
import br.com.salve_uma_vida_front.databinding.FragmentPerfilOngGaleriaBinding

class PerfilOngGaleriaFragment : Fragment() {
    lateinit var binding: FragmentPerfilOngGaleriaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerfilOngGaleriaBinding.inflate(inflater, container, false)
        return binding.root
    }
    companion object {
        @JvmStatic
        fun newInstance() = PerfilOngGaleriaFragment()
    }
}