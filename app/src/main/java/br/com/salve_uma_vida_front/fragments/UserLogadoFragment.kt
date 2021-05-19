package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.adapters.TabPerfilAdapter
import br.com.salve_uma_vida_front.adapters.TabPerfilOngAdapter
import br.com.salve_uma_vida_front.closeLoading
import br.com.salve_uma_vida_front.databinding.FragmentPerfilUserLogadoBinding
import br.com.salve_uma_vida_front.dto.UserDto
import br.com.salve_uma_vida_front.models.UserType
import br.com.salve_uma_vida_front.startLoading
import br.com.salve_uma_vida_front.toolbarVazia
import br.com.salve_uma_vida_front.viewmodels.UserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso


class UserLogadoFragment : Fragment() {
    private lateinit var toolbar: Toolbar
    lateinit var binding: FragmentPerfilUserLogadoBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var user: UserDto
    private val titulos = mutableListOf("Info", "Campanhas", "Eventos", "Galeria")
    var navController: NavController? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilUserLogadoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        startLoading(requireActivity(), R.id.ongLoading)
        configuraToolbar()
        configuraObservers()
        getOngsFavoritas()
        carregaPerfilDoUserPeloId()
        configuraTabLayout()
        return binding.root
    }

    private fun carregaPerfilDoUserPeloId() {
        viewModel.getProfile()
    }

    private fun getOngsFavoritas() {
        viewModel.getOngsFavororitasDoUserLogado()
    }

    private fun configuraObservers() {
        viewModel.profile.observe(viewLifecycleOwner, Observer {
            user = it
            configuraInformacoesUser()
            configuraViewPager()
            closeLoading(requireActivity(), R.id.ongLoading)
        })
    }

    private fun configuraInformacoesUser() {
        binding.perfilNome.text = user.name
        Picasso.get()
            .load(user.image)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(binding.perfilImagem)
    }

    private fun configuraViewPager() {
        var viewPagerAdapterNGO: TabPerfilOngAdapter? = null
        var viewPagerAdapterCOMMON: TabPerfilAdapter? = null
        if (UserType.COMMON == user.type){
            titulos.removeAll(mutableListOf("Campanhas", "Eventos"))
            viewPagerAdapterCOMMON = TabPerfilAdapter(titulos.size, user, this)
            binding.perfilFragmentViewPager.adapter = viewPagerAdapterCOMMON
        }
        else{
            viewPagerAdapterNGO = TabPerfilOngAdapter(titulos.size, user, 1, this)
            binding.perfilFragmentViewPager.adapter = viewPagerAdapterNGO
        }

        TabLayoutMediator(
            binding.perfilFragmentTabLayout,
            binding.perfilFragmentViewPager
        ) { tab, position ->
            tab.text = titulos[position]
        }.attach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    private fun configuraTabLayout() {
        binding.perfilFragmentTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.perfilFragmentViewPager.setCurrentItem(tab.position)
                }
            }
        })
    }

    private fun configuraToolbar() {
        toolbar = toolbarVazia(activity)!!
        toolbar.inflateMenu(R.menu.fragment_both_perfil_user_logado_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.bothPerfilUserLogadoEdita -> {
                    navController!!.navigate(UserLogadoFragmentDirections.actionBothUserLogadoFragmentToEditarUserFragment(
                        user
                    ))
                    true
                }
                else -> {
                    throw IllegalArgumentException("Item inexistente")
                }
            }
        }
    }
}
