package br.com.salve_uma_vida_front.both.viewholders

import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.databinding.CardCampanhaFinalBinding
import br.com.salve_uma_vida_front.databinding.CardEventoFinalBinding

class CardEventoViewHolder(binding: CardEventoFinalBinding) : RecyclerView.ViewHolder(binding.root) {

    var imagemEvento: ImageView = binding.cardEventoImagem
    var textViewTitulo: TextView = binding.cardEventoTitulo
    var textViewTimeStamp: TextView = binding.cardEventoData
    var textViewDescricao: TextView = binding.cardEventoDescricao

}