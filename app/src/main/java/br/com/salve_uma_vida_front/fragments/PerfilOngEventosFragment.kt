package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.databinding.FragmentPerfilOngCampanhasBinding
import br.com.salve_uma_vida_front.databinding.FragmentPerfilOngEventosBinding


class PerfilOngEventosFragment : Fragment() {
    lateinit var binding: FragmentPerfilOngEventosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerfilOngEventosBinding.inflate(inflater, container, false)
        return binding.root
    }
    companion object {
        @JvmStatic
        fun newInstance() = PerfilOngEventosFragment()
    }
}