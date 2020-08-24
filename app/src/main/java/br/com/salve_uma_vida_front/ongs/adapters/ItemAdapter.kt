package br.com.salve_uma_vida_front.ongs.adapters

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.both.models.ItemCard


class ItemAdapter(var listaItens: MutableList<ItemCard>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder : RecyclerView.ViewHolder {
        var titulo: TextView
        var progresso: TextView
        var progressBar: ProgressBar
        var editar: ImageButton
        var excluir: ImageButton

        constructor(itemView: View) : super(itemView) {
            titulo = itemView.findViewById(R.id.itemOngTitulo)
            progresso = itemView.findViewById(R.id.itemOngProgresso)
            progressBar = itemView.findViewById(R.id.itemOngProgressBar)
            editar = itemView.findViewById(R.id.itemOngEdita)
            excluir = itemView.findViewById(R.id.itemOngExclui)
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

    fun mudouAlgoNoAdapter() {
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem: ItemCard = listaItens.get(position)
        configuraHolder(holder, currentItem)
        holder.editar.setOnClickListener {
            Toast.makeText(
                it.context,
                "Apertou no editar do ${currentItem.titulo}",
                Toast.LENGTH_SHORT
            ).show()
            showPopUp(it, currentItem)
        }
        holder.excluir.setOnClickListener{
            Toast.makeText(
                it.context,
                "Apertou no excluir do ${currentItem.titulo}",
                Toast.LENGTH_SHORT
            ).show()
            listaItens.removeAt(position)
            mudouAlgoNoAdapter()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun configuraHolder(
        holder: ItemViewHolder,
        currentItem: ItemCard
    ) {
        holder.titulo.text = currentItem.titulo
        holder.progresso.text =
            "Arrecadado ${currentItem.quantidadeAtual} / ${currentItem.quantidadeMaxima} ${currentItem.unidadeMedida}"
        holder.progressBar.max = currentItem.quantidadeMaxima
        holder.progressBar.progress = currentItem.quantidadeAtual
    }

    fun showPopUp(view: View, currentItem: ItemCard) {
        val myDialog: Dialog = Dialog(view.context)
        myDialog.setContentView(R.layout.item_cadastro_campanha)

        val campoTitulo = myDialog.findViewById<EditText>(R.id.cadastroCampanhaEditTextNomeItem)
        campoTitulo.setText(currentItem.titulo)

        val campoQuantidade = myDialog.findViewById<EditText>(R.id.cadastroCampanhaEditTextQuantidadeItem)
        campoQuantidade.setText("${currentItem.quantidadeMaxima}")

        val campoUnidade = myDialog.findViewById<Spinner>(R.id.cadastroCampanhaSpinnerUnidadesMedida)
        val adapter = ArrayAdapter.createFromResource(view.context, R.array.unidades_medida, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        campoUnidade.setAdapter(adapter)
        val spinnerPosition = adapter.getPosition(currentItem.unidadeMedida)
        campoUnidade.setSelection(spinnerPosition)

        val close = myDialog.findViewById<ImageButton>(R.id.cadastroCampanhaClose)
        close.setOnClickListener {
            myDialog.dismiss()
        }
        val finaliza = myDialog.findViewById<ImageButton>(R.id.cadastroCampanhaSubmitNovoItem)
        finaliza.setOnClickListener{
            currentItem.titulo = campoTitulo.text.toString()
            currentItem.unidadeMedida = campoUnidade.selectedItem.toString()
            currentItem.quantidadeMaxima = campoQuantidade.text.toString().toInt()
            myDialog.dismiss()
            mudouAlgoNoAdapter()
        }
        myDialog.show()

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(myDialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        myDialog.window!!.attributes = layoutParams
    }
}