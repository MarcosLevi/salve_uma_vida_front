package br.com.salve_uma_vida_front.ongs.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.both.models.ItemCard

class ItemAdapter(var listaItens: List<ItemCard>) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder : RecyclerView.ViewHolder {
        var titulo: TextView
        var progresso: TextView
        var progressBar: ProgressBar
        var imageButton: ImageButton

        constructor(itemView: View) : super(itemView) {
            titulo = itemView.findViewById(R.id.itemOngTitulo)
            progresso = itemView.findViewById(R.id.itemOngProgresso)
            progressBar = itemView.findViewById(R.id.itemOngProgressBar)
            imageButton = itemView.findViewById(R.id.itemOngEdita)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_campanha_visao_ong, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaItens.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem: ItemCard = listaItens.get(position)
        holder.titulo.text = currentItem.titulo
        holder.progresso.text = "Arrecadado ${currentItem.quantidadeAtual} / ${currentItem.quantidadeMaxima} ${currentItem.unidadeMedida}"
        holder.progressBar.max = currentItem.quantidadeMaxima
        holder.progressBar.progress = currentItem.quantidadeAtual
        holder.imageButton.setOnClickListener{
            Toast.makeText(it.context,"Apertou no editar do ${currentItem.titulo}", Toast.LENGTH_SHORT).show()
        }
    }
}