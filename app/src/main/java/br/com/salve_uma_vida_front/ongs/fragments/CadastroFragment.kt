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
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.both.DateToString
import br.com.salve_uma_vida_front.both.NewCalendar
import br.com.salve_uma_vida_front.both.StringToDate
import br.com.salve_uma_vida_front.both.models.DialogEditaItem
import br.com.salve_uma_vida_front.both.models.ItemCampanha
import br.com.salve_uma_vida_front.databinding.FragmentCadastroBinding
import br.com.salve_uma_vida_front.ongs.adapters.ItemAdapter
import java.util.*

class CadastroFragment : Fragment(), View.OnClickListener, ItemAdapter.ItemListener {
    var navController: NavController? = null

    lateinit var binding: FragmentCadastroBinding

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    val itensCampanha: MutableList<ItemCampanha> = mutableListOf(
        ItemCampanha(
            "Ração",
            "Kg",
            100
        ),
        ItemCampanha(
            "Leite",
            "L",
            90
        ),
        ItemCampanha(
            "Coleira",
            "Un",
            500
        ),
        ItemCampanha(
            "Água",
            "L",
            600
        ),
        ItemCampanha(
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
            val teste = ItemCampanha()
            DialogEditaItem(requireContext(),teste,itensCampanha,mAdapter,binding.cadastroCampanhaQuantidadeDeItens,false)
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
                    dataCampanha.setText(DateToString(calendar))
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
        mAdapter = ItemAdapter(itensCampanha, this)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter
    }

    private fun atualizaQuantidadeDeItens() {
        val quantidade = itensCampanha.size
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
                val diaMesAno = StringToDate(binding.cadastroCampanhaData.text.toString())
//                addCampanhaNaOng(
//                    //Quando fazer login da ong, aqui vai o nome dela
//                    "São Camilo",
//                    binding.cadastroCampanhaTitulo.text.toString(),
//                    NewCalendar(diaMesAno.get(0),diaMesAno.get(1),diaMesAno.get(2)),
//                    binding.cadastroCampanhaDescricao.text.toString(),
//                    itensCampanha
//                )
                notificaMudancaAdapter()
                navController!!.navigate(R.id.action_cadastroFragment_to_ongCampanhasFragment)
            }
        }
    }

    override fun onEditaClicked(itemCampanha: ItemCampanha) {
        DialogEditaItem(requireContext(),itemCampanha,itensCampanha,mAdapter,binding.cadastroCampanhaQuantidadeDeItens)
    }

    override fun onRemoveClicked(itemCampanha: ItemCampanha) {
        itensCampanha.remove(itemCampanha)
        notificaMudancaAdapter()
        atualizaQuantidadeDeItens()
    }

    private fun notificaMudancaAdapter() {
        mRecyclerView.adapter!!.notifyDataSetChanged()
    }
}