package br.com.salve_uma_vida_front.models

class Erros {
    private var listaErros = mutableListOf<String>()

    fun addErro(erro: String) {
        listaErros.add(erro)
    }

    fun removeErros() {
        listaErros.clear()
    }

    override fun toString(): String {
        val errosString = StringBuilder()
        for (erro in listaErros) {
            errosString.append(erro + "\n")
        }
        return errosString.toString()
    }
}