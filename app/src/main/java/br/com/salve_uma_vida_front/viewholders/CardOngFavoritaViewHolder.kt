package br.com.salve_uma_vida_front.viewholders

import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.databinding.CardCampanhaFinalBinding
import br.com.salve_uma_vida_front.databinding.CardOngFavoritaBinding

class CardOngFavoritaViewHolder(binding: CardOngFavoritaBinding) : RecyclerView.ViewHolder(binding.root) {

    var imagemPerfil: ImageView = binding.cardOngFavoritaImagem
    var textViewNome: TextView = binding.cardOngFavoritaNome
    var imageButtonFavorito: ImageButton = binding.cardOngFavoritaBotaoFavorito

}