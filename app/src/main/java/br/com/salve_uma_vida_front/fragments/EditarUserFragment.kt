package br.com.salve_uma_vida_front.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.databinding.FragmentEditarUserBinding
import br.com.salve_uma_vida_front.dto.UserDto
import br.com.salve_uma_vida_front.toolbarVazia
import br.com.salve_uma_vida_front.viewmodels.UserViewModel


class EditarUserFragment : Fragment() {
    var navController: NavController? = null
    lateinit var user: UserDto
    lateinit var binding: FragmentEditarUserBinding
    private lateinit var viewModelUser: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModelUser = ViewModelProviders.of(this).get(UserViewModel::class.java)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditarUserBinding.inflate(inflater, container, false)
        configuraToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setaUser()
        preencheCampos()
        navController = Navigation.findNavController(view)
    }

    private fun preencheCampos() {
        binding.perfilEditarDescricao.setText(user.detail)
        binding.perfilEditarNome.setText(user.name)
        binding.perfilEditarEmail.setText(user.email)
        binding.perfilEditarEndereco.setText(user.address)
    }

    private fun setaUser() {
        user = getUserByArgs()
    }

    private fun configuraToolbar() {
        val toolbar = toolbarVazia(activity)
        toolbar?.inflateMenu(R.menu.fragment_editar_user_menu)
        toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.editarUserFragmentCancelar -> {
                    true
                }

                R.id.editarUserFragmentFinalizar -> {
                    true
                }
                else -> {
                    throw IllegalArgumentException("Item inexistente")
                }
            }
        }
    }

    private fun getUserByArgs(): UserDto {
        val args: EditarUserFragmentArgs by navArgs()
        val user = args.user
        return user
    }


}