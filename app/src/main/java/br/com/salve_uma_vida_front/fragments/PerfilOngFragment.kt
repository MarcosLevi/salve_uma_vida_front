package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.adapters.TabPerfilOngAdapter
import br.com.salve_uma_vida_front.databinding.FragmentPerfilOngBinding
import br.com.salve_uma_vida_front.dto.UserDto
import br.com.salve_uma_vida_front.toolbarVazia
import br.com.salve_uma_vida_front.viewmodels.UserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class PerfilOngFragment : Fragment() {

    lateinit var binding: FragmentPerfilOngBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var user: UserDto
    private val titulos = mutableListOf("Info","Campanhas","Eventos","Galeria")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilOngBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        configuraObservers()
        carregaPerfilDoUserPeloId()
        configuraTabLayout()
        configuraToolbar()
        return binding.root
    }

    private fun carregaPerfilDoUserPeloId() {
        getIdByArgs()?.let { viewModel.getUserById(it) }
    }

    private fun configuraObservers() {
        viewModel.findUserById.observe(viewLifecycleOwner, Observer {
            user = it
            configuraInformacoesUser()
            configuraViewPager()
        })
    }

    private fun configuraInformacoesUser() {
        binding.ongPerfilNome.text = user.name
        Picasso.get()
            .load(user.image)
            .fit()
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(binding.ongPerfilImagem)
    }

    private fun configuraViewPager() {
        val viewPagerAdapter =
            TabPerfilOngAdapter(binding.ongPerfilFragmentTabLayout.tabCount,user, this)
//        binding.ongPerfilFragmentTabLayout.setupWithViewPager(binding.ongPerfilFragmentViewPager);
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
        val toolbar = toolbarVazia(activity)
        toolbar?.inflateMenu(R.menu.fragment_perfil_ong_menu)
    }

    private fun getIdByArgs(): Int? {
        val args: PerfilOngFragmentArgs by navArgs()
        val idOng = args.idOng
        return idOng
    }
}