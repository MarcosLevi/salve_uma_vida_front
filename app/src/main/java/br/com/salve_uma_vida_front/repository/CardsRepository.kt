package br.com.salve_uma_vida_front.repository

import br.com.salve_uma_vida_front.both.models.CardPesquisa
import br.com.salve_uma_vida_front.both.models.ItemCard

private var listaCards: MutableList<CardPesquisa> = mutableListOf(
    CardPesquisa(
        "Ajude o abrigo são José",
        "Ocorrerá em 24/08/2020",
        "Precisamo de ajuda com água e leite o mais urgente possível", mutableListOf(
            ItemCard(
                "Leite",
                "L",
                90
            ),
            ItemCard(
                "Água",
                "L",
                600
            )
        ),
        "https://s2.glbimg.com/8atC6PRf6CwMVV-UxvibB4fNz9o=/0x0:800x700/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2019/w/s/Er62guTOeZ7DE8x9uCBA/apelo-canino.jpg"
    ),
    CardPesquisa(
        "Ajude o abrigo São Camilo",
        "Ocorrerá em 26/06/2020",
        "Estamos precisando de ração o mais rápido possível! Por favor nos ajudem.",
        mutableListOf(
            ItemCard(
                "Ração",
                "Kg",
                100,
                30
            )
        ),
        "https://jornalzo.com.br/media/k2/items/cache/cb9c495b17bc28a44ffb50c55572ed63_XL.jpg?t=20141103_151946"
    ),
    CardPesquisa(
        "Ajude o abrigo São Fernando",
        "Ocorrerá em 22/06/2022",
        "Por favor nos ajudem",
        mutableListOf(
            ItemCard(
                "Ração",
                "Kg",
                100,
                30
            ),
            ItemCard(
                "Leite",
                "L",
                90,
                60
            ),
            ItemCard(
                "Água",
                "L",
                600,
                30
            ),
            ItemCard(
                "Sabão",
                "L",
                200,
                20
            )
        ),
        "https://s2.glbimg.com/v3Ds3slPcnF4IqvrIFEs8wZO_Q4=/0x0:1032x581/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2019/D/t/J7RB8WRHauvmsa0hSVCg/img-20190113-wa0027.jpg"
    ),
    CardPesquisa(
        "Ajude o abrigo São Castro",
        "Ocorrerá em 24/02/2021",
        "Estamos precisando de toda a sua ajuda para manter a necessidades do abrigo!",
        mutableListOf(
            ItemCard(
                "Ração",
                "Kg",
                100,
                30
            ),
            ItemCard(
                "Leite",
                "L",
                90,
                60
            )
        ),
        "https://s2.glbimg.com/cV_HpgcrpfWCvwfAnmvTUPN5Zyc=/0x0:1920x1080/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_59edd422c0c84a879bd37670ae4f538a/internal_photos/bs/2019/1/E/xFaPoJSOqGgrTxNbKHrA/crise-abrigo-animais-06-05-2019.mov-snapshot-01.14-2019.05.07-10.58.16-.jpg"
    ),
    CardPesquisa(
        "Ajude o abrigo São Lázaro",
        "Ocorrerá em 12/10/2020",
        "Estamos precisando de toda a sua ajuda para manter o abrigo!",
        mutableListOf(
            ItemCard(
                "Ração",
                "Kg",
                600,
                30
            )
        ),
        "https://scontent-for1-1.xx.fbcdn.net/v/t1.0-9/60441261_2947994721892555_4719557158608306176_o.jpg?_nc_cat=101&_nc_sid=09cbfe&_nc_eui2=AeHKE-p7aHv7ffI3yFrPfahbzi0y2E7q7erOLTLYTurt6vCnvjCMckJTjzC52NSTx-46zxnbauCr7QxTmCOvqd3a&_nc_ohc=H4-ExhLGCPEAX_nQpRJ&_nc_ht=scontent-for1-1.xx&oh=eb9d05416e15c06be6b41b55ec226d52&oe=5F6FF9E4"
    )
)

//salva no banco
fun addCardNaLista(
    titulo: String,
    timestamp: String,
    descricao: String,
    listaItens: MutableList<ItemCard>,
    urlImagem: String
) {
    listaCards.add(
        CardPesquisa(
            titulo,
            timestamp,
            descricao,
            listaItens,
            urlImagem
        )
    )
}

//get do banco
fun getListaTodosOsCards(): MutableList<CardPesquisa> {
    return listaCards.toMutableList()
}

//fun getListaComFiltro(filtro: String): MutableList<CardPesquisa> {
//
//}