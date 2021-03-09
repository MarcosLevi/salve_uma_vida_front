package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.adapters.TabPerfilOngAdapter
import br.com.salve_uma_vida_front.closeLoading
import br.com.salve_uma_vida_front.databinding.FragmentPerfilOngBinding
import br.com.salve_uma_vida_front.dto.OngFavoritaDto
import br.com.salve_uma_vida_front.dto.UserDto
import br.com.salve_uma_vida_front.startLoading
import br.com.salve_uma_vida_front.toolbarVazia
import br.com.salve_uma_vida_front.viewmodels.UserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class PerfilOngFragment : Fragment() {

    private lateinit var toolbar: Toolbar
    lateinit var binding: FragmentPerfilOngBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var ong: UserDto
    private val titulos = mutableListOf("Info","Campanhas","Eventos","Galeria")
    private var idOng: Int? = null
    private var isFavorita = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilOngBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        idOng=getIdByArgs()
        startLoading(requireActivity(), R.id.ongLoading)
        configuraObservers()
        getOngsFavoritas()
        carregaPerfilDoUserPeloId()
        configuraTabLayout()
        configuraToolbar()
        return binding.root
    }

    private fun carregaPerfilDoUserPeloId() {
        idOng?.let { viewModel.getUserById(it) }
    }

    private fun getOngsFavoritas(){
        viewModel.getOngsFavororitasDoUserLogado()
    }

    private fun favoritaOng(){
        idOng?.let { viewModel.favoritarOngPorId(it) }
    }

    private fun desfavoritaOng(){
        idOng?.let { viewModel.desfavoritarOngPorId(it) }
    }

    private fun configuraObservers() {
        viewModel.findUserById.observe(viewLifecycleOwner, Observer {
            ong = it
            configuraInformacoesUser()
            configuraViewPager()
        })
        viewModel.respostaDoBancoAoFavoritar.observe(viewLifecycleOwner, Observer {
            isFavorita = true
            toolbar.menu.getItem(1).icon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_star_24)
        })
        viewModel.respostaDoBancoAoDesfavoritar.observe(viewLifecycleOwner, Observer {
            if (it){
                isFavorita = false
                toolbar.menu.getItem(1).icon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_star_border_24)
            }
        })
        viewModel.ongsFavoritasDoUserLogado.observe(viewLifecycleOwner, Observer {
            for (ongFavorita: OngFavoritaDto in it){
                if (ongFavorita.id == idOng){
                    isFavorita = true
                    toolbar.menu.getItem(1).icon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_star_24)
                }
                else{
                    isFavorita = false
                    toolbar.menu.getItem(1).icon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_star_border_24)
                }
            }
            closeLoading(requireActivity(), R.id.ongLoading)
        })
    }

    private fun configuraInformacoesUser() {
        binding.ongPerfilNome.text = ong.name
        Picasso.get()
            .load(ong.image)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(binding.ongPerfilImagem)
    }

    private fun configuraViewPager() {
        val viewPagerAdapter =
            TabPerfilOngAdapter(binding.ongPerfilFragmentTabLayout.tabCount,ong, idOng!!, this)
        binding.ongPerfilFragmentViewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.ongPerfilFragmentTabLayout, binding.ongPerfilFragmentViewPager) { tab, position ->
            tab.text = titulos[position]
        }.attach()
    }

    private fun configuraTabLayout() {
        binding.ongPerfilFragmentTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.ongPerfilFragmentViewPager.setCurrentItem(tab.position)
                }
            }
        })
    }

    private fun configuraToolbar() {
        toolbar = toolbarVazia(activity)!!
        toolbar.inflateMenu(R.menu.fragment_perfil_ong_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.ongPerfilFragmentSendMessage -> {
                    Toast.makeText(requireContext(),"Função ainda não implementada", Toast.LENGTH_SHORT)
                    true
                }
                R.id.ongPerfilFragmentFavoritar ->{
                    if (isFavorita)
                        desfavoritaOng()
                    else
                        favoritaOng()
                    true
                }
                else -> {
                    throw IllegalArgumentException("Item inexistente")
                }
            }
        }
    }

    private fun getIdByArgs(): Int? {
        val args: PerfilOngFragmentArgs by navArgs()
        val idOng = args.idOng
        return idOng
    }
}