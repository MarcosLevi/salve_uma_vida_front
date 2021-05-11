package br.com.salve_uma_vida_front.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.FormatStringToDate
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.databinding.CardEventoFinalBinding
import br.com.salve_uma_vida_front.dp
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.interfaces.CardEventoFinalListener
import br.com.salve_uma_vida_front.viewholders.CardEventoFinalViewHolder
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class CardEventoFinalAdapter(
    var listaCards: MutableList<EventoDto>,
    private val listener: CardEventoFinalListener
) :
    RecyclerView.Adapter<CardEventoFinalViewHolder>() {

    lateinit var bindingEvento: CardEventoFinalBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardEventoFinalViewHolder {
        bindingEvento =
            CardEventoFinalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardEventoFinalViewHolder(
            bindingEvento
        )
    }

    override fun getItemCount(): Int {
        return if (listaCards.size == 0) 10 else listaCards.size
    }

    override fun onBindViewHolder(holderFinal: CardEventoFinalViewHolder, position: Int) {
        if (listaCards.size == 0) {
            holderFinal.loadingTitulo.visibility = View.VISIBLE
            holderFinal.loadingData.visibility = View.VISIBLE
            holderFinal.loadingImagem.visibility = View.VISIBLE
        } else {
            val currentItem: EventoDto = listaCards[position]

            Picasso.get()
                .load(currentItem.imagem)
                .resize(110.dp, 110.dp)
                .centerCrop()
                .error(R.drawable.ic_baseline_report_problem_24)
                .into(holderFinal.imagemEvento, object : Callback {
                    override fun onSuccess() {
                        holderFinal.loadingImagem.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        holderFinal.loadingImagem.visibility = View.GONE
                    }

                })

            if (!currentItem.aberta!!)
                holderFinal.eventoFinalizado.visibility = View.VISIBLE

            holderFinal.textViewTitulo.text = currentItem.titulo
            holderFinal.textViewTimeStamp.text =
                "Ocorrerá em ${FormatStringToDate(currentItem.data!!)}"

            holderFinal.cardEvento.setOnLongClickListener {
                listener.abrePerfilOng(currentItem.userId!!)
                true
            }
            holderFinal.cardEvento.setOnClickListener {
                listener.abreEvento(currentItem)
            }
            holderFinal.loadingTitulo.visibility = View.GONE
            holderFinal.loadingData.visibility = View.GONE
        }
    }

}