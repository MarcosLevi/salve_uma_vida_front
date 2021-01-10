package br.com.salve_uma_vida_front.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.*
import br.com.salve_uma_vida_front.adapters.ItemAdapterOng
import br.com.salve_uma_vida_front.databinding.FragmentCadastroCampanhaBinding
import br.com.salve_uma_vida_front.dto.CampanhaDto
import br.com.salve_uma_vida_front.dto.CampanhaItemDto
import br.com.salve_uma_vida_front.models.DialogEditaItemNew
import br.com.salve_uma_vida_front.models.Erros
import br.com.salve_uma_vida_front.viewholders.itemCadastroCampanhaViewHolder
import br.com.salve_uma_vida_front.viewmodels.CampanhasViewModel
import com.squareup.picasso.Picasso
import androidx.lifecycle.Observer
import java.util.*

class CadastroCampanhaFragment : Fragment(), ItemAdapterOng.ItemListener,
    DialogEditaItemNew.DialogEditaItemListener {
    var navController: NavController? = null

    lateinit var binding: FragmentCadastroCampanhaBinding
    private lateinit var viewModel: CampanhasViewModel

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapterOng: RecyclerView.Adapter<itemCadastroCampanhaViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    private var campanha = CampanhaDto()
    private var erros = Erros()
    private var isEdita = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCadastroCampanhaBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(CampanhasViewModel::class.java)
        campanha.itens.add(CampanhaItemDto(descricao = "Ração", unidade = "KG", maximo = 90F))
        campanha.itens.add(CampanhaItemDto(descricao = "Leite", unidade = "L", maximo = 90F))
        campanha.itens.add(CampanhaItemDto(descricao = "Coleira", unidade = "UN", maximo = 500F))
        campanha.itens.add(CampanhaItemDto(descricao = "Água", unidade = "L", maximo = 600F))
        campanha.itens.add(CampanhaItemDto(descricao = "Sabão", unidade = "L", maximo = 200F))
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

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
        configuraImagemCampanha()


        configuraAdicionarItem()

        configuraFinalizarCampanha()

        configuraDatePicker(view)

        configuraToolbar()

        configuraObservers()

    }

    private fun configuraDatePicker(view: View) {
        val dataCampanha = binding.cadastroCampanhaData
        val calendar = Calendar.getInstance()
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
                    dataCampanha.setText(DateToString(calendar))
                    campanha.data = DateToStringBanco(calendar)
                },
                ano,
                mes,
                dia
            )
            dpd.show()
        }
    }

    private fun configuraFinalizarCampanha() {
        val finalizaCampanha = binding.cadastroCampanhaFinalizar
        finalizaCampanha.setOnClickListener {
            campanha.descricao = binding.cadastroCampanhaDescricao.text.toString()
            campanha.titulo = binding.cadastroCampanhaTitulo.text.toString()
            resetaErros()
            if (validate()) {
                novaCampanha()
            } else {
                mostraErros()
            }
        }
    }

    private fun mostraErros() {
        binding.cadastroCampanhaErros.text = erros.toString()
        binding.cadastroCampanhaErros.visibility = View.VISIBLE
        binding.cadastroCampanhaScrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    private fun resetaErros() {
        binding.cadastroCampanhaErros.visibility = View.GONE
        erros.removeErros()
    }

    private fun novaCampanha() {
        viewModel.novaCampanha(campanha)
    }

    private fun configuraObservers() {
        viewModel.novaCampanha.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            navController!!.navigate(CadastroCampanhaFragmentDirections.actionCadastroCampanhaFragmentToOngCampanhasFragment())
        })
    }

    private fun validate(): Boolean {
        var validate = true
        if (TextUtils.isEmpty(campanha.titulo)) {
            erros.addErro("Título é necessário para fazer o cadastro da campanha")
            validate = false
        }
        if (TextUtils.isEmpty(campanha.data)) {
            erros.addErro("Data é necessária para fazer o cadastro da campanha")
            validate = false
        }
        if (TextUtils.isEmpty(campanha.descricao)) {
            erros.addErro("Descrição é necessária para fazer o cadastro da campanha")
            validate = false
        }
        if (campanha.itens.isEmpty()) {
            erros.addErro("Pelo menos 1 item é necessário para fazer o cadastro da campanha")
            validate = false
        }
        return validate
    }

    private fun configuraAdicionarItem() {
        val adicionaItem = binding.cadastroCampanhaAdicionaItem
        adicionaItem.setOnClickListener {
            val dialogEditaItemNew = DialogEditaItemNew(this)
            dialogEditaItemNew.show(parentFragmentManager, "Dialog edita item")
        }
    }

    private fun configuraImagemCampanha() {
        Picasso.get()
            .load("https://thumbs.dreamstime.com/b/default-avatar-profile-icon-vector-social-media-user-image-182145777.jpg")
            .resize(110.dp, 110.dp)
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(binding.cadastroCampanhaImagem)
//        campanha.userImage =
//            "https://thumbs.dreamstime.com/b/default-avatar-profile-icon-vector-social-media-user-image-182145777.jpg"
    }

    private fun configuraToolbar() {
        val toolbar = toolbarVazia(activity)
        toolbar?.setBackgroundColor(resources.getColor(R.color.corCampanhas))
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
        isEdita = true
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
        if (!isEdita) {
            campanha.itens.add(item)
            atualizaQuantidadeDeItens()
        } else {
            isEdita = false
        }
        notificaMudancaAdapter()
    }

    override fun onClose() {
        isEdita = false
    }

}