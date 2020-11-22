package br.com.salve_uma_vida_front.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.FormatStringToDate
import br.com.salve_uma_vida_front.dp
import br.com.salve_uma_vida_front.viewholders.CardEventoViewHolder
import br.com.salve_uma_vida_front.databinding.CardEventoFinalBinding
import br.com.salve_uma_vida_front.dto.EventoDto
import com.squareup.picasso.Picasso

class CardEventoAdapter(var listaCards: MutableList<EventoDto>, var contexto: Context) :
    RecyclerView.Adapter<CardEventoViewHolder>() {

    lateinit var bindingEvento: CardEventoFinalBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardEventoViewHolder {
//        bindingCampanha = CardCampanhaFinalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        bindingEvento =
            CardEventoFinalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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


        holder.textViewTitulo.text = currentItem.titulo
//        holder.textViewTimeStamp.text = FormatStringToDate(currentItem.data)
        holder.textViewTimeStamp.text =
            FormatStringToDate(currentItem.data)
        holder.textViewDescricao.text = currentItem.descricao

        holder.itemView.setOnClickListener{
            Log.d("CardEventoViewHolder", currentItem.toString())
        }
    }

}