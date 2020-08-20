package br.com.salve_uma_vida_front.doador.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.doador.adapters.CardAdapter
import br.com.salve_uma_vida_front.doador.adapters.ItemAdapter
import br.com.salve_uma_vida_front.doador.models.CardDoador
import br.com.salve_uma_vida_front.doador.models.ItemCardDoador

//open class cardCampanhaDoadorActivity() {
class campanhasActivity() : AppCompatActivity() {
    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<CardAdapter.CardViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_campanha_visao_doador)
        var itensPrimeiroCard: List<ItemCardDoador> = mutableListOf(
            ItemCardDoador(
                "Ração",
                "Kg",
                100,
                30
            ),
            ItemCardDoador(
                "Leite",
                "L",
                90,
                60
            ),
            ItemCardDoador(
                "Coleira",
                "Unidades",
                500,
                450
            ),
            ItemCardDoador(
                "Água",
                "L",
                600,
                30
            ),
            ItemCardDoador(
                "Sabão",
                "L",
                200,
                20
            )
        )
        var itensSegundoCard: List<ItemCardDoador> = mutableListOf(
            ItemCardDoador(
                "Dinheiros",
                "R$",
                10000,
                2000
            )
        )

        var itensTerceiroCard: List<ItemCardDoador> = mutableListOf(
            ItemCardDoador(
                "Ração",
                "Kg",
                100,
                30
            ),
            ItemCardDoador(
                "Leite",
                "L",
                90,
                60
            ),
            ItemCardDoador(
                "Coleira",
                "Unidades",
                500,
                450
            ),
            ItemCardDoador(
                "Água",
                "L",
                600,
                30
            ),
            ItemCardDoador(
                "Sabão",
                "L",
                200,
                20
            )
        )

        var listaCards: List<CardDoador> = mutableListOf(
            CardDoador(
                "Ajude o abrigo São José",
                "Anunciado em 26/06/2020",
                "Estamos precisando de ração o mais rápido possível! Por favor nos ajudem.",
                itensPrimeiroCard,
                "https://jornalzo.com.br/media/k2/items/cache/cb9c495b17bc28a44ffb50c55572ed63_XL.jpg?t=20141103_151946"
            ),
            CardDoador(
                "Me ajuda aí o caralho, nunca te pedi nada",
                "Anunciado em 24/06/2020",
                "Quero comprar um playstation 5, me ajudem a fazer isso aí o porra",
                itensSegundoCard,
                "https://www.showmetech.com.br/wp-content/uploads//2020/08/143354-games-feature-sony-playstation-5-release-date-rumours-and-everything-you-need-to-know-about-ps5-image1-cvz3adase9-1024x683.jpg"
            ),
            CardDoador(
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
}