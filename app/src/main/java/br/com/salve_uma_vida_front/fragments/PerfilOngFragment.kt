package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.adapters.TabPerfilOngAdapter
import br.com.salve_uma_vida_front.databinding.FragmentPerfilOngBinding
import br.com.salve_uma_vida_front.toolbarVazia
import com.google.android.material.tabs.TabLayout

class PerfilOngFragment : Fragment() {

    lateinit var binding: FragmentPerfilOngBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilOngBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var idByArgs = getIdByArgs()
        configuraViewPager()
        configuraTabLayout()
        configuraToolbar()
    }

    private fun configuraViewPager() {
        val viewPagerAdapter =
            TabPerfilOngAdapter(parentFragmentManager, binding.ongPerfilFragmentTabLayout.tabCount)
        binding.ongPerfilFragmentTabLayout.setupWithViewPager(binding.ongPerfilFragmentViewPager);
        binding.ongPerfilFragmentViewPager.adapter = viewPagerAdapter
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