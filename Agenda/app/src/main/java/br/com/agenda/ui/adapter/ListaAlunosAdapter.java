package br.com.agenda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.agenda.R;
import br.com.agenda.model.Aluno;

public class ListaAlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos = new ArrayList<>();
    private final Context context;

    public ListaAlunosAdapter(Context context) {
        this.context = context;
    }

    //quantidade de elementos que um adapter vai ter
    @Override
    public int getCount() {
        return alunos.size();
    }

    //O item que queremos pegar a partir de uma posicao
    @Override
    public Aluno getItem(int posicao) {
        return alunos.get(posicao);
    }

    //Representa o id do aluno que está nessa posição
    @Override
    public long getItemId(int posicao) {
        return alunos.get(posicao).getId();
    }

    //A view que está representado cada elemento do adapter
    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        View viewCriada = criaView(viewGroup);
        Aluno alunoDevolvido = alunos.get(posicao);
        vincula(viewCriada, alunoDevolvido);
        return viewCriada;
    }

    private void vincula(View view, Aluno aluno) {
        TextView nome = view.findViewById(R.id.item_aluno_nome);
        nome.setText(aluno.getNome());
        TextView telefone = view.findViewById(R.id.item_aluno_telefone);
        telefone.setText(aluno.getTelefone());
    }

    private View criaView(ViewGroup viewGroup) {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.item_aluno, viewGroup, false);
    }

    private void clear() {
        alunos.clear();
    }

    private void addAll(List<Aluno> alunos) {
        this.alunos.addAll(alunos);
    }

    public void  atualiza(List<Aluno> alunos){
        this.alunos.clear();
        this.alunos.addAll(alunos);
        notifyDataSetChanged();
    }
    public void remove(Aluno aluno) {
        alunos.remove(aluno);
        notifyDataSetChanged();
    }
}
