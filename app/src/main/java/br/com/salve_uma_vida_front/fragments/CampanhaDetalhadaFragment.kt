package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.salve_uma_vida_front.*
import br.com.salve_uma_vida_front.adapters.ItemAdapterDoador
import br.com.salve_uma_vida_front.databinding.FragmentCampanhaDetalhadaBinding
import br.com.salve_uma_vida_front.dto.CampanhaDto
import com.squareup.picasso.Picasso


class CampanhaDetalhadaFragment : Fragment() {
    var navController: NavController? = null
    private lateinit var binding: FragmentCampanhaDetalhadaBinding
    private lateinit var campanha: CampanhaDto

    override fun onCreate(savedInstanceState: Bundle?) {
        campanha = getCampanhaByArgs()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCampanhaDetalhadaBinding.inflate(inflater, container, false)
        setaCamposCampanha()
        configuraRecyclerView()
        configuraToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = Navigation.findNavController(view)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setaCamposCampanha() {
        Picasso.get()
            .load(campanha.userImage)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(binding.campanhaDetalhadaImagem)
        binding.campanhaDetalhadaTitulo.text = campanha.titulo
        binding.campanhaDetalhadaData.text = "Anunciado em ${FormatStringToDate(
            campanha.data
        )}"
        binding.campanhaDetalhadaDescricao.text = campanha.descricao
        binding.campanhaDetalhadaQuantidadeItens.text = quantidadeDeItensString(campanha.itens.size)
    }

    private fun configuraRecyclerView() {
        val mRecyclerView = binding.campanhaDetalhadaItens
        mRecyclerView.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(requireContext())
        val mAdapterDoador =
            ItemAdapterDoador(campanha.itens)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapterDoador
    }

    private fun getCampanhaByArgs(): CampanhaDto {
        val args: CampanhaDetalhadaFragmentArgs by navArgs()
        val campanha = args.campanha
        return campanha
    }

    private fun configuraToolbar() {
        val toolbar = toolbarVazia(activity)
        toolbar?.inflateMenu(R.menu.fragment_campanha_ou_evento_detalhado_menu)
        toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.campanhaOuEventoDetalhadoFragmentVaiParaPerfilOng -> {
                    navController!!.navigate(
                        CampanhaDetalhadaFragmentDirections.actionCampanhaDetalhadaFragmentToPerfilOngFragment(
                            campanha.userId!!
                        )
                    )
                    true
                }
                else -> {
                    throw IllegalArgumentException("Item inexistente")
                }
            }
        }
    }

    fun quantidadeDeItensString(quantidade: Int): String {
        if (quantidade == 1) {
            return "1 item"
        } else if (quantidade > 1) {
            return "$quantidade itens"
        }
        return "0 itens"
    }
}