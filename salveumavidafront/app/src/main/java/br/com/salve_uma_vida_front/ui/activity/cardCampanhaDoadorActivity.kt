package br.com.salve_uma_vida_front.ui.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import br.com.salve_uma_vida_front.R

class cardCampanhaDoadorActivity : AppCompatActivity() {
    var quantidadeDeItens = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_card_campanha_visao_ong)
        setContentView(R.layout.activity_card_campanha_visao_doador)
        var listaItens = findViewById<View>(R.id.cardCampanhaDoadorItens) as LinearLayout
        listaItens.addView(geraItem("Ração (Kg)", 90, 50))
        listaItens.addView(geraItem("Água (L)", 200, 30))
        listaItens.addView(geraItem("Xampu (L)", 110, 10))
        listaItens.addView(geraItem("Coleira (Quantidade)", 500, 450))
    }

    fun Int.toDp(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()

    fun wrapContentNos2Campos(): ConstraintLayout.LayoutParams {
        return ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
    }

    fun atualizaCampoQuantidadeDeItens() {
        var textViewQuantidadeDeItens = findViewById<View>(R.id.cardQuantidadeItens) as TextView
        if (this.quantidadeDeItens == 1) {
            textViewQuantidadeDeItens.text = "1 item"
        }else if (this.quantidadeDeItens > 1){
            textViewQuantidadeDeItens.text = quantidadeDeItens.toString() + " itens"
        }
    }

    fun geraItem(titulo: String, maximo: Int, atual: Int): ConstraintLayout {
        val idTitulo = 1
        val idProgressBar = 2
        val idMaxValor = 3
        val idAtual = 4
        val idItem = 5

//        val configTextViews = ConstraintLayout.LayoutParams(
//            ConstraintLayout.LayoutParams.WRAP_CONTENT,
//            ConstraintLayout.LayoutParams.WRAP_CONTENT
//        )
        val configProgressBar = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        configProgressBar.marginStart = 20.toDp(this)
        configProgressBar.marginEnd = 20.toDp(this)
        val configConstraintLayout = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        configConstraintLayout.bottomMargin = 10.toDp(this)

        val textViewValorAtualProgressBar = TextView(this)
        textViewValorAtualProgressBar.text = atual.toString()
        textViewValorAtualProgressBar.layoutParams = wrapContentNos2Campos();
        textViewValorAtualProgressBar.id = idAtual

        val textViewTitulo = TextView(this)
        textViewTitulo.text = titulo
        textViewTitulo.layoutParams = wrapContentNos2Campos()
        textViewTitulo.id = idTitulo


        val textViewMaxValorProgressBar = TextView(this)
        textViewMaxValorProgressBar.text = maximo.toString()
        textViewMaxValorProgressBar.layoutParams = wrapContentNos2Campos()
        textViewMaxValorProgressBar.id = idMaxValor


        val progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal)
        progressBar.max = maximo
        progressBar.progress = atual
        progressBar.layoutParams = configProgressBar
        progressBar.id = idProgressBar

        val item = ConstraintLayout(this)
        item.layoutParams = configConstraintLayout
        item.id = idItem

        item.addView(textViewValorAtualProgressBar)
        item.addView(textViewTitulo)
        item.addView(progressBar)
        item.addView(textViewMaxValorProgressBar)

        val constraintSet = ConstraintSet()
        //Copy all the previous constraints present in the constraint layout.
        constraintSet.clone(item)
        //Valor atual
        constraintSet.connect(
            idAtual,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,
            0
        )
        constraintSet.connect(idAtual, ConstraintSet.START, idProgressBar, ConstraintSet.START, 0)
        constraintSet.connect(idAtual, ConstraintSet.END, idProgressBar, ConstraintSet.END, 0)
        //Titulo
        constraintSet.connect(
            idTitulo,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            0
        )
        constraintSet.connect(idTitulo, ConstraintSet.TOP, idAtual, ConstraintSet.BOTTOM, 0)
        constraintSet.connect(
            idTitulo,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            0
        )
//        //MaxValor
        constraintSet.connect(
            idMaxValor,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END,
            0
        )
        constraintSet.connect(idMaxValor, ConstraintSet.TOP, idAtual, ConstraintSet.BOTTOM, 0)
        constraintSet.connect(
            idMaxValor,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            0
        )
//        //ProgressBar
        constraintSet.connect(idProgressBar, ConstraintSet.START, idTitulo, ConstraintSet.END)
        constraintSet.connect(idProgressBar, ConstraintSet.END, idMaxValor, ConstraintSet.START)
        constraintSet.connect(idProgressBar, ConstraintSet.TOP, idAtual, ConstraintSet.BOTTOM, 0)
        constraintSet.connect(
            idProgressBar,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            0
        )

        constraintSet.applyTo(item)

        quantidadeDeItens++
        atualizaCampoQuantidadeDeItens();

        return item

    }
}