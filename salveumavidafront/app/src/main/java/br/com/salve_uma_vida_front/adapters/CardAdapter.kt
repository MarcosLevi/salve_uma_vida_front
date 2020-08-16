package br.com.salve_uma_vida_front.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.models.CardDoador
import com.squareup.picasso.Picasso

class CardAdapter(var listaCards: List<CardDoador>, var context: Context) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager


    class CardViewHolder : RecyclerView.ViewHolder {

        var imagemCampanha: ImageView
        var buttonFavoritar: ImageButton
        var textViewTitulo: TextView
        var textViewTimeStamp: TextView
        var textViewDescricao: TextView
        var textViewQuantidadeItens: TextView
        var itensCampanha: RecyclerView

        constructor(cardView: View) : super(cardView) {
            imagemCampanha = cardView.findViewById(R.id.cardImagemCampanha)
            buttonFavoritar = cardView.findViewById(R.id.cardFavoritar)
            textViewTitulo = cardView.findViewById(R.id.cardTitulo)
            textViewTimeStamp = cardView.findViewById(R.id.cardTimeStamp)
            textViewDescricao = cardView.findViewById(R.id.cardDescricao)
            textViewQuantidadeItens = cardView.findViewById(R.id.cardQuantidadeItens)
            itensCampanha = cardView.findViewById(R.id.itensCampanha)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        var view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_campanha_visao_doador, parent, false)
        var viewHolder = CardAdapter.CardViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return listaCards.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        var currentItem: CardDoador = listaCards.get(position)

        //mudar pra pegar imagem pra url

        Picasso.get()
            .load(currentItem.imagem)
            .resize(110.dp, 110.dp)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .into(holder.imagemCampanha);


//        holder.imagemCampanha.setImageResource(R.drawable.ic_launcher_foreground)
//        fazer função que salve esse cara
//        holder.buttonFavoritar
        holder.textViewTitulo.text = currentItem.titulo
        holder.textViewTimeStamp.text = currentItem.timeStamp
        holder.textViewDescricao.text = currentItem.descricao
        holder.textViewQuantidadeItens.text = quantidadeDeItensString(currentItem.quantidadeDeItens)

        mRecyclerView = holder.itensCampanha
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(context)
        mAdapter = ItemAdapter(currentItem.listaDeItens)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter
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
}