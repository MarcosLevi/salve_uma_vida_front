package br.com.salve_uma_vida_front.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.FormatStringToDate
import br.com.salve_uma_vida_front.databinding.CardEventoEditavelBinding
import br.com.salve_uma_vida_front.dp
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.interfaces.CardEventoEditavelListener
import br.com.salve_uma_vida_front.viewholders.CardEventoEditavelViewHolder
import com.squareup.picasso.Picasso

class CardEventoEditavelAdapter(var listaCards: MutableList<EventoDto>, private val listener: CardEventoEditavelListener) :
    RecyclerView.Adapter<CardEventoEditavelViewHolder>() {

    lateinit var bindingEvento: CardEventoEditavelBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardEventoEditavelViewHolder {
//        bindingCampanha = CardCampanhaFinalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        bindingEvento =
            CardEventoEditavelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder =
            CardEventoEditavelViewHolder(
                bindingEvento
            )
        return viewHolder
    }

    override fun getItemCount(): Int {
        return listaCards.size
    }

    override fun onBindViewHolder(holderFinal: CardEventoEditavelViewHolder, position: Int) {
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
//        holder.textViewTimeStamp.text = FormatStringToDate(currentItem.data)
        holderFinal.textViewTimeStamp.text = "Ocorrerá em ${FormatStringToDate(currentItem.data!!)}"

        holderFinal.imageButtonEditar.setOnClickListener{
            Log.d("CardEventoEditavel", "Cliquei em editar")
            listener.onEditaClicked(EventoDto.newInstance(currentItem))
        }
        holderFinal.imageButtonArquivar.setOnClickListener{
            Log.d("CardEventoEditavel", "Cliquei em Arquivar")
            listener.onArquivaClicked(currentItem)
        }
        holderFinal.imageButtonFinalizar.setOnClickListener{
            Log.d("CardEventoEditavel", "Cliquei em Finalizar")
            listener.onFinalizaClicked(currentItem)
        }
    }

}