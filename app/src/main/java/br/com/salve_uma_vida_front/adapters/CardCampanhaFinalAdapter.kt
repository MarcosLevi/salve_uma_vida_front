package br.com.salve_uma_vida_front.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.FormatStringToDate
import br.com.salve_uma_vida_front.dp
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.viewholders.CardCampanhaFinalViewHolder
import br.com.salve_uma_vida_front.databinding.CardCampanhaFinalBinding
import com.squareup.picasso.Picasso

class CardCampanhaFinalAdapter(var listaCards: MutableList<CampanhaDto>, var contexto: Context) :
    RecyclerView.Adapter<CardCampanhaFinalViewHolder>() {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapterDoador: RecyclerView.Adapter<ItemAdapterDoador.ItemViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var bindingCampanha: CardCampanhaFinalBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardCampanhaFinalViewHolder {
        bindingCampanha = CardCampanhaFinalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        bindingEvento = CardEventoFinalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val viewHolder =
            CardCampanhaFinalViewHolder(
                bindingCampanha
            )
        return viewHolder
    }

    override fun getItemCount(): Int {
        return listaCards.size
    }

    override fun onBindViewHolder(holderFinal: CardCampanhaFinalViewHolder, position: Int) {
        val currentItem: CampanhaDto = listaCards.get(position)

//        seta imagem
        Picasso.get()
            .load(currentItem.userImage)
            .resize(110.dp, 110.dp)
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(holderFinal.imagemCampanha)


//        fazer função que salve esse cara
        holderFinal.textViewTitulo.text = currentItem.titulo
        holderFinal.textViewTimeStamp.text = "Anunciado em ${FormatStringToDate(currentItem.data)}"
        holderFinal.textViewDescricao.text = currentItem.descricao
        holderFinal.textViewQuantidadeItens.text = quantidadeDeItensString(currentItem.itens.size) //quantidadeDeItensString(currentItem.quantidadeDeItens)
        holderFinal.itemView.setOnClickListener{
            Log.d("CardCampanhaViewHolder", currentItem.toString())
        }

        mRecyclerView = holderFinal.itensCampanha
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(contexto)
        mAdapterDoador =
            ItemAdapterDoador(currentItem.itens)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapterDoador
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