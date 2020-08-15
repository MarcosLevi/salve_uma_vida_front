package br.com.salve_uma_vida_front.ui.activity

import android.content.Context
import android.graphics.Typeface
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.models.itemCardDoador

//open class cardCampanhaDoadorActivity() {
class cardCampanhaDoador(
    val contexto: Context,
    val listaItens: List<itemCardDoador>,
    val titulo: String,
    val timestamp: String,
    val imagem: String,
    val descricao: String
) : ConstraintLayout(contexto) {
    var quantidadeDeItens = listaItens.size

    init {

    }

    fun geraCard() {
        val idButtonFavoritar = 1
        val idTitulo = 2
        val idTimeStamp = 3
        val idDescricao = 4
        val idQuantidadeItens = 5
        val idImagemCampanha = 6
        val idItens = 6

        this.layoutParams = layoutParamsConstraintLayout()
        var imageButtonFavoritar = geraImageButtonFavoritar(idButtonFavoritar)
        var textViewTitulo = geraTextViewTitulo(idTitulo)
        var textViewTimeStamp = geraTextViewTimeStamp(idTimeStamp)
        var textViewDescricao = geraTextViewDescricao(idDescricao)
        var textViewQuantidadeItens = geraTextViewQuantidadeItens(idQuantidadeItens)
        var imageViewImagemCampanha = geraImageViewImagemCampanha(idImagemCampanha)
        var linearLayoutItens = geraLinearLayoutItens(idItens)
    }

    private fun geraLinearLayoutItens(idItens: Int): LinearLayout {
        val linearLayoutItens = LinearLayout(contexto)
        linearLayoutItens.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        linearLayoutItens.orientation = LinearLayout.VERTICAL
        linearLayoutItens.id = idItens
        return linearLayoutItens
    }

    private fun geraImageViewImagemCampanha(idImagemCampanha: Int): ImageView {
        val imageViewImagemCampanha = ImageView(contexto)
        imageViewImagemCampanha.layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        imageViewImagemCampanha.id = idImagemCampanha
        //Tem que mudar pra ser a imagem que receber
        imageViewImagemCampanha.setImageDrawable(ContextCompat.getDrawable(contexto, R.drawable.ic_launcher_foreground))
        return imageViewImagemCampanha
    }

    private fun geraTextViewQuantidadeItens(idQuantidadeItens: Int): TextView {
        val textViewDescricao = TextView(contexto)
        textViewDescricao.layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        textViewDescricao.text = defineTextQuantidadeDeItens()
        textViewDescricao.id = idQuantidadeItens
        textViewDescricao.setTextColor(ContextCompat.getColor(contexto, R.color.ciano))
        return textViewDescricao
    }

    private fun geraTextViewDescricao(idDescricao: Int): TextView {
        val textViewDescricao = TextView(contexto)
        textViewDescricao.layoutParams = LayoutParams(
            LayoutParams.MATCH_CONSTRAINT,
            LayoutParams.WRAP_CONTENT
        )
        textViewDescricao.text = descricao
        textViewDescricao.id = idDescricao
        textViewDescricao.setTextColor(ContextCompat.getColor(contexto, R.color.black))
        return textViewDescricao
    }

    private fun geraTextViewTimeStamp(idTimeStamp: Int): TextView {
        val textViewTimeStamp = TextView(contexto)
        textViewTimeStamp.layoutParams = LayoutParams(
            LayoutParams.MATCH_CONSTRAINT,
            LayoutParams.WRAP_CONTENT
        )
        textViewTimeStamp.text = timestamp
        textViewTimeStamp.id = idTimeStamp
        textViewTimeStamp.setTextColor(ContextCompat.getColor(contexto, R.color.black))
        return textViewTimeStamp
    }

    private fun geraTextViewTitulo(idTitulo: Int): TextView {
        val textViewTitulo = TextView(contexto)
        textViewTitulo.layoutParams = LayoutParams(
            LayoutParams.MATCH_CONSTRAINT,
            LayoutParams.WRAP_CONTENT
        )
        textViewTitulo.text = titulo
        textViewTitulo.id = idTitulo
        textViewTitulo.setTextColor(ContextCompat.getColor(contexto, R.color.black))
        //precisa setar o tamanho do texto
        textViewTitulo.setTypeface(null, Typeface.BOLD)
        return textViewTitulo
    }

    private fun geraImageButtonFavoritar(idButtonFavoritar: Int): ImageButton {
        val buttonFavoritar = ImageButton(contexto)
        buttonFavoritar.layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        buttonFavoritar.setBackgroundColor(ContextCompat.getColor(contexto, R.color.white))
        buttonFavoritar.id = idButtonFavoritar
        buttonFavoritar.setImageDrawable(ContextCompat.getDrawable(contexto,R.drawable.ic_action_doador_favoritar))
        return buttonFavoritar
    }

    private fun layoutParamsConstraintLayout(): LayoutParams {
        return LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        CardView(this)
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.card_campanha_visao_ong)
//        setContentView(R.layout.card_campanha_visao_doador)
//        var listaItens = findViewById<View>(R.id.cardCampanhaDoadorItens) as LinearLayout
//        adicionaItensNaListaView(listaItens)
//    }

    private fun adicionaItensNaListaView() {
        val listaItens = findViewById<LinearLayout>(R.id.cardCampanhaDoadorItens)
        listaItens.addView(
            itemCardDoador(
                "Ração",
                "Kg",
                90,
                50,
                contexto
            )
        )
        quantidadeDeItens++
        listaItens.addView(
            itemCardDoador(
                "Coleira",
                "Unidades",
                100,
                25,
                contexto
            )
        )
        quantidadeDeItens++
        listaItens.addView(
            itemCardDoador(
                "Água",
                "L",
                250,
                200,
                contexto
            )
        )
        quantidadeDeItens++
        listaItens.addView(
            itemCardDoador(
                "Sabão",
                "L",
                100,
                10,
                contexto
            )
        )
        quantidadeDeItens++
        defineTextQuantidadeDeItens()
    }

    fun defineTextQuantidadeDeItens(): String {
        if (this.quantidadeDeItens == 1) {
            return "1 item"
        } else if (this.quantidadeDeItens > 1) {
            return quantidadeDeItens.toString() + " itens"
        }
        return "0 itens"
    }
}