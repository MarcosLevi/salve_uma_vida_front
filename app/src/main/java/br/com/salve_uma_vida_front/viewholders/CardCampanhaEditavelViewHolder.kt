package br.com.salve_uma_vida_front.viewholders

import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.databinding.CardCampanhaEditavelBinding

class CardCampanhaEditavelViewHolder(binding: CardCampanhaEditavelBinding) : RecyclerView.ViewHolder(binding.root) {

    var imagemCampanha: ImageView = binding.cardCampanhaEditavelImagem
    var textViewTitulo: TextView = binding.cardCampanhaEditavelTitulo
    var textViewTimeStamp: TextView = binding.cardCampanhaEditavelData
    var imageButtonArquivar: ImageButton = binding.cardCampanhaEditavelArquivar
    var loadingTitulo: FrameLayout = binding.cardCampanhaEditavelLoadingTitulo
    var loadingData: FrameLayout = binding.cardCampanhaEditavelLoadingData
    var loadingImagem: FrameLayout = binding.cardCampanhaEditavelLoadingImagem
    var loadingArquivar: FrameLayout = binding.cardCampanhaEditavelLoadingArquivar
    var campanhaFinalizada: ImageView = binding.cardCampanhaEditavelFinalizada
}