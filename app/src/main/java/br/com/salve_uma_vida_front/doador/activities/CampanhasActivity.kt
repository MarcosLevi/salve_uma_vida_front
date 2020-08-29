package br.com.salve_uma_vida_front.doador.activities

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.both.hideKeyboard
import br.com.salve_uma_vida_front.both.adapters.CardAdapter
import br.com.salve_uma_vida_front.both.models.CardPesquisa
import br.com.salve_uma_vida_front.both.models.ItemCard

//open class cardCampanhaDoadorActivity() {
class CampanhasActivity() : AppCompatActivity() {
    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<CardAdapter.CardViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campanha_visao_doador)
        val campoDePesquisa = findViewById<EditText>(R.id.activity_campanha_visao_doador_pesquisa)
        val botaoPesquisar = findViewById<ImageButton>(R.id.campanhasPesquisar)
        botaoPesquisar.setOnClickListener{
            acaoPesquisarCampanhas(campoDePesquisa)
        }
    }

    private fun mostraCards() {
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

        val listaCards: List<CardPesquisa> = mutableListOf(
            CardPesquisa(
                "Ajude o abrigo São José",
                "Anunciado em 26/06/2020",
                "Estamos precisando de ração o mais rápido possível! Por favor nos ajudem.",
                itensPrimeiroCard,
                "https://jornalzo.com.br/media/k2/items/cache/cb9c495b17bc28a44ffb50c55572ed63_XL.jpg?t=20141103_151946"
            ),
            CardPesquisa(
                "Me ajuda aí o caralho, nunca te pedi nada",
                "Anunciado em 24/06/2020",
                "Quero comprar um playstation 5, me ajudem a fazer isso aí o porra",
                itensSegundoCard,
                "https://www.showmetech.com.br/wp-content/uploads//2020/08/143354-games-feature-sony-playstation-5-release-date-rumours-and-everything-you-need-to-know-about-ps5-image1-cvz3adase9-1024x683.jpg"
            ),
            CardPesquisa(
                "Ajude o abrigo Seu Cuck Feliz",
                "Anunciado em 66/66/6666",
                "Se essa merda tá funcionando vai aparecer algo aqui.",
                itensTerceiroCard,
                "https://cinema10.com.br/upload/filmes/filmes_14167_MV5BYjdkZjQ3NTctY2E0Ni00Njc4LTlmZWItZDlkMmZhNTRiOGQxXkEyXkFqcGdeQXVyMjIwNTg2ODA@._V1_SY1000_CR0,0,666,1000_AL_.jpg"
            )
        )

        mRecyclerView = findViewById(R.id.cardsCampanhas)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)
        mAdapter = CardAdapter(
            listaCards,
            this
        )
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter
    }

    private fun acaoPesquisarCampanhas(campoDePesquisa: EditText) {
        this.hideKeyboard(campoDePesquisa)
        Toast.makeText(this, campoDePesquisa.text, Toast.LENGTH_SHORT).show()
        mostraCards()
    }
}