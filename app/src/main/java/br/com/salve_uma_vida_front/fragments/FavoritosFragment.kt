package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.databinding.FragmentBothFavoritosBinding
import br.com.salve_uma_vida_front.dto.OngFavoritaDto
import br.com.salve_uma_vida_front.toolbarVazia
import br.com.salve_uma_vida_front.viewholders.CardOngFavoritaViewHolder

class FavoritosFragment : Fragment() {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var binding: FragmentBothFavoritosBinding
    lateinit var ongFavoritaAdapter: RecyclerView.Adapter<CardOngFavoritaViewHolder>
    private val listaOngsFavoritas: MutableList<OngFavoritaDto> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBothFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraRecyclerView()
        configuraToolbar()
    }

    private fun configuraToolbar() {
        val toolbar = toolbarVazia(activity)
//        toolbar?.inflateMenu(R.menu.fragment_both_procurar_menu)
    }

    private fun configuraRecyclerView() {
        mRecyclerView = binding.cardsFavoritos
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(requireContext())
//        ongFavoritaAdapter =
//            CardOngFavoritaAdapter(
//                listaOngsFavoritas,
//                requireContext()
//            )
        mRecyclerView.layoutManager = mLayoutManager
//        mRecyclerView.adapter = ongFavoritaAdapter
    }
}