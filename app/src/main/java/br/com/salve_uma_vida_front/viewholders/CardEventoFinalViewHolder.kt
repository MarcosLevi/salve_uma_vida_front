package br.com.salve_uma_vida_front.viewholders

import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.databinding.CardEventoFinalBinding

class CardEventoFinalViewHolder(binding: CardEventoFinalBinding) : RecyclerView.ViewHolder(binding.root) {

    var imagemEvento: ImageView = binding.cardEventoFinalImagem
    var textViewTitulo: TextView = binding.cardEventoFinalTitulo
    var textViewTimeStamp: TextView = binding.cardEventoFinalData
    var cardEvento: CardView = binding.cardEventoFinal

}