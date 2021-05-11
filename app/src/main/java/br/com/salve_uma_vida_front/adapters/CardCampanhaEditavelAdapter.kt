package br.com.salve_uma_vida_front.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.FormatStringToDate
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.databinding.CardCampanhaEditavelBinding
import br.com.salve_uma_vida_front.dp
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.interfaces.CardCampanhaEditavelListener
import br.com.salve_uma_vida_front.viewholders.CardCampanhaEditavelViewHolder
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class CardCampanhaEditavelAdapter(var listaCards: MutableList<CampanhaDto>, private val listener: CardCampanhaEditavelListener) :
    RecyclerView.Adapter<CardCampanhaEditavelViewHolder>() {

    lateinit var bindingCampanha: CardCampanhaEditavelBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardCampanhaEditavelViewHolder {
        bindingCampanha = CardCampanhaEditavelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardCampanhaEditavelViewHolder(
            bindingCampanha
        )
    }

    override fun getItemCount(): Int {
        return if (listaCards.size == 0) 10 else listaCards.size
    }

    override fun onBindViewHolder(holderFinal: CardCampanhaEditavelViewHolder, position: Int) {
        if (listaCards.size == 0) {
            holderFinal.loadingArquivar.visibility = View.VISIBLE
            holderFinal.loadingTitulo.visibility = View.VISIBLE
            holderFinal.loadingData.visibility = View.VISIBLE
            holderFinal.loadingImagem.visibility = View.VISIBLE
            holderFinal.campanhaFinalizada.visibility = View.GONE
        } else {
            val currentItem: CampanhaDto = listaCards[position]
            Picasso.get()
                .load(currentItem.userImage)
                .resize(110.dp, 110.dp)
                .centerCrop()
                .error(R.drawable.ic_baseline_report_problem_24)
                .into(holderFinal.imagemCampanha, object : Callback {
                    override fun onSuccess() {
                        holderFinal.loadingImagem.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        holderFinal.loadingImagem.visibility = View.GONE
                    }

                })
            holderFinal.textViewTitulo.text = currentItem.titulo
            holderFinal.textViewTimeStamp.text =
                "Anunciado em ${FormatStringToDate(currentItem.data)}"

            if (currentItem.aberta!!){
                holderFinal.imageButtonArquivar.setOnClickListener {
                    Log.d("CardCampanhaEditavel", "Cliquei em Arquivar")
                    listener.onArquivaClicked(currentItem)
                }
            }else{
                holderFinal.imageButtonArquivar.visibility = View.GONE
                holderFinal.campanhaFinalizada.visibility = View.VISIBLE
            }

            holderFinal.loadingArquivar.visibility = View.GONE
            holderFinal.loadingTitulo.visibility = View.GONE
            holderFinal.loadingData.visibility = View.GONE
        }
    }

}