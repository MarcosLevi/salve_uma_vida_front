package br.com.salve_uma_vida_front.viewholders

import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.databinding.CardCampanhaFinalBinding

class CardCampanhaFinalViewHolder(binding: CardCampanhaFinalBinding) : RecyclerView.ViewHolder(binding.root) {

    var imagemCampanha: ImageView = binding.cardCampanhaFinalImagem
    var textViewTitulo: TextView = binding.cardCampanhaFinalTitulo
    var textViewTimeStamp: TextView = binding.cardCampanhaFinalData
    var textViewQuantidadeItens: TextView = binding.cardCampanhaFinalQuantidadeItens
    var itensCampanha: RecyclerView = binding.cardCampanhaFinalItens
    var cardCampanha: CardView = binding.cardCampanhaFinal

}