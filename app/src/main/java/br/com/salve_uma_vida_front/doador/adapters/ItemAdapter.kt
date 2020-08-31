package br.com.salve_uma_vida_front.doador.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.both.models.ItemCampanha

class ItemAdapter(var listaItens: List<ItemCampanha>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titulo: TextView = itemView.findViewById(R.id.itemDoadorTitulo)
        var progresso: TextView = itemView.findViewById(R.id.itemDoadorProgresso)
        var progressBar: ProgressBar = itemView.findViewById(R.id.itemDoadorProgressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_campanha_pesquisa, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaItens.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem: ItemCampanha = listaItens.get(position)

        holder.titulo.text = currentItem.titulo
        holder.progresso.text =
            "Arrecadado ${currentItem.quantidadeAtual} / ${currentItem.quantidadeMaxima} ${currentItem.unidadeMedida}"
        holder.progressBar.max = currentItem.quantidadeMaxima
        holder.progressBar.progress = currentItem.quantidadeAtual
    }
}