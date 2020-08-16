package br.com.salve_uma_vida_front.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.models.CardDoador
import br.com.salve_uma_vida_front.models.ItemCardDoador
import java.lang.RuntimeException

class CardAdapter(var listaCards: List<CardDoador>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    class CardViewHolder : RecyclerView.ViewHolder {

        var imagemCampanha: ImageView
        var buttonFavoritar: ImageButton
        var textViewTitulo: TextView
        var textViewTimeStamp: TextView
        var textViewDescricao: TextView
        var textViewQuantidadeItens: TextView
        var itensCampanha: RecyclerView

        constructor(cardView: View) : super(cardView) {
            imagemCampanha = cardView.findViewById(R.id.cardImagemCampanha)
            buttonFavoritar = cardView.findViewById(R.id.cardFavoritar)
            textViewTitulo = cardView.findViewById(R.id.cardTitulo)
            textViewTimeStamp = cardView.findViewById(R.id.cardTimeStamp)
            textViewDescricao = cardView.findViewById(R.id.cardDescricao)
            textViewQuantidadeItens = cardView.findViewById(R.id.cardQuantidadeItens)
            itensCampanha = cardView.findViewById(R.id.itensCampanha)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        var view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_campanha_visao_doador, parent, false)
        var viewHolder = CardAdapter.CardViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return listaCards.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        var currentItem: CardDoador = listaCards.get(position)

        //mudar pra pegar imagem pra url
        holder.imagemCampanha.setImageResource(R.drawable.ic_launcher_foreground)
//        fazer função que salve esse cara
//        holder.buttonFavoritar
        holder.textViewTitulo.text = currentItem.titulo

    }
}