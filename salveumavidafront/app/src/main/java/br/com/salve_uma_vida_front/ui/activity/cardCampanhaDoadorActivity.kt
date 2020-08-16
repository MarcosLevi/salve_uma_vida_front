package br.com.salve_uma_vida_front.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.adapters.ItemAdapter
import br.com.salve_uma_vida_front.models.ItemCardDoador

//open class cardCampanhaDoadorActivity() {
class cardCampanhaDoadorActivity() : AppCompatActivity() {
    var quantidadeDeItens = 0
    lateinit var mRecyclerView: RecyclerView

    lateinit var mAdapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.card_campanha_visao_ong)
        setContentView(R.layout.card_campanha_visao_doador)
        var teste: List<ItemCardDoador> = mutableListOf(
            ItemCardDoador("Ração", "Kg", 100, 30),
            ItemCardDoador("Leite", "L", 90, 60),
            ItemCardDoador("Coleira", "Unidades", 500, 450),
            ItemCardDoador("Água", "L", 600, 30),
            ItemCardDoador("Sabão", "L", 200, 20)
        )
        quantidadeDeItens=teste.size
        atualizaCampoQuantidadeDeItens()
//        var listaItens = findViewById<LinearLayout>(R.id.cardCampanhaDoadorItens)
//        adicionaItensNaListaView(listaItens)

        mRecyclerView = findViewById(R.id.itensCampanha)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)
        mAdapter = ItemAdapter(teste)

        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter
    }

    fun atualizaCampoQuantidadeDeItens() {
        var textViewQuantidadeDeItens = findViewById<TextView>(R.id.cardQuantidadeItens)
        if (this.quantidadeDeItens == 1) {
            textViewQuantidadeDeItens.text = "1 item"
        } else if (this.quantidadeDeItens > 1) {
            textViewQuantidadeDeItens.text = quantidadeDeItens.toString() + " itens"
        }
    }
}