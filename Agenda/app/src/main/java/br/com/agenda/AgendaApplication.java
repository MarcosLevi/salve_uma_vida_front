package br.com.agenda;

import android.app.Application;

import br.com.agenda.DAO.AlunoDAO;
import br.com.agenda.model.Aluno;

public class AgendaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        criaAlunosDeTeste();
    }

    private void criaAlunosDeTeste() {
        AlunoDAO dao = new AlunoDAO();
        dao.salva(new Aluno("Alex", "985654565", "marcos@gmail.com"));
        dao.salva(new Aluno("Joao", "985654565", "marcos@gmail.com"));
    }
}
