package br.com.salve_uma_vida_front.repository

import br.com.salve_uma_vida_front.both.NewCalendar
import br.com.salve_uma_vida_front.both.models.Campanha
import br.com.salve_uma_vida_front.both.models.ItemCampanha
import br.com.salve_uma_vida_front.both.models.Ong
import java.util.*

var ong1: Ong = Ong(
    "São josé",
    "https://s2.glbimg.com/8atC6PRf6CwMVV-UxvibB4fNz9o=/0x0:800x700/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2019/w/s/Er62guTOeZ7DE8x9uCBA/apelo-canino.jpg",
    mutableListOf(), "Rua teste de souza",
    "(85)88888-8888"
)

var ong2: Ong = Ong(
    "São Camilo",
    "https://jornalzo.com.br/media/k2/items/cache/cb9c495b17bc28a44ffb50c55572ed63_XL.jpg?t=20141103_151946",
    mutableListOf(), "Rua teste2 de souza",
    "(85)99999-9999"
)

var listaCampanhas: MutableList<Campanha> = mutableListOf(
    Campanha(
        ong1,
        "Ajude o abrigo São josé",
        NewCalendar(26,6,2020),
        "Estamos precisando de ração o mais rápido possível! Por favor nos ajudem", mutableListOf(
            ItemCampanha("Ração", "Kg", 100, 30)
        )
    ),
    Campanha(
        ong1,
        "Pedimos ajuda novamente",
        NewCalendar(22,6,2022),
        "Por favor nos ajudem",
        mutableListOf(
            ItemCampanha(
                "Ração",
                "Kg",
                100,
                30
            ),
            ItemCampanha(
                "Leite",
                "L",
                90,
                60
            ),
            ItemCampanha(
                "Água",
                "L",
                600,
                30
            ),
            ItemCampanha(
                "Sabão",
                "L",
                200,
                20
            )
        )
    ),
    Campanha(
        ong2,
        "Ajude o abrigo São camilo",
        NewCalendar(20,2,2020),
        "Não sabemos mais o que fazer",
        mutableListOf(
        ItemCampanha("Água","L",300,20)
        )
    )
)

val listaOngs: MutableList<Ong> = mutableListOf(ong1,ong2)

//salva no banco
fun addCampanhaNaOng(
    nomeOng: String,
    titulo: String,
    dataDaCampanha: Calendar,
    descricao: String,
    listaItens: MutableList<ItemCampanha>
) {
    val ongEcontrada = listaOngs.find { ong -> ong.nomeDaOng.equals(nomeOng)}
    if (ongEcontrada != null){
        val novaCampanha = Campanha(ongEcontrada,titulo,dataDaCampanha,descricao,listaItens)
        listaCampanhas.add(novaCampanha)
    }

}

//get do banco
fun getListaTodosCards(): MutableList<Campanha> {
    return listaCampanhas
}

//fun getListaComFiltro(filtro: String): MutableList<CardPesquisa> {
//
//}