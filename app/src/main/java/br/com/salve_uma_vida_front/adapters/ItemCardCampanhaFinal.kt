package br.com.salve_uma_vida_front.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.databinding.ItemCardCampanhaPesquisaBinding
import br.com.salve_uma_vida_front.dto.CampanhaItemDto
import br.com.salve_uma_vida_front.viewholders.ItemCardCampanhaFinalViewHolder

class ItemCardCampanhaFinal(var listaItens: List<CampanhaItemDto>) :
    RecyclerView.Adapter<ItemCardCampanhaFinalViewHolder>() {
    lateinit var binding: ItemCardCampanhaPesquisaBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCardCampanhaFinalViewHolder {
        binding = ItemCardCampanhaPesquisaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemCardCampanhaFinalViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return listaItens.size
    }

    override fun onBindViewHolder(holderCardCampanhaFinal: ItemCardCampanhaFinalViewHolder, position: Int) {
        val currentItem: CampanhaItemDto = listaItens.get(position)

        holderCardCampanhaFinal.titulo.text = currentItem.descricao
        holderCardCampanhaFinal.progresso.text =
            "Arrecadado ${currentItem.progresso} / ${currentItem.maximo} ${currentItem.unidade}"
        holderCardCampanhaFinal.progressBar.max = currentItem.maximo!!.toInt()
        holderCardCampanhaFinal.progressBar.progress = currentItem.progresso.toInt()
    }
}