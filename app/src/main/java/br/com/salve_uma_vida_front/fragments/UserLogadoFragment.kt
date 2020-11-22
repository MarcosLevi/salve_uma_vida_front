package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.viewmodels.UserViewModel
import br.com.salve_uma_vida_front.hideKeyboard
import br.com.salve_uma_vida_front.models.LoadingDialog
import br.com.salve_uma_vida_front.models.Responses
import br.com.salve_uma_vida_front.databinding.FragmentPerfilUserLogadoBinding


class UserLogadoFragment : Fragment() {
    var navController: NavController? = null
    lateinit var binding: FragmentPerfilUserLogadoBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var myMenu: Menu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerfilUserLogadoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        setHasOptionsMenu(true)
        configuraObservers()
    }

    fun startLoading() {
        val loadingDialog = LoadingDialog()
        loadingDialog.show(parentFragmentManager, "Loading")
    }

    fun closeLoading() {
        val transaction = parentFragmentManager.beginTransaction()
        val loadingDialog = parentFragmentManager.findFragmentByTag("Loading") as LoadingDialog
        loadingDialog.dismiss()
        transaction.remove(loadingDialog)
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
        constraintSet.connect(binding.userLogadoLabelDetalhes.id,ConstraintSet.TOP,binding.userLogadoNomeEditText.id,ConstraintSet.BOTTOM,0)
        constraintSet.connect(binding.userLogadoLabelEndereco.id,ConstraintSet.TOP,binding.userLogadoDetalhesEditText.id,ConstraintSet.BOTTOM,0)
        constraintSet.applyTo(constraintLayout)
    }

    fun atualizar(){
        startLoading()
        viewModel.atualizar()
    }

    private fun configuraObservers() {
        viewModel.atualiza.observe(viewLifecycleOwner, Observer {
            closeLoading()
            when(it){
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
        constraintSet.connect(binding.userLogadoLabelDetalhes.id,ConstraintSet.TOP,binding.userLogadoNome.id,ConstraintSet.BOTTOM,0)
        constraintSet.connect(binding.userLogadoLabelEndereco.id,ConstraintSet.TOP,binding.userLogadoDetalhes.id,ConstraintSet.BOTTOM,0)
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