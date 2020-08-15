package br.com.salve_uma_vida_front.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.models.itemCardDoador

//open class cardCampanhaDoadorActivity() {
class cardCampanhaDoadorActivity() : AppCompatActivity() {
    var quantidadeDeItens = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.card_campanha_visao_ong)
        setContentView(R.layout.card_campanha_visao_doador)
        var listaItens = findViewById<View>(R.id.cardCampanhaDoadorItens) as LinearLayout
        adicionaItensNaListaView(listaItens)
    }

    private fun adicionaItensNaListaView(listaItens: LinearLayout) {
        listaItens.addView(
            itemCardDoador(
                "Ração",
                "Kg",
                90,
                50,
                this
            )
        )
        quantidadeDeItens++
        listaItens.addView(
            itemCardDoador(
                "Coleira",
                "Unidades",
                100,
                25,
                this
            )
        )
        quantidadeDeItens++
        listaItens.addView(
            itemCardDoador(
                "Água",
                "L",
                250,
                200,
                this
            )
        )
        quantidadeDeItens++
        listaItens.addView(
            itemCardDoador(
                "Sabão",
                "L",
                100,
                10,
                this
            )
        )
        quantidadeDeItens++
        atualizaCampoQuantidadeDeItens()
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