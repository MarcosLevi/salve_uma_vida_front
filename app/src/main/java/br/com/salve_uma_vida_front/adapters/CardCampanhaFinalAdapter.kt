package br.com.salve_uma_vida_front.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.FormatStringToDate
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.databinding.CardCampanhaFinalBinding
import br.com.salve_uma_vida_front.dp
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.interfaces.CardCampanhaFinalListener
import br.com.salve_uma_vida_front.viewholders.CardCampanhaFinalViewHolder
import br.com.salve_uma_vida_front.viewholders.ItemCardCampanhaFinalViewHolder
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class CardCampanhaFinalAdapter(
    var listaCards: MutableList<CampanhaDto>,
    var contexto: Context,
    private val listener: CardCampanhaFinalListener
) :
    RecyclerView.Adapter<CardCampanhaFinalViewHolder>() {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapterDoador: RecyclerView.Adapter<ItemCardCampanhaFinalViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var bindingCampanha: CardCampanhaFinalBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardCampanhaFinalViewHolder {
        bindingCampanha =
            CardCampanhaFinalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardCampanhaFinalViewHolder(
            bindingCampanha
        )
    }


    override fun getItemCount(): Int {
        return if (listaCards.size == 0) 10 else listaCards.size
    }

    override fun onBindViewHolder(holderFinal: CardCampanhaFinalViewHolder, position: Int) {
        if (listaCards.size == 0) {
            holderFinal.loadingQuantidadeItens.visibility = View.VISIBLE
            holderFinal.loadingItens.visibility = View.VISIBLE
            holderFinal.loadingTitulo.visibility = View.VISIBLE
            holderFinal.loadingData.visibility = View.VISIBLE
            holderFinal.loadingImagem.visibility = View.VISIBLE
        } else {
            val currentItem: CampanhaDto = listaCards.get(position)

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
            holderFinal.textViewQuantidadeItens.text =
                quantidadeDeItensString(currentItem.itens.size)
            holderFinal.cardCampanha.setOnLongClickListener {
                listener.abrePerfilOng(currentItem.userId!!)
                true
            }
            holderFinal.cardCampanha.setOnClickListener {
                listener.abreCampanha(currentItem)
            }
            if (!currentItem.aberta!!)
                holderFinal.campanhaFinalizada.visibility = View.VISIBLE

            mRecyclerView = holderFinal.itensCampanha
            mRecyclerView.setHasFixedSize(true)
            mLayoutManager = LinearLayoutManager(contexto)
            mAdapterDoador = ItemCardCampanhaFinal(currentItem.itens)
            mRecyclerView.layoutManager = mLayoutManager
            mRecyclerView.adapter = mAdapterDoador
            holderFinal.loadingQuantidadeItens.visibility = View.GONE
            holderFinal.loadingItens.visibility = View.GONE
            holderFinal.loadingTitulo.visibility = View.GONE
            holderFinal.loadingData.visibility = View.GONE
        }
    }

    fun quantidadeDeItensString(quantidade: Int): String {
        if (quantidade == 1) {
            return "1 item"
        } else if (quantidade > 1) {
            return "$quantidade itens"
        }
        return "0 itens"
    }

}