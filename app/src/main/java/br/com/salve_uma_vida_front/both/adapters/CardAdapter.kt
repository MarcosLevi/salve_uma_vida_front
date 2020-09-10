package br.com.salve_uma_vida_front.both.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.both.DateToString
import br.com.salve_uma_vida_front.both.models.Campanha
import br.com.salve_uma_vida_front.databinding.CardCampanhaFinalBinding
import br.com.salve_uma_vida_front.databinding.CardEventoFinalBinding
import br.com.salve_uma_vida_front.doador.adapters.ItemAdapter
import com.squareup.picasso.Picasso
import java.util.*

class CardAdapter(var listaCards: MutableList<Campanha>, var contexto: Context) :
    RecyclerView.Adapter<CardAdapter.CardCampanhaViewHolder>(), Filterable {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    var listaCardsAll: MutableList<Campanha> = listaCards
    lateinit var bindingCampanha: CardCampanhaFinalBinding
//    lateinit var bindingEvento: CardEventoFinalBinding


    class CardCampanhaViewHolder(binding: CardCampanhaFinalBinding) : RecyclerView.ViewHolder(binding.root) {

        var imagemCampanha: ImageView = binding.cardCampanhaImagem
        var buttonFavoritar: ImageButton = binding.cardCampanhaFavoritar
        var textViewTitulo: TextView = binding.cardCampanhaTitulo
        var textViewTimeStamp: TextView = binding.cardCampanhaData
        var textViewDescricao: TextView = binding.cardCampanhaDescricao
        var textViewQuantidadeItens: TextView = binding.cardQuantidadeItens
        var itensCampanha: RecyclerView = binding.cardCampanhaItens

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardCampanhaViewHolder {
        bindingCampanha = CardCampanhaFinalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        bindingEvento = CardEventoFinalBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val viewHolder =
            CardCampanhaViewHolder(
                bindingCampanha
            )
        return viewHolder
    }

    override fun getItemCount(): Int {
        return listaCards.size
    }

    override fun onBindViewHolder(holder: CardCampanhaViewHolder, position: Int) {
        val currentItem: Campanha = listaCards.get(position)

        //seta imagem
        Picasso.get()
            .load(currentItem.imagemCampanha)
            .resize(110.dp, 110.dp)
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(holder.imagemCampanha)


//        fazer função que salve esse cara
        holder.buttonFavoritar.setOnClickListener {
            clickFavoritar(currentItem, holder.buttonFavoritar)
        }
        ajustaIconeFavorito(currentItem, holder.buttonFavoritar)
        holder.textViewTitulo.text = currentItem.titulo
        holder.textViewTimeStamp.text = DateToString(currentItem.timeStamp)
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

    private fun clickFavoritar(currentItem: Campanha, buttonFavoritar: ImageButton) {
        currentItem.favorito = !currentItem.favorito
        ajustaIconeFavorito(currentItem, buttonFavoritar)
    }

    private fun ajustaIconeFavorito(
        currentItem: Campanha,
        buttonFavoritar: ImageButton
    ) {
        if (currentItem.favorito) {
            buttonFavoritar.setImageDrawable(
                ContextCompat.getDrawable(
                    contexto,
                    R.drawable.ic_baseline_star_24
                )
            )
        } else {
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

                var filteredList: MutableList<Campanha> = mutableListOf()
                if (charSequence.toString().isEmpty()) {
                    filteredList = listaCardsAll.toMutableList()
                } else {
                    for (campanha in listaCardsAll) {

                        if (campanha.titulo.toLowerCase(Locale.getDefault())
                                .contains(charSequence.toString().toLowerCase(Locale.getDefault()))
                        ) {
                            filteredList.add(campanha)
                        } else if (campanha.descricao.toLowerCase(Locale.getDefault())
                                .contains(charSequence.toString().toLowerCase(Locale.getDefault()))
                        ) {
                            filteredList.add(campanha)
                        } else {
                            for (item in campanha.listaDeItens) {
                                if (item.titulo.toLowerCase(Locale.getDefault()).contains(
                                        charSequence.toString().toLowerCase(Locale.getDefault())
                                    )
                                ) {
                                    filteredList.add(campanha)
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
                listaCards.addAll(filterResults!!.values as Collection<Campanha>)
                notifyDataSetChanged()
            }

        }
    }


}