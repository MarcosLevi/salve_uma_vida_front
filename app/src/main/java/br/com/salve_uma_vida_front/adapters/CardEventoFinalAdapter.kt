package br.com.salve_uma_vida_front.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.FormatStringToDate
import br.com.salve_uma_vida_front.dp
import br.com.salve_uma_vida_front.viewholders.CardEventoFinalViewHolder
import br.com.salve_uma_vida_front.databinding.CardEventoFinalBinding
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.interfaces.CardEventoFinalListener
import com.squareup.picasso.Picasso

class CardEventoFinalAdapter(var listaCards: MutableList<EventoDto>, var contexto: Context, private val listener: CardEventoFinalListener) :
    RecyclerView.Adapter<CardEventoFinalViewHolder>() {

    lateinit var bindingEvento: CardEventoFinalBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardEventoFinalViewHolder {
//        bindingCampanha = CardCampanhaFinalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        bindingEvento =
            CardEventoFinalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder =
            CardEventoFinalViewHolder(
                bindingEvento
            )
        return viewHolder
    }

    override fun getItemCount(): Int {
        return listaCards.size
    }

    override fun onBindViewHolder(holderFinal: CardEventoFinalViewHolder, position: Int) {
        val currentItem: EventoDto = listaCards.get(position)

        //seta imagem
        Picasso.get()
            .load(currentItem.imagem)
            .resize(110.dp, 110.dp)
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(holderFinal.imagemEvento)


        holderFinal.textViewTitulo.text = currentItem.titulo
        holderFinal.textViewTimeStamp.text = "Ocorrerá em ${FormatStringToDate(currentItem.data!!)}"

        holderFinal.cardEvento.setOnLongClickListener{
            listener.abrePerfilOng(currentItem.userId!!)
            true
        }
        holderFinal.cardEvento.setOnClickListener{
            listener.abreEvento(currentItem)
        }
    }

}