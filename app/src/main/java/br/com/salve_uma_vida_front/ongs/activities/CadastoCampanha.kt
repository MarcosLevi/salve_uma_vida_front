package br.com.salve_uma_vida_front.ongs.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.salve_uma_vida_front.R
import br.com.salve_uma_vida_front.both.models.ItemCard
import br.com.salve_uma_vida_front.ongs.adapters.ItemAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class CadastoCampanha : AppCompatActivity() {
    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        var calendar = Calendar.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_campanha)
        mostraItens()
        val adicionaItem = findViewById<ImageButton>(R.id.cadastroCampanhaAdicionaItem)
        adicionaItem.setOnClickListener {
            Toast.makeText(this, "Clicou no adiciona Item", Toast.LENGTH_SHORT).show()
        }
        val finalizaCampanha = findViewById<ImageButton>(R.id.cadastroCampanhaFinalizar)
        finalizaCampanha.setOnClickListener {
            Toast.makeText(this, "Clicou no finalizar campanha", Toast.LENGTH_SHORT).show()
        }

        val dataCampanha = findViewById<EditText>(R.id.cadastroCampanhaData)

        val escolheData = findViewById<ImageButton>(R.id.cadatroCampanhaPickDate)
        escolheData.setOnClickListener{
            var dia = calendar.get(Calendar.DAY_OF_MONTH)
            var mes = calendar.get(Calendar.MONTH)
            var ano = calendar.get(Calendar.YEAR)

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                calendar.set(year,monthOfYear,dayOfMonth)
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                dataCampanha.setText(formatter.format(calendar.time))
            }, ano, mes, dia)
            dpd.show()

        }
    }

    fun atualizaQuantidadeDeItens(quantidade: Int) {
        val quantidadeDeItens = findViewById<TextView>(R.id.cadastroCampanhaQuantidadeDeItens)
        if (quantidade > 1 || quantidade == 0) {
            quantidadeDeItens.text = "$quantidade itens"
        } else if (quantidade == 1) {
            quantidadeDeItens.text = "$quantidade item"
        }
    }

    private fun mostraItens() {
        val itensCard: List<ItemCard> = mutableListOf(
            ItemCard(
                "Ração",
                "Kg",
                100,
                0
            ),
            ItemCard(
                "Leite",
                "L",
                90,
                0
            ),
            ItemCard(
                "Coleira",
                "Unidades",
                500,
                0
            ),
            ItemCard(
                "Água",
                "L",
                600,
                0
            ),
            ItemCard(
                "Sabão",
                "L",
                200,
                0
            )
        )
        atualizaQuantidadeDeItens(itensCard.size)

        mRecyclerView = findViewById(R.id.cadastroCampanhaitensCampanhaOng)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)
        mAdapter = ItemAdapter(itensCard)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter
    }
}