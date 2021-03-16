package br.com.salve_uma_vida_front.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
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
    lateinit var evento: EventoDto
    private lateinit var viewModel: EventosViewModel
    private var isEdita = false

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
        configuracoesIniciais(view)

    }

    private fun configuracoesIniciais(view: View) {
        navController = Navigation.findNavController(view)
        setaEvento()
        configuraFinalizarEvento(view)
        configuraDatePicker(view)
        configuraToolbar()
        configuraListenerUserFoto()
        configuraObservers()
    }

    private fun setaEvento() {
        val eventoRecebido = getEventoByArgs()
        if (eventoRecebido == null) {
            evento = EventoDto()
        } else {
            isEdita = true
            evento = eventoRecebido
            preencheCampos()
        }
    }

    private fun preencheCampos() {
        binding.cadastroEventoUrlImagem.setText(evento.imagem)
        Picasso.get()
            .load(evento.imagem)
            .resize(110.dp, 110.dp)
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(binding.cadastroEventoImagem)
        binding.cadastroEventoTitulo.setText(evento.titulo)
        binding.cadastroEventoData.setText(FormatStringToDate(evento.data!!))
        binding.cadastroEventoDescricao.setText(evento.descricao)
        binding.cadastroEventoEndereco.setText(evento.endereco)
    }

    private fun getEventoByArgs(): EventoDto? {
        val args: CadastroEventoFragmentArgs by navArgs()
        val evento = args.evento
        return evento
    }

    private fun configuraObservers() {
        viewModel.novoEvento.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            navController!!.navigate(CadastroEventoFragmentDirections.actionCadastroEventoFragmentToOngCampanhasFragment())
        })

        viewModel.updateEvento.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            navController!!.navigate(CadastroEventoFragmentDirections.actionCadastroEventoFragmentToOngCampanhasFragment())
        })
    }

    private fun configuraDatePicker(view: View) {
        val calendar = Calendar.getInstance()
        val data = binding.cadastroEventoData
        val datePicker = binding.cadastroEventoPickDate

        datePicker.setOnClickListener {
            view.clearFocus()
            requireContext().hideKeyboard(view)
            val dia = calendar.get(Calendar.DAY_OF_MONTH)
            val mes = calendar.get(Calendar.MONTH)
            val ano = calendar.get(Calendar.YEAR)

            val dpd = DatePickerDialog(
                view.context,
                R.style.DialogTheme,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    calendar.set(year, monthOfYear, dayOfMonth)
                    data.setText(DateToString(calendar))
                    evento.data = DateToStringBanco(calendar)
                },
                ano,
                mes,
                dia
            )
            dpd.show()
        }
    }

    private fun configuraFinalizarEvento(view: View) {
        val finalizaEvento = binding.cadastroEventoFinalizar
        finalizaEvento.setOnClickListener {
            view.clearFocus()
            requireContext().hideKeyboard(view)
            zeraErros()
            if (validate()) {
                atribuiCamposAoEvento()
                if (isEdita)
                    updateEvento()
                else
                    novoEvento()
            }

        }
    }

    private fun atribuiCamposAoEvento() {
        evento.descricao = binding.cadastroEventoDescricao.text.toString()
        evento.titulo = binding.cadastroEventoTitulo.text.toString()
        evento.endereco = binding.cadastroEventoEndereco.text.toString()
        evento.imagem = binding.cadastroEventoUrlImagem.text.toString()
        val (latitude, longitude, endereco) = adressToLatLong(
            evento.endereco!!,
            activity?.applicationContext
        )
        evento.latitude = latitude as Float
        evento.longitude = longitude as Float
        evento.endereco = endereco as String
    }

    private fun validate(): Boolean {
        if (TextUtils.isEmpty(binding.cadastroEventoUrlImagem.text)) {
            binding.cadastroEventoUrlImagem.error =
                "Imagem é necessária para fazer o cadastro do evento"
            return false
        }
        if (TextUtils.isEmpty(binding.cadastroEventoTitulo.text)) {
            binding.cadastroEventoTitulo.error =
                "Título é necessário para fazer o cadastro do evento"
            return false
        }
        if (TextUtils.isEmpty(binding.cadastroEventoData.text)) {
            binding.cadastroEventoData.error = "Data é necessária para fazer o cadastro do evento"
            return false
        }
        if (TextUtils.isEmpty(binding.cadastroEventoDescricao.text)) {
            binding.cadastroEventoDescricao.error =
                "Descrição é necessária para fazer o cadastro do evento"
            return false
        }
        if (TextUtils.isEmpty(binding.cadastroEventoEndereco.text)) {
            binding.cadastroEventoEndereco.error =
                "Endereço é necessário para fazer o cadastro do evento"
            return false
        }
        return true
    }

    private fun zeraErros() {
        binding.cadastroEventoUrlImagem.error = null
        binding.cadastroEventoTitulo.error = null
        binding.cadastroEventoData.error = null
        binding.cadastroEventoDescricao.error = null
        binding.cadastroEventoEndereco.error = null
    }

    private fun novoEvento() {
        viewModel.novoEvento(evento)
    }

    private fun updateEvento() {
        viewModel.updateEvento(evento)
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
        if (isEdita)
            toolbar?.title = "Edita Evento"
    }

    override fun passaUrl(url: String) {
        binding.cadastroEventoUrlImagem.setText(url)
        Picasso.get()
            .load(url)
            .resize(110.dp, 110.dp)
            .centerCrop()
            .placeholder(R.drawable.ic_dafault_photo)
            .error(R.drawable.ic_baseline_report_problem_24)
            .into(binding.cadastroEventoImagem)
    }

}