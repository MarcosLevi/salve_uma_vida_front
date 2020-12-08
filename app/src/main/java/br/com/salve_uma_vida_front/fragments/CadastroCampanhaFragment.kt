package br.com.salve_uma_vida_front.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.DateToString
import br.com.salve_uma_vida_front.models.DialogEditaItem
import br.com.salve_uma_vida_front.models.ItemCampanha
import br.com.salve_uma_vida_front.adapters.ItemAdapterOng
import br.com.salve_uma_vida_front.databinding.FragmentCadastroCampanhaBinding
import br.com.salve_uma_vida_front.models.DialogEditaItemNew
import java.util.*

class CadastroCampanhaFragment : Fragment(),ItemAdapterOng.ItemListener, DialogEditaItemNew.DialogEditaItemListener {
    var navController: NavController? = null

    lateinit var binding: FragmentCadastroCampanhaBinding

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapterOng: RecyclerView.Adapter<ItemAdapterOng.ItemViewHolder>
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
        binding = FragmentCadastroCampanhaBinding.inflate(inflater, container, false)
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
            val dialogEditaItemNew = DialogEditaItemNew(this)
            dialogEditaItemNew.show(parentFragmentManager, "Dialog edita item")
        }

        val finalizaCampanha = binding.cadastroCampanhaFinalizar
        finalizaCampanha.setOnClickListener {  }

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
                    dataCampanha.setText(
                        DateToString(
                            calendar
                        )
                    )
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
        mAdapterOng = ItemAdapterOng(
            itensCampanha,
            this
        )
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapterOng
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

    override fun onEditaClicked(itemCampanha: ItemCampanha) {
        DialogEditaItem(requireContext(),itemCampanha,itensCampanha,mAdapterOng,binding.cadastroCampanhaQuantidadeDeItens)
    }

    override fun onRemoveClicked(itemCampanha: ItemCampanha) {
        itensCampanha.remove(itemCampanha)
        notificaMudancaAdapter()
        atualizaQuantidadeDeItens()
    }

    private fun notificaMudancaAdapter() {
        mRecyclerView.adapter!!.notifyDataSetChanged()
    }

    override fun passaItem(item: ItemCampanha) {
        itensCampanha.add(item)
        atualizaQuantidadeDeItens()
    }

}