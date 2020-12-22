package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.salve_uma_vida_front.*
import br.com.salve_uma_vida_front.adapters.GalleryAdapter
import br.com.salve_uma_vida_front.databinding.FragmentPerfilUserLogadoBinding
import br.com.salve_uma_vida_front.models.Responses
import br.com.salve_uma_vida_front.models.ScaleType
import br.com.salve_uma_vida_front.viewmodels.UserViewModel


class UserLogadoFragment : Fragment() {
    var navController: NavController? = null
    lateinit var binding: FragmentPerfilUserLogadoBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var myMenu: Menu

    private val imageUrls = mutableListOf(
        "https://istoe.com.br/wp-content/uploads/sites/17/2020/08/cachorro.jpg",
        "https://www.selecoes.com.br/wp-content/uploads/2018/08/brinquedos-para-cachorro-760x450.jpg",
        "https://super.abril.com.br/wp-content/uploads/2018/05/filhotes-de-cachorro-alcanc3a7am-o-c3a1pice-de-fofura-com-8-semanas1.png",
        "https://saude.abril.com.br/wp-content/uploads/2018/12/cachorro-livro.png",
        "https://s2.glbimg.com/slaVZgTF5Nz8RWqGrHRJf0H1PMQ=/0x0:800x450/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2019/U/e/NTegqdSe6SoBAoQDjKZA/cachorro.jpg"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerfilUserLogadoBinding.inflate(inflater, container, false)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setHasOptionsMenu(true)
        configuraObservers()
        configuraViewPager(view)
        configuraToolbar()
    }

    private fun configuraToolbar() {
        val toolbar = toolbarVazia(activity)
//        toolbar?.inflateMenu(R.menu.fragment_both_procurar_menu)
    }

    private fun configuraViewPager(view: View) {
        var viewPager = binding.userLogadoGallery
        var viewPagerAdapter = GalleryAdapter(view.context, imageUrls,parentFragmentManager,ScaleType.CROPPED)
        viewPager.adapter = viewPagerAdapter
    }

    fun ajusteConstraintsEdita() {
        binding.userLogadoNome.visibility = View.GONE
        binding.userLogadoDetalhes.visibility = View.GONE
        binding.userLogadoEndereco.visibility = View.GONE
        binding.userLogadoNomeEditText.visibility = View.VISIBLE
        binding.userLogadoDetalhesEditText.visibility = View.VISIBLE
        binding.userLogadoEnderecoEditText.visibility = View.VISIBLE
        binding.userLogadoNomeEditText.setText(binding.userLogadoNome.text)
        binding.userLogadoDetalhesEditText.setText(binding.userLogadoDetalhes.text)
        binding.userLogadoEnderecoEditText.setText(binding.userLogadoEndereco.text)
        val constraintLayout = binding.constraint
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            binding.userLogadoLabelDetalhes.id,
            ConstraintSet.TOP,
            binding.userLogadoNomeEditText.id,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.connect(
            binding.userLogadoLabelEndereco.id,
            ConstraintSet.TOP,
            binding.userLogadoDetalhesEditText.id,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.applyTo(constraintLayout)
    }

    fun atualizar() {
        startLoading(activity,R.id.ongLoading)
        userViewModel.atualizar()
    }

    private fun configuraObservers() {
        userViewModel.atualiza.observe(viewLifecycleOwner, Observer {
            closeLoading(activity,R.id.ongLoading)
            when (it) {
                Responses.SUCESS -> {
                    ajusteConstraintsNormal()
                }
                Responses.FAILED -> {
                    //aparece erro na tela
                }
            }
        })
    }

    fun ajusteConstraintsNormal() {
        binding.userLogadoNomeEditText.visibility = View.GONE
        binding.userLogadoDetalhesEditText.visibility = View.GONE
        binding.userLogadoEnderecoEditText.visibility = View.GONE
        binding.userLogadoNome.visibility = View.VISIBLE
        binding.userLogadoDetalhes.visibility = View.VISIBLE
        binding.userLogadoEndereco.visibility = View.VISIBLE
        binding.userLogadoNome.text = binding.userLogadoNomeEditText.text.toString()
        binding.userLogadoDetalhes.text = binding.userLogadoDetalhesEditText.text.toString()
        binding.userLogadoEndereco.text = binding.userLogadoEnderecoEditText.text.toString()
        val constraintLayout = binding.constraint
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            binding.userLogadoLabelDetalhes.id,
            ConstraintSet.TOP,
            binding.userLogadoNome.id,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.connect(
            binding.userLogadoLabelEndereco.id,
            ConstraintSet.TOP,
            binding.userLogadoDetalhes.id,
            ConstraintSet.BOTTOM,
            0
        )
        constraintSet.applyTo(constraintLayout)
        view?.hideKeyboard()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_both_perfil_user_logado_menu, menu)
        myMenu = menu
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bothPerfilUserLogadoEdita -> {
                item.isVisible = false
                myMenu.findItem(R.id.bothPerfilUserLogadoFinaliza).setVisible(true)
                ajusteConstraintsEdita()
            }
            R.id.bothPerfilUserLogadoFinaliza -> {
                item.isVisible = false
                myMenu.findItem(R.id.bothPerfilUserLogadoEdita).setVisible(true)
                //atualizar()
                ajusteConstraintsNormal()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}