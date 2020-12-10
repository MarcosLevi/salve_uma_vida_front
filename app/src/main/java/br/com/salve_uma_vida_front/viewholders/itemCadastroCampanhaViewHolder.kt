package br.com.salve_uma_vida_front.viewholders

import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.databinding.ItemCardCampanhaOngCadastroBinding

class itemCadastroCampanhaViewHolder(binding: ItemCardCampanhaOngCadastroBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var descricao: TextView = binding.itemOngTitulo
    var meta: TextView = binding.itemOngProgresso
    var edita: ImageButton = binding.itemOngEdita
    var excluir: ImageButton = binding.itemOngExclui
}
