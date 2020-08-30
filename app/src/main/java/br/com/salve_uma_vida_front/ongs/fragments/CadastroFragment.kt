package br.com.salve_uma_vida_front.ongs.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.repository.addCardNaLista
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.both.models.ItemCard
import br.com.salve_uma_vida_front.databinding.FragmentCadastroBinding
import br.com.salve_uma_vida_front.ongs.adapters.ItemAdapter
import java.text.SimpleDateFormat
import java.util.*

class CadastroFragment : Fragment(), View.OnClickListener, ItemAdapter.ItemListener {
    var navController: NavController? = null

    lateinit var binding: FragmentCadastroBinding

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    val itensCard: MutableList<ItemCard> = mutableListOf(
        ItemCard(
            "Ração",
            "Kg",
            100
        ),
        ItemCard(
            "Leite",
            "L",
            90
        ),
        ItemCard(
            "Coleira",
            "Un",
            500
        ),
        ItemCard(
            "Água",
            "L",
            600
        ),
        ItemCard(
            "Sabão",
            "L",
            200
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCadastroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val calendar = Calendar.getInstance()

        atualizaQuantidadeDeItens()

        configuraRecyclerView()


        val adicionaItem = binding.cadastroCampanhaAdicionaItem
        adicionaItem.setOnClickListener {
            Toast.makeText(view.context, "Clicou no adiciona Item", Toast.LENGTH_SHORT).show()
            val teste = ItemCard()
            showPopUp(teste, false)
        }

        val finalizaCampanha = binding.cadastroCampanhaFinalizar
        finalizaCampanha.setOnClickListener (this)

        val dataCampanha = binding.cadastroCampanhaData

        val escolheData = binding.cadatroCampanhaPickDate
        escolheData.setOnClickListener {
            val dia = calendar.get(Calendar.DAY_OF_MONTH)
            val mes = calendar.get(Calendar.MONTH)
            val ano = calendar.get(Calendar.YEAR)

            val dpd = DatePickerDialog(
                view.context,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    calendar.set(year, monthOfYear, dayOfMonth)
                    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                    dataCampanha.setText("Ocorrerá em " + formatter.format(calendar.time))
                },
                ano,
                mes,
                dia
            )
            dpd.show()

        }

    }

    private fun configuraRecyclerView() {
        mRecyclerView = binding.cadastroCampanhaitensCampanhaOng
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(requireContext())
        mAdapter = ItemAdapter(itensCard, this)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter
    }

    private fun atualizaQuantidadeDeItens() {
        val quantidade = itensCard.size
        val quantidadeDeItens = binding.cadastroCampanhaQuantidadeDeItens
        if (quantidade > 1 || quantidade == 0) {
            quantidadeDeItens.text = "$quantidade itens"
        } else if (quantidade == 1) {
            quantidadeDeItens.text = "$quantidade item"
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.cadastroCampanhaFinalizar -> {
                Toast.makeText(requireContext(), "Clicou no finalizar campanha", Toast.LENGTH_SHORT).show()
                //salva no banco
                addCardNaLista(
                    binding.cadastroCampanhaTitulo.text.toString(),
                    binding.cadastroCampanhaData.text.toString(),
                    binding.cadastroCampanhaDescricao.text.toString(),
                    itensCard,
                    //colocar a url dps
                    "https://www.showmetech.com.br/wp-content/uploads//2020/08/143354-games-feature-sony-playstation-5-release-date-rumours-and-everything-you-need-to-know-about-ps5-image1-cvz3adase9-1024x683.jpg"
                )
                navController!!.navigate(R.id.action_cadastroFragment_to_ongCampanhasFragment)
            }
        }
    }

    override fun onEditaClicked(itemCard: ItemCard) {
        Toast.makeText(
            requireContext(),
            "Apertou no editar2 do ${itemCard.titulo}",
            Toast.LENGTH_SHORT
        ).show()
        showPopUp(itemCard)
    }

    override fun onRemoveClicked(itemCard: ItemCard) {
        Toast.makeText(
            requireContext(),
            "Apertou no excluir2 do ${itemCard.titulo}",
            Toast.LENGTH_SHORT
        ).show()
        itensCard.remove(itemCard)
        notificaMudancaAdapter()
        atualizaQuantidadeDeItens()
    }

    fun showPopUp(currentItem: ItemCard, editable: Boolean = true) {
        val myDialog: Dialog = Dialog(requireContext())
        myDialog.setContentView(R.layout.item_cadastro_campanha)

        val campoTitulo = myDialog.findViewById<EditText>(R.id.cadastroCampanhaEditTextNomeItem)
        campoTitulo.setText(currentItem.titulo)

        val campoQuantidade =
            myDialog.findViewById<EditText>(R.id.cadastroCampanhaEditTextQuantidadeItem)
        campoQuantidade.setText("${currentItem.quantidadeMaxima}")

        val campoUnidade =
            myDialog.findViewById<Spinner>(R.id.cadastroCampanhaSpinnerUnidadesMedida)
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
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
                itensCard.add(currentItem)
                atualizaQuantidadeDeItens()
            }
            myDialog.dismiss()
            notificaMudancaAdapter()
        }
        myDialog.show()

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(myDialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        myDialog.window!!.attributes = layoutParams
    }

    private fun notificaMudancaAdapter() {
        mRecyclerView.adapter!!.notifyDataSetChanged()
    }
}