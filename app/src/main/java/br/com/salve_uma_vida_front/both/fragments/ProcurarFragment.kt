package br.com.salve_uma_vida_front.both.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.both.hideKeyboard
import br.com.salve_uma_vida_front.both.adapters.CardAdapter
import br.com.salve_uma_vida_front.both.models.CardPesquisa
import br.com.salve_uma_vida_front.databinding.FragmentBothProcurarBinding
import br.com.salve_uma_vida_front.repository.getListaTodosOsCards

class ProcurarFragment : Fragment(), View.OnClickListener {
    var navController: NavController? = null
    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<CardAdapter.CardViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var binding: FragmentBothProcurarBinding

    var listaComFiltro: MutableList<CardPesquisa> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBothProcurarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        configuraRecyclerView()
        val campoDePesquisa = binding.campanhasSearchView
        campoDePesquisa.queryHint = "Procurar por Nome, Descrição ou Itens"
        campoDePesquisa.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                (mAdapter as CardAdapter).filter.filter(newText)
                return false
            }

        })

    }

    private fun configuraRecyclerView() {
        mRecyclerView = binding.cardsCampanhas
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(requireContext())
        mAdapter = CardAdapter(
            getListaTodosOsCards(),
            requireContext()
        )
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter
    }

    //Botoes dentro da view que vão fazer algo na view
    override fun onClick(v: View?) {
        when (v!!.id) {
//            R.id.cadastroCampanhaAdicionaItem -> navController!!.navigate(R.id.action_itemCadastroFragment_to_cadastroFragment)
        }
    }
}