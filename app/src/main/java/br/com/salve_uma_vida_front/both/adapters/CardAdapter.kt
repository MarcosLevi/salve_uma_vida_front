package br.com.salve_uma_vida_front.both.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.doador.adapters.ItemAdapter
import br.com.salve_uma_vida_front.both.models.CardPesquisa
import com.squareup.picasso.Picasso
import java.util.*

class CardAdapter(var listaCards: MutableList<CardPesquisa>, var contexto: Context) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>(), Filterable {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    var listaCardsAll: MutableList<CardPesquisa> = listaCards.toMutableList()


    class CardViewHolder(cardView: View) : RecyclerView.ViewHolder(cardView) {

        var imagemCampanha: ImageView = cardView.findViewById(R.id.cardImagemCampanha)
        var buttonFavoritar: ImageButton = cardView.findViewById(R.id.cardFavoritar)
        var textViewTitulo: TextView = cardView.findViewById(R.id.cardTitulo)
        var textViewTimeStamp: TextView = cardView.findViewById(R.id.cardDataCampanha)
        var textViewDescricao: TextView = cardView.findViewById(R.id.cardDescricao)
        var textViewQuantidadeItens: TextView = cardView.findViewById(R.id.cardQuantidadeItens)
        var itensCampanha: RecyclerView = cardView.findViewById(R.id.itensCampanhaDoador)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_campanha_final, parent, false)
        val viewHolder =
            CardViewHolder(
                view
            )
        return viewHolder
    }

    override fun getItemCount(): Int {
        return listaCards.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem: CardPesquisa = listaCards.get(position)

        //seta imagem
        Picasso.get()
            .load(currentItem.imagem)
            .resize(110.dp, 110.dp)
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(holder.imagemCampanha)


//        fazer função que salve esse cara
        holder.buttonFavoritar.setOnClickListener {
            clickFavoritar(currentItem, holder.buttonFavoritar)
        }
        holder.textViewTitulo.text = currentItem.titulo
        holder.textViewTimeStamp.text = currentItem.timeStamp
        holder.textViewDescricao.text = currentItem.descricao
        holder.textViewQuantidadeItens.text = quantidadeDeItensString(currentItem.quantidadeDeItens)

        mRecyclerView = holder.itensCampanha
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(contexto)
        mAdapter =
            ItemAdapter(currentItem.listaDeItens)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter
    }

    private fun clickFavoritar(currentItem: CardPesquisa, buttonFavoritar: ImageButton) {
        currentItem.favorito = !currentItem.favorito
        if (currentItem.favorito) {
            Toast.makeText(contexto, "Ficou favorito", Toast.LENGTH_SHORT)
                .show()
            buttonFavoritar.setImageDrawable(
                ContextCompat.getDrawable(
                    contexto,
                    R.drawable.ic_baseline_star_24
                )
            )
        } else {
            Toast.makeText(contexto, "Não é mais favorito", Toast.LENGTH_SHORT)
                .show()
            buttonFavoritar.setImageDrawable(
                ContextCompat.getDrawable(
                    contexto,
                    R.drawable.ic_baseline_star_border_24
                )
            )
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

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {

                var filteredList: MutableList<CardPesquisa> = mutableListOf()
                if (charSequence.toString().isEmpty()) {
                    filteredList = listaCardsAll.toMutableList()
                } else {
                    for (card in listaCardsAll) {
                        if (card.titulo.toLowerCase(Locale.getDefault()).contains(charSequence.toString().toLowerCase(Locale.getDefault()))) {
                            filteredList.add(card)
                        } else if (card.descricao.toLowerCase(Locale.getDefault()).contains(charSequence.toString().toLowerCase(Locale.getDefault()))) {
                            filteredList.add(card)
                        } else {
                            for (item in card.listaDeItens) {
                                if (item.titulo.toLowerCase(Locale.getDefault()).contains(charSequence.toString().toLowerCase(Locale.getDefault()))
                                ) {
                                    filteredList.add(card)
                                }

                            }
                        }

                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList

                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults?
            ) {
                listaCards.clear()
                listaCards.addAll(filterResults!!.values as Collection<CardPesquisa>)
                notifyDataSetChanged()
            }

        }
    }


}