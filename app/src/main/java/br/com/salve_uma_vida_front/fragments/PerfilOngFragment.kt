package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.salve_uma_vida_front.adapters.TabPerfilOngAdapter
import br.com.salve_uma_vida_front.databinding.FragmentPerfilOngBinding
import br.com.salve_uma_vida_front.databinding.FragmentPerfilOngCampanhasBinding
import com.google.android.material.tabs.TabLayout

class PerfilOngFragment : Fragment() {

    lateinit var binding: FragmentPerfilOngBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewPagerAdapter = TabPerfilOngAdapter(parentFragmentManager,binding.ongPerfilFragmentTabLayout.tabCount)
        binding.ongPerfilFragmentViewPager.adapter = viewPagerAdapter
        binding.ongPerfilFragmentTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    binding.ongPerfilFragmentViewPager.setCurrentItem(tab.position)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerfilOngBinding.inflate(inflater, container, false)
        return binding.root
    }
}