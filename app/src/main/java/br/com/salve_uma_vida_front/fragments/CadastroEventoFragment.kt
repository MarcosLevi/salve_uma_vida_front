package br.com.salve_uma_vida_front.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.salve_uma_vida_front.*
import br.com.salve_uma_vida_front.databinding.FragmentCadastroEventoBinding
import br.com.salve_uma_vida_front.dto.EventoDto
import br.com.salve_uma_vida_front.models.DialogUrl
import br.com.salve_uma_vida_front.viewmodels.EventosViewModel
import com.squareup.picasso.Picasso
import java.util.*


class CadastroEventoFragment : Fragment(), DialogUrl.DialogUrlListener {
    var navController: NavController? = null

    lateinit var binding: FragmentCadastroEventoBinding
    private var evento = EventoDto()
    private lateinit var viewModel: EventosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCadastroEventoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(EventosViewModel::class.java)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        val calendar = Calendar.getInstance()

        val finalizaEvento = binding.cadastroEventoFinalizar
        finalizaEvento.setOnClickListener {
//            if(validate()){
//
//            }
            evento.descricao = binding.cadastroEventoDescricao.text.toString()
            evento.titulo = binding.cadastroEventoTitulo.text.toString()
            evento.endereco = binding.cadastroEventoEndereco.text.toString()
            evento.imagem = binding.cadastroEventoUrlImagem.text.toString()
            val (latitude, longitude, endereco) = adressToLatLong(
                evento.endereco!!,
                requireContext()
            )
            evento.latitude = latitude as Float
            evento.longitude = longitude as Float
            evento.endereco = endereco as String
            novoEvento()
        }

        val data = binding.cadastroEventoData

        val labelOcorrera = binding.cadastroEventoLabelOcorrera

        val escolheData = binding.cadastroEventoPickDate
        escolheData.setOnClickListener {
            val dia = calendar.get(Calendar.DAY_OF_MONTH)
            val mes = calendar.get(Calendar.MONTH)
            val ano = calendar.get(Calendar.YEAR)

            val dpd = DatePickerDialog(
                view.context,
                R.style.DialogTheme,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    calendar.set(year, monthOfYear, dayOfMonth)
                    labelOcorrera.visibility = View.VISIBLE
                    data.text = DateToString(
                        calendar
                    )
                    evento.data = DateToStringBanco(calendar)
                },
                ano,
                mes,
                dia
            )
            dpd.show()

        }

        configuraToolbar()
        configuraListenerUserFoto()

        viewModel.novoEvento.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context,it,Toast.LENGTH_SHORT)
            navController!!.navigate(CadastroEventoFragmentDirections.actionCadastroEventoFragmentToOngCampanhasFragment())
        })

    }

    private fun novoEvento() {
        viewModel.novoEvento(evento)
    }

    private fun configuraListenerUserFoto() {
        binding.cadastroEventoImagem.setOnClickListener {
            val urlDialog = DialogUrl(this)
            urlDialog.show(parentFragmentManager, "Url Dialog")
        }
    }

    private fun configuraToolbar() {
        val toolbar = toolbarVazia(activity)
        toolbar?.setBackgroundColor(resources.getColor(R.color.corEventos))
//        toolbar?.inflateMenu(R.menu.fragment_both_procurar_menu)
    }

    override fun passaUrl(url: String) {
        binding.cadastroEventoUrlImagem.text = url
        Picasso.get()
            .load(url)
            .resize(110.dp, 110.dp)
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(binding.cadastroEventoImagem)
    }

}