package br.com.salve_uma_vida_front.ongs.adapters

import android.app.Dialog
import android.view.*
import android.widget.*
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.both.models.ItemCard
import br.com.salve_uma_vida_front.databinding.ItemCardCampanhaVisaoOngBinding


class ItemAdapter(var listaItens: MutableList<ItemCard>, val listener: ItemListener) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(
        private val binding: ItemCardCampanhaVisaoOngBinding,
        private val listener: ItemListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun configuraHolder(currentItem: ItemCard) {
            binding.itemOngTitulo.text = currentItem.titulo
            binding.itemOngProgresso.text =
                "Arrecadado ${currentItem.quantidadeAtual} / ${currentItem.quantidadeMaxima} ${currentItem.unidadeMedida}"
            binding.itemOngProgressBar.max = currentItem.quantidadeMaxima
            binding.itemOngProgressBar.progress = currentItem.quantidadeAtual
            binding.listener = listener
            binding.itemCard = currentItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = ItemCardCampanhaVisaoOngBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return listaItens.size
    }

    interface ItemListener {
        fun onEditaClicked(itemCard: ItemCard)
        fun onRemoveClicked(itemCard: ItemCard)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem: ItemCard = listaItens.get(position)
        holder.configuraHolder(currentItem)
    }
}