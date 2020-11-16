package br.com.salve_uma_vida_front.both.viewholders

import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.databinding.CardCampanhaFinalBinding

class CardCampanhaViewHolder(binding: CardCampanhaFinalBinding) : RecyclerView.ViewHolder(binding.root) {

    var imagemCampanha: ImageView = binding.cardCampanhaImagem
    var textViewTitulo: TextView = binding.cardCampanhaTitulo
    var textViewTimeStamp: TextView = binding.cardCampanhaData
    var textViewDescricao: TextView = binding.cardCampanhaDescricao
    var textViewQuantidadeItens: TextView = binding.cardQuantidadeItens
    var itensCampanha: RecyclerView = binding.cardCampanhaItens

}