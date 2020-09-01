package br.com.salve_uma_vida_front.both.models

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.ongs.adapters.ItemAdapter

class DialogEditaItem(
    context: Context,
    currentItem: ItemCampanha,
    itensCampanha: MutableList<ItemCampanha>,
    mAdapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>,
    quantidadeDeItens: TextView,
    editable: Boolean = true
) : Dialog(context) {
    init {
        showPopUp(currentItem, editable,itensCampanha,mAdapter,quantidadeDeItens)
    }

    private fun atualizaQuantidadeDeItens(itensCampanha: MutableList<ItemCampanha>, quantidadeDeItens: TextView) {
        val quantidade = itensCampanha.size
        if (quantidade > 1 || quantidade == 0) {
            quantidadeDeItens.text = "$quantidade itens"
        } else if (quantidade == 1) {
            quantidadeDeItens.text = "$quantidade item"
        }
    }

    fun showPopUp(
        currentItem: ItemCampanha,
        editable: Boolean = true,
        itensCampanha: MutableList<ItemCampanha>,
        mAdapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>,
        quantidadeDeItens: TextView
    ) {
        val myDialog = Dialog(context)
        myDialog.setContentView(R.layout.item_cadastro_campanha)

        val campoTitulo = myDialog.findViewById<EditText>(R.id.cadastroCampanhaEditTextNomeItem)
        campoTitulo.setText(currentItem.titulo)

        val campoQuantidade =
            myDialog.findViewById<EditText>(R.id.cadastroCampanhaEditTextQuantidadeItem)
        campoQuantidade.setText("${currentItem.quantidadeMaxima}")

        val campoUnidade =
            myDialog.findViewById<Spinner>(R.id.cadastroCampanhaSpinnerUnidadesMedida)
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.unidades_medida,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        campoUnidade.setAdapter(adapter)
        val spinnerPosition = adapter.getPosition(currentItem.unidadeMedida)
        campoUnidade.setSelection(spinnerPosition)

        val close = myDialog.findViewById<ImageButton>(R.id.cadastroCampanhaClose)
        close.setOnClickListener {
            myDialog.dismiss()
        }
        val finaliza = myDialog.findViewById<ImageButton>(R.id.cadastroCampanhaSubmitNovoItem)
        finaliza.setOnClickListener {
            currentItem.titulo = campoTitulo.text.toString()
            currentItem.unidadeMedida = campoUnidade.selectedItem.toString()
            currentItem.quantidadeMaxima = campoQuantidade.text.toString().toInt()
            if (!editable) {
                itensCampanha.add(currentItem)
                atualizaQuantidadeDeItens(itensCampanha,quantidadeDeItens)
            }
            myDialog.dismiss()
            mAdapter.notifyDataSetChanged()
        }
        myDialog.show()

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(myDialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        myDialog.window!!.attributes = layoutParams
    }
}