package br.com.agenda.DAO;

import java.util.ArrayList;
import java.util.List;

import br.com.agenda.model.Aluno;

public class AlunoDAO {
    private  final static List<Aluno> alunos = new ArrayList<>();

    public void salva(Aluno aluno) { alunos.add(aluno); }

    public List<Aluno> getAlunos() { return new ArrayList<>(alunos); } //Boa prática mandar cópia de algo que seja importante
}
