package br.com.salve_uma_vida_front.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.DateToString
import br.com.salve_uma_vida_front.DateToStringBanco
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.adapters.ItemAdapterOng
import br.com.salve_uma_vida_front.databinding.FragmentCadastroCampanhaBinding
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.CampanhaItemDto
import br.com.salve_uma_vida_front.models.DialogEditaItemNew
import br.com.salve_uma_vida_front.toolbarVazia
import br.com.salve_uma_vida_front.viewholders.itemCadastroCampanhaViewHolder
import java.util.*

class CadastroCampanhaFragment : Fragment(), ItemAdapterOng.ItemListener,
    DialogEditaItemNew.DialogEditaItemListener {
    var navController: NavController? = null

    lateinit var binding: FragmentCadastroCampanhaBinding

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapterOng: RecyclerView.Adapter<itemCadastroCampanhaViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    private var campanha = CampanhaDto()
    private var isEdita = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCadastroCampanhaBinding.inflate(inflater, container, false)
        campanha.itens.add(CampanhaItemDto(descricao = "Ração", unidade = "Kg", maximo = 90F))
        campanha.itens.add(CampanhaItemDto(descricao = "Leite", unidade = "L", maximo = 90F))
        campanha.itens.add(CampanhaItemDto(descricao = "Coleira", unidade = "Un", maximo = 500F))
        campanha.itens.add(CampanhaItemDto(descricao = "Água", unidade = "L", maximo = 600F))
        campanha.itens.add(CampanhaItemDto(descricao = "Sabão", unidade = "L", maximo = 200F))
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val calendar = Calendar.getInstance()

        atualizaQuantidadeDeItens()

        configuraRecyclerView()

        //Pegar imagem do usuário atual
//        Picasso.get()
//            .load(currentItem.userImage)
//            .resize(110.dp, 110.dp)
//            .centerCrop()
//            .placeholder(R.drawable.ic_dafault_photo)
//            .error(R.drawable.ic_baseline_report_problem_24)
//            .into(binding.cadastroCampanhaImagem)


        val adicionaItem = binding.cadastroCampanhaAdicionaItem
        adicionaItem.setOnClickListener {
            val dialogEditaItemNew = DialogEditaItemNew(this)
            dialogEditaItemNew.show(parentFragmentManager, "Dialog edita item")
        }

        val finalizaCampanha = binding.cadastroCampanhaFinalizar
        finalizaCampanha.setOnClickListener {
//            if(validate()){
//
//            }
            campanha.descricao = binding.cadastroCampanhaDescricao.text.toString()
            campanha.titulo = binding.cadastroCampanhaTitulo.text.toString()
//            viewModel.cadastraCampanha()
        }

        val dataCampanha = binding.cadastroCampanhaData

        val labelDataCampanha = binding.cadastroCampanhaLabelData

        val escolheData = binding.cadastroCampanhaPickDate
        escolheData.setOnClickListener {
            val dia = calendar.get(Calendar.DAY_OF_MONTH)
            val mes = calendar.get(Calendar.MONTH)
            val ano = calendar.get(Calendar.YEAR)

            val dpd = DatePickerDialog(
                view.context,
                R.style.DialogTheme,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    calendar.set(year, monthOfYear, dayOfMonth)
                    labelDataCampanha.visibility = View.VISIBLE
                    dataCampanha.text = DateToString(
                        calendar
                    )
                    campanha.data = DateToStringBanco(calendar)
                },
                ano,
                mes,
                dia
            )
            dpd.show()

        }

        configuraToolbar()

    }

    private fun configuraToolbar() {
        val toolbar = toolbarVazia(activity)
        toolbar?.setBackgroundColor(resources.getColor(R.color.corCampanhas))
//        toolbar?.inflateMenu(R.menu.fragment_both_procurar_menu)
    }

    private fun configuraRecyclerView() {
        mRecyclerView = binding.cadastroCampanhaitensCampanhaOng
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(requireContext())
        mAdapterOng = ItemAdapterOng(
            this,
            campanha.itens
        )
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapterOng
    }

    private fun atualizaQuantidadeDeItens() {
        val quantidade = campanha.itens.size
        val quantidadeDeItens = binding.cadastroCampanhaQuantidadeDeItens
        if (quantidade > 1 || quantidade == 0) {
            quantidadeDeItens.text = "$quantidade itens"
        } else if (quantidade == 1) {
            quantidadeDeItens.text = "$quantidade item"
        }
    }

    override fun onEditaClicked(itemCampanha: CampanhaItemDto) {
        isEdita=true
        Log.d("CadastroCampanha", "Cliquei em editar")
        val dialogEditaItemNew = DialogEditaItemNew(this, itemCampanha)
        dialogEditaItemNew.show(parentFragmentManager, "Dialog edita item")

    }

    override fun onRemoveClicked(itemCampanha: CampanhaItemDto) {
        Log.d("CadastroCampanha", "Cliquei em Excluir")
        campanha.itens.remove(itemCampanha)
        notificaMudancaAdapter()
        atualizaQuantidadeDeItens()
    }

    private fun notificaMudancaAdapter() {
        mRecyclerView.adapter!!.notifyDataSetChanged()
    }

    override fun passaItem(item: CampanhaItemDto) {
        if (!isEdita){
            campanha.itens.add(item)
            atualizaQuantidadeDeItens()
        }else{
            isEdita=false
        }

        notificaMudancaAdapter()
    }

    override fun onClose() {
        isEdita=false
    }

}