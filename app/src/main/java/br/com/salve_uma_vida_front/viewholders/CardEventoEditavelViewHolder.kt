package br.com.salve_uma_vida_front.viewholders

import android.widget.FrameLayout
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
    var imageButtonArquivar: ImageButton = binding.cardEventoEditavelArquivar
    var loadingTitulo: FrameLayout = binding.cardEventoEditavelLoadingTitulo
    var loadingData: FrameLayout = binding.cardEventoEditavelLoadingData
    var loadingImagem: FrameLayout = binding.cardEventoEditavelLoadingImagem
    var loadingArquivar: FrameLayout = binding.cardEventoEditavelLoadingArquivar

}