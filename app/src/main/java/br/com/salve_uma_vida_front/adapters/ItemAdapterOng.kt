package br.com.salve_uma_vida_front.adapters

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.models.ItemCampanha
import br.com.salve_uma_vida_front.databinding.ItemCardCampanhaOngCadastroBinding


class ItemAdapterOng(var listaItens: MutableList<ItemCampanha>, val listener: ItemListener) :
    RecyclerView.Adapter<ItemAdapterOng.ItemViewHolder>() {
    class ItemViewHolder(
        private val binding: ItemCardCampanhaOngCadastroBinding,
        private val listener: ItemListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun configuraHolder(currentItem: ItemCampanha) {
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
        val view = ItemCardCampanhaOngCadastroBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(
            view,
            listener
        )
    }

    override fun getItemCount(): Int {
        return listaItens.size
    }

    interface ItemListener {
        fun onEditaClicked(itemCampanha: ItemCampanha)
        fun onRemoveClicked(itemCampanha: ItemCampanha)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem: ItemCampanha = listaItens.get(position)
        holder.configuraHolder(currentItem)
    }
}