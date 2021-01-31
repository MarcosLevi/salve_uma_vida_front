package br.com.salve_uma_vida_front.viewholders

import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.databinding.CardEventoEditavelBinding
import br.com.salve_uma_vida_front.databinding.CardEventoFinalBinding

class CardEventoEditavelViewHolder(binding: CardEventoEditavelBinding) : RecyclerView.ViewHolder(binding.root) {

    var imagemEvento: ImageView = binding.cardEventoEditavelImagem
    var textViewTitulo: TextView = binding.cardEventoEditavelTitulo
    var textViewTimeStamp: TextView = binding.cardEventoEditavelData
    var imageButtonEditar: ImageButton = binding.cardEventoEditavelEditar
    var imageButtonFinalizar: ImageButton = binding.cardEventoEditavelFinalizar
    var imageButtonArquivar: ImageButton = binding.cardEventoEditavelArquivar

}