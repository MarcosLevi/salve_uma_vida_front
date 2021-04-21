package br.com.salve_uma_vida_front.viewholders

import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.databinding.CardCampanhaEditavelBinding
import br.com.salve_uma_vida_front.databinding.CardEventoEditavelBinding
import br.com.salve_uma_vida_front.databinding.CardEventoFinalBinding

class CardCampanhaEditavelViewHolder(binding: CardCampanhaEditavelBinding) : RecyclerView.ViewHolder(binding.root) {

    var imagemCampanha: ImageView = binding.cardCampanhaEditavelImagem
    var textViewTitulo: TextView = binding.cardCampanhaEditavelTitulo
    var textViewTimeStamp: TextView = binding.cardCampanhaEditavelData
    var imageButtonArquivar: ImageButton = binding.cardCampanhaEditavelArquivar

}