package br.com.salve_uma_vida_front.models

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.salve_uma_vida_front.adapters.GalleryAdapter
import br.com.salve_uma_vida_front.databinding.FragmentGalleryBinding

class DialogGallery(imageUrls: MutableList<String>) : DialogFragment() {
    private var imageUrls = imageUrls
    lateinit var binding: FragmentGalleryBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraViewPager(view)
        configuraBotaoFechar()
    }

    private fun configuraViewPager(view: View) {
        var viewPager = binding.fragmentGalleryViewPager
        var viewPagerAdapter = GalleryAdapter(view.context, imageUrls,parentFragmentManager,ScaleType.FULL_SIZE)
        viewPager.adapter = viewPagerAdapter
    }

    private fun configuraBotaoFechar() {
        binding.fragmentGalleryClose.bringToFront()
        binding.fragmentGalleryClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        dialog!!.getWindow()!!
            .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        super.onStart()
    }
}