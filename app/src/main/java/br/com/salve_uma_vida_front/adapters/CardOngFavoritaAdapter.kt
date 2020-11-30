package br.com.salve_uma_vida_front.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.databinding.CardOngFavoritaBinding
import br.com.salve_uma_vida_front.dp
import br.com.salve_uma_vida_front.dto.OngFavoritaDto
import br.com.salve_uma_vida_front.viewholders.CardOngFavoritaViewHolder
import com.squareup.picasso.Picasso

class CardOngFavoritaAdapter(var listaCards: MutableList<OngFavoritaDto>, var contexto: Context) :
    RecyclerView.Adapter<CardOngFavoritaViewHolder>() {

    lateinit var bindingOngFavorita: CardOngFavoritaBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardOngFavoritaViewHolder {
//        bindingCampanha = CardCampanhaFinalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        bindingOngFavorita =
            CardOngFavoritaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder =
            CardOngFavoritaViewHolder(
                bindingOngFavorita
            )
        return viewHolder
    }

    override fun getItemCount(): Int {
        return listaCards.size
    }

    override fun onBindViewHolder(holder: CardOngFavoritaViewHolder, position: Int) {
        val currentItem: OngFavoritaDto = listaCards.get(position)

        //seta imagem
        Picasso.get()
            .load(currentItem.image)
            .resize(110.dp, 110.dp)
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(holder.imagemPerfil)


        holder.textViewNome.text = currentItem.name
//        holder.textViewTimeStamp.text = FormatStringToDate(currentItem.data)
        holder.textViewCampanhasEventosAbertos.text = "2 Campanha Aberta\\n2 Evento Aberto"

        holder.itemView.setOnClickListener {
            Log.d("CardOngFavorita", currentItem.toString())
        }
        holder.imageButtonFavorito.setOnClickListener {
            if (holder.imageButtonFavorito.drawable == ContextCompat.getDrawable(
                    contexto,
                    R.drawable.ic_baseline_star_border_24
                )
            ) {
                holder.imageButtonFavorito.setImageDrawable(
                    ContextCompat.getDrawable(
                        contexto,
                        R.drawable.ic_baseline_star_24
                    )
                )
            } else {
                holder.imageButtonFavorito.setImageDrawable(
                    ContextCompat.getDrawable(
                        contexto,
                        R.drawable.ic_baseline_star_border_24
                    )
                )
            }
        }
    }

}