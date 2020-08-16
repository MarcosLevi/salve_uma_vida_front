package br.com.salve_uma_vida_front.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.models.ItemCardDoador

class ItemAdapter(var listaItens: List<ItemCardDoador>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder : RecyclerView.ViewHolder {
        var titulo: TextView
        var progresso: TextView
        var progressBar: ProgressBar

        constructor(itemView: View) : super(itemView) {
            titulo = itemView.findViewById(R.id.itemTitulo)
            progresso = itemView.findViewById(R.id.itemProgresso)
            progressBar = itemView.findViewById(R.id.itemProgressBar)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        var view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_campanha_visao_doador, parent, false)
        var viewHolder = ItemViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return listaItens.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var currentItem: ItemCardDoador = listaItens.get(position)

        holder.titulo.text = currentItem.titulo
        holder.progresso.text = "Arrecadado ${currentItem.quantidadeAtual} / ${currentItem.quantidadeMaxima} ${currentItem.unidadeMedida}"
        holder.progressBar.max = currentItem.quantidadeMaxima
        holder.progressBar.progress = currentItem.quantidadeAtual
    }
}