package br.com.salve_uma_vida_front.models

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import br.com.salve_uma_vida_front.R

class itemCardDoador(
    var titulo: String,
    val unidadeMedida: String,
    var quantidadeMaxima: Int,
    var quantidadeAtual: Int,
    val contexto: Context

) : ConstraintLayout(contexto) {

    init {
        geraItem()
    }


    fun geraItem() {
        val idTitulo = 1
        val idProgressBar = 2
        val idProgresso = 3

        val textViewTitulo = geraTextViewTitulo(idTitulo)

        val textViewProgresso = geraTextViewProgresso(idProgresso)

        val progressBar = geraProgressBar(idProgressBar)

        this.layoutParams = layoutParamsConstraintLayout()

        var views = listOf(textViewTitulo,progressBar, textViewProgresso)

        adicionaViewsNoLayout(views)

        configuraConstrainsDasViews(idProgressBar, idTitulo, idProgresso)
    }

    private fun configuraConstrainsDasViews(
        idProgressBar: Int,
        idTitulo: Int,
        idProgresso: Int
    ) {
        val constraintSet = ConstraintSet()
        //Copy all the previous constraints present in the constraint layout.
        constraintSet.clone(this)

        //Titulo
        constraintSet.connect(
            idTitulo,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        constraintSet.connect(
            idTitulo,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )

        //idProgresso
        constraintSet.connect(
            idProgresso,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )
        constraintSet.connect(
            idProgresso,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )

        //ProgressBar
        constraintSet.connect(
            idProgressBar,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        constraintSet.connect(
            idProgressBar,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )
        constraintSet.connect(
            idProgressBar,
            ConstraintSet.TOP,
            idTitulo,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            idProgressBar,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )

        constraintSet.applyTo(this)
    }

    private fun adicionaViewsNoLayout(
        views: List<View>
    ) {
        views.forEach{
            this.addView(it)
        }
    }

    private fun geraProgressBar(idProgressBar: Int): ProgressBar {
        val progressBar = ProgressBar(contexto, null, android.R.attr.progressBarStyleHorizontal)
        progressBar.max = quantidadeMaxima
        progressBar.progress = quantidadeAtual
        progressBar.layoutParams = layoutParamsProgressBar()
        progressBar.id = idProgressBar
        return progressBar
    }

    private fun geraTextViewMaxValorProgressBar(idMaxValor: Int): TextView {
        val textViewMaxValorProgressBar = TextView(contexto)
        textViewMaxValorProgressBar.text = quantidadeMaxima.toString()
        textViewMaxValorProgressBar.layoutParams = LayoutParams(
            50.toDp(contexto),
            LayoutParams.WRAP_CONTENT
        )
        textViewMaxValorProgressBar.id = idMaxValor
        textViewMaxValorProgressBar.textAlignment = View.TEXT_ALIGNMENT_VIEW_END
        textViewMaxValorProgressBar.setTextColor(ContextCompat.getColor(contexto, R.color.black))
        return textViewMaxValorProgressBar
    }

    private fun geraTextViewProgresso(idProgresso: Int): TextView {
        val textViewProgresso = TextView(contexto)
        textViewProgresso.text = "Arrecadado $quantidadeAtual / $quantidadeMaxima $unidadeMedida"
        textViewProgresso.layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        textViewProgresso.id = idProgresso
        textViewProgresso.setTextColor(ContextCompat.getColor(contexto, R.color.black))
        return textViewProgresso
    }

    private fun geraTextViewTitulo(idTitulo: Int): TextView {
        val textViewTitulo = TextView(contexto)
        textViewTitulo.text = titulo
        textViewTitulo.layoutParams = LayoutParams(
            100.toDp(contexto),
            LayoutParams.WRAP_CONTENT
        )
        textViewTitulo.id = idTitulo
        textViewTitulo.setTextColor(ContextCompat.getColor(contexto, R.color.black))
        return textViewTitulo
    }

    private fun geraTextViewValorAtualProgressBar(idAtual: Int): TextView {
        val textViewValorAtualProgressBar = TextView(contexto)
        textViewValorAtualProgressBar.text = quantidadeAtual.toString()
        textViewValorAtualProgressBar.layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        textViewValorAtualProgressBar.id = idAtual
        textViewValorAtualProgressBar.setTextColor(ContextCompat.getColor(contexto, R.color.black))
        return textViewValorAtualProgressBar
    }

    private fun layoutParamsConstraintLayout(): LayoutParams {
        val configConstraintLayout = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        configConstraintLayout.bottomMargin = 10.toDp(contexto)
        return configConstraintLayout
    }

    private fun layoutParamsProgressBar(): LayoutParams {
        val configProgressBar = LayoutParams(
            LayoutParams.MATCH_CONSTRAINT,
            LayoutParams.WRAP_CONTENT
        )
        return configProgressBar
    }

    fun Int.toDp(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()
}