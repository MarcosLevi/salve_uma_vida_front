package br.com.salve_uma_vida_front.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.FormatStringToDate
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.databinding.CardEventoEditavelBinding
import br.com.salve_uma_vida_front.dp
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.interfaces.CardEventoEditavelListener
import br.com.salve_uma_vida_front.viewholders.CardEventoEditavelViewHolder
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class CardEventoEditavelAdapter(
    var listaCards: MutableList<EventoDto>,
    private val listener: CardEventoEditavelListener
) :
    RecyclerView.Adapter<CardEventoEditavelViewHolder>() {

    lateinit var bindingEvento: CardEventoEditavelBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardEventoEditavelViewHolder {
        bindingEvento =
            CardEventoEditavelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardEventoEditavelViewHolder(
            bindingEvento
        )
    }

    override fun getItemCount(): Int {
        return if (listaCards.size == 0) 10 else listaCards.size
    }

    override fun onBindViewHolder(holderFinal: CardEventoEditavelViewHolder, position: Int) {
        if (listaCards.size == 0) {
            holderFinal.loadingArquivar.visibility = View.VISIBLE
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


            holderFinal.textViewTitulo.text = currentItem.titulo
            holderFinal.textViewTimeStamp.text =
                "Ocorrerá em ${FormatStringToDate(currentItem.data!!)}"

            holderFinal.imageButtonArquivar.setOnClickListener {
                Log.d("CardEventoEditavel", "Cliquei em Arquivar")
                listener.onArquivaClicked(currentItem)
            }
            holderFinal.loadingArquivar.visibility = View.GONE
            holderFinal.loadingTitulo.visibility = View.GONE
            holderFinal.loadingData.visibility = View.GONE
        }
    }

}