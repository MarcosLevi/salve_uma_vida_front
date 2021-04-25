package br.com.salve_uma_vida_front.viewholders

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R

class ItemCardCampanhaFinalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var titulo: TextView = itemView.findViewById(R.id.itemDoadorTitulo)
    var progresso: TextView = itemView.findViewById(R.id.itemDoadorProgresso)
    var progressBar: ProgressBar = itemView.findViewById(R.id.itemDoadorProgressBar)
}
