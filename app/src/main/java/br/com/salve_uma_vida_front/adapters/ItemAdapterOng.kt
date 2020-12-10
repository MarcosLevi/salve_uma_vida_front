package br.com.salve_uma_vida_front.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.databinding.ItemCardCampanhaOngCadastroBinding
import br.com.salve_uma_vida_front.dto.CampanhaItemDto
import br.com.salve_uma_vida_front.viewholders.itemCadastroCampanhaViewHolder


class ItemAdapterOng(
    private val editaItemListener: ItemListener,
    var listaItens: MutableList<CampanhaItemDto>
) :
    RecyclerView.Adapter<itemCadastroCampanhaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemCadastroCampanhaViewHolder {
        val view = ItemCardCampanhaOngCadastroBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return itemCadastroCampanhaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaItens.size
    }

    interface ItemListener {
        fun onEditaClicked(itemCampanha: CampanhaItemDto)
        fun onRemoveClicked(itemCampanha: CampanhaItemDto)
    }

    override fun onBindViewHolder(holderCampanha: itemCadastroCampanhaViewHolder, position: Int) {
        val currentItem: CampanhaItemDto = listaItens[position]
        holderCampanha.descricao.text = currentItem.descricao
        holderCampanha.meta.text = "${currentItem.maximo} ${currentItem.unidade} "
        holderCampanha.edita.setOnClickListener {
            editaItemListener.onEditaClicked(currentItem)
        }
        holderCampanha.excluir.setOnClickListener {
            editaItemListener.onRemoveClicked(currentItem)
        }
    }
}