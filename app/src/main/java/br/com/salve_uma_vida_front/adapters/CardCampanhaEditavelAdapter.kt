package br.com.salve_uma_vida_front.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.FormatStringToDate
import br.com.salve_uma_vida_front.databinding.CardCampanhaEditavelBinding
import br.com.salve_uma_vida_front.dp
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.interfaces.CardCampanhaEditavelListener
import br.com.salve_uma_vida_front.viewholders.CardCampanhaEditavelViewHolder
import br.com.salve_uma_vida_front.viewholders.CardEventoEditavelViewHolder
import com.squareup.picasso.Picasso

class CardCampanhaEditavelAdapter(var listaCards: MutableList<CampanhaDto>, private val listener: CardCampanhaEditavelListener) :
    RecyclerView.Adapter<CardCampanhaEditavelViewHolder>() {

    lateinit var bindingCampanha: CardCampanhaEditavelBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardCampanhaEditavelViewHolder {
//        bindingCampanha = CardCampanhaFinalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        bindingCampanha =
            CardCampanhaEditavelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder =
            CardCampanhaEditavelViewHolder(
                bindingCampanha
            )
        return viewHolder
    }

    override fun getItemCount(): Int {
        return listaCards.size
    }

    override fun onBindViewHolder(holderFinal: CardCampanhaEditavelViewHolder, position: Int) {
        val currentItem: CampanhaDto = listaCards.get(position)

        //seta imagem
        Picasso.get()
            .load(currentItem.userImage)
            .resize(110.dp, 110.dp)
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(holderFinal.imagemCampanha)


        holderFinal.textViewTitulo.text = currentItem.titulo
//        holder.textViewTimeStamp.text = FormatStringToDate(currentItem.data)
        holderFinal.textViewTimeStamp.text = "Anunciado em ${FormatStringToDate(currentItem.data)}"

        holderFinal.imageButtonEditar.setOnClickListener{
            Log.d("CardCampanhaEditavel", "Cliquei em editar")
            listener.onEditaClicked(CampanhaDto.newInstance(currentItem))
        }
        holderFinal.imageButtonArquivar.setOnClickListener{
            Log.d("CardCampanhaEditavel", "Cliquei em Arquivar")
        }
        holderFinal.imageButtonFinalizar.setOnClickListener{
            Log.d("CardCampanhaEditavel", "Cliquei em Finalizar")
        }
    }

}