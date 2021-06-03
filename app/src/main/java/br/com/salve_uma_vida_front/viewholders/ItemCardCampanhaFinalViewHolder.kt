package br.com.salve_uma_vida_front.viewholders

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.databinding.ItemCardCampanhaPesquisaBinding

class ItemCardCampanhaFinalViewHolder(binding: ItemCardCampanhaPesquisaBinding) : RecyclerView.ViewHolder(binding.root) {
    var titulo: TextView = binding.itemDoadorTitulo
    var progresso: TextView = binding.itemDoadorProgresso
    var progressBar: ProgressBar = binding.itemDoadorProgressBar
}
