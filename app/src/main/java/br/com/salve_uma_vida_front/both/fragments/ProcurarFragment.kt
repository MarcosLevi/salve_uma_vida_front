package br.com.salve_uma_vida_front.both.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.both.hideKeyboard
import br.com.salve_uma_vida_front.both.models.ItemCard
import br.com.salve_uma_vida_front.both.adapters.CardAdapter
import br.com.salve_uma_vida_front.both.models.CardPesquisa
import br.com.salve_uma_vida_front.databinding.FragmentBothProcurarBinding

class ProcurarFragment : Fragment(),View.OnClickListener {
    var navController: NavController? = null
    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<CardAdapter.CardViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var binding: FragmentBothProcurarBinding


    val itensPrimeiroCard: MutableList<ItemCard> = mutableListOf(
        ItemCard(
            "Ração",
            "Kg",
            100,
            30
        ),
        ItemCard(
            "Leite",
            "L",
            90,
            60
        ),
        ItemCard(
            "Coleira",
            "Unidades",
            500,
            450
        ),
        ItemCard(
            "Água",
            "L",
            600,
            30
        ),
        ItemCard(
            "Sabão",
            "L",
            200,
            20
        )
    )

    val itensSegundoCard: MutableList<ItemCard> = mutableListOf(
        ItemCard(
            "Dinheiros",
            "R$",
            10000,
            2000
        ),
        ItemCard(
            "Dólares",
            "R$",
            10000,
            20
        )
    )

    val itensTerceiroCard: MutableList<ItemCard> = mutableListOf(
        ItemCard(
            "Ração",
            "Kg",
            100,
            30
        ),
        ItemCard(
            "Leite",
            "L",
            90,
            60
        ),
        ItemCard(
            "Coleira",
            "Unidades",
            500,
            450
        ),
        ItemCard(
            "Água",
            "L",
            600,
            30
        ),
        ItemCard(
            "Sabão",
            "L",
            200,
            20
        )
    )

    val listaCards: MutableList<CardPesquisa> = mutableListOf()


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

        val campoDePesquisa = binding.activityCampanhaVisaoDoadorPesquisa
        val botaoPesquisar = binding.campanhasPesquisar
        botaoPesquisar.setOnClickListener{
            acaoPesquisarCampanhas(campoDePesquisa)
        }
    }

    private fun configuraRecyclerView() {
        mRecyclerView = binding.cardsCampanhas
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(requireContext())
        mAdapter = CardAdapter(
            listaCards,
            requireContext()
        )
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter
    }

    private fun acaoPesquisarCampanhas(campoDePesquisa: EditText) {
        requireContext().hideKeyboard(campoDePesquisa)
        Toast.makeText(requireContext(), campoDePesquisa.text, Toast.LENGTH_SHORT).show()
        mostraCards(campoDePesquisa.text)
    }

    private fun mostraCards(text: Editable) {
        //mostra os cards especificos usando o text
        listaCards.add(
            CardPesquisa(
                "Ajude o abrigo São José",
                "Anunciado em 26/06/2020",
                "Estamos precisando de ração o mais rápido possível! Por favor nos ajudem.",
                itensPrimeiroCard,
                "https://jornalzo.com.br/media/k2/items/cache/cb9c495b17bc28a44ffb50c55572ed63_XL.jpg?t=20141103_151946"
            )
        )
        listaCards.add(
            CardPesquisa(
                "Me ajuda aí o caralho, nunca te pedi nada",
                "Anunciado em 24/06/2020",
                "Quero comprar um playstation 5, me ajudem a fazer isso aí o porra",
                itensSegundoCard,
                "https://www.showmetech.com.br/wp-content/uploads//2020/08/143354-games-feature-sony-playstation-5-release-date-rumours-and-everything-you-need-to-know-about-ps5-image1-cvz3adase9-1024x683.jpg"
            )
        )
        listaCards.add(
            CardPesquisa(
                "Ajude o abrigo Seu Cuck Feliz",
                "Anunciado em 66/66/6666",
                "Se essa merda tá funcionando vai aparecer algo aqui.",
                itensTerceiroCard,
                "https://cinema10.com.br/upload/filmes/filmes_14167_MV5BYjdkZjQ3NTctY2E0Ni00Njc4LTlmZWItZDlkMmZhNTRiOGQxXkEyXkFqcGdeQXVyMjIwNTg2ODA@._V1_SY1000_CR0,0,666,1000_AL_.jpg"
            )
        )
        notificaMudancaAdapter()
    }

    private fun notificaMudancaAdapter() {
        mRecyclerView.adapter!!.notifyDataSetChanged()
    }

    //Botoes dentro da view que vão fazer algo na view
    override fun onClick(v: View?) {
        when (v!!.id) {
//            R.id.cadastroCampanhaAdicionaItem -> navController!!.navigate(R.id.action_itemCadastroFragment_to_cadastroFragment)
        }
    }
}