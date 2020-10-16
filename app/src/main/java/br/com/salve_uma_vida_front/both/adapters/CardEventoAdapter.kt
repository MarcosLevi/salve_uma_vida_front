package br.com.salve_uma_vida_front.both.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.both.FormatStringToDate
import br.com.salve_uma_vida_front.both.viewholders.CardEventoViewHolder
import br.com.salve_uma_vida_front.databinding.CardEventoFinalBinding
import br.com.salve_uma_vida_front.dto.EventoDto
import com.squareup.picasso.Picasso

class CardEventoAdapter(var listaCards: MutableList<EventoDto>, var contexto: Context) :
    RecyclerView.Adapter<CardEventoViewHolder>() {

    var listaCardsAll: MutableList<EventoDto> = listaCards
    lateinit var bindingEvento: CardEventoFinalBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardEventoViewHolder {
//        bindingCampanha = CardCampanhaFinalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        bindingEvento = CardEventoFinalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val viewHolder =
            CardEventoViewHolder(
                bindingEvento
            )
        return viewHolder
    }

    override fun getItemCount(): Int {
        return listaCards.size
    }

    override fun onBindViewHolder(holder: CardEventoViewHolder, position: Int) {
        val currentItem: EventoDto = listaCards.get(position)

        //seta imagem
        Picasso.get()
            .load(currentItem.imagem)
            .resize(110.dp, 110.dp)
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(holder.imagemEvento)


//        fazer função que salve esse cara
        holder.buttonFavoritar.setOnClickListener {
            clickFavoritar(currentItem, holder.buttonFavoritar)
        }
        ajustaIconeFavorito(currentItem, holder.buttonFavoritar)
        holder.textViewTitulo.text = currentItem.titulo
//        holder.textViewTimeStamp.text = FormatStringToDate(currentItem.data)
        holder.textViewTimeStamp.text = FormatStringToDate(currentItem.data)
        holder.textViewDescricao.text = currentItem.descricao
    }

    private fun clickFavoritar(currentItem: EventoDto, buttonFavoritar: ImageButton) {
        currentItem.favorito = !currentItem.favorito
        ajustaIconeFavorito(currentItem, buttonFavoritar)
    }

    private fun ajustaIconeFavorito(
        currentItem: EventoDto,
        buttonFavoritar: ImageButton
    ) {
        if (currentItem.favorito) {
            buttonFavoritar.setImageDrawable(
                ContextCompat.getDrawable(
                    contexto,
                    R.drawable.ic_baseline_star_24
                )
            )
        } else {
            buttonFavoritar.setImageDrawable(
                ContextCompat.getDrawable(
                    contexto,
                    R.drawable.ic_baseline_star_border_24
                )
            )
        }
    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

}