package br.com.agenda.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import br.com.agenda.DAO.AlunoDAO;
import br.com.agenda.R;
import br.com.agenda.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {
    public static final String TITULO_APPBAR = "Novo Aluno";
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private final AlunoDAO dao = new AlunoDAO();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        setTitle(TITULO_APPBAR);
        inicializacaoDosCampos();
        configuraBotao();
    }

    private void configuraBotao() {
        Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Aluno alunoCriado = criaAluno();
                if(validar(alunoCriado)){
                    salva(alunoCriado);
                }
            }
        });
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

    private void salva(Aluno aluno) {
        dao.salva(aluno);
        finish();
    }

    private Aluno criaAluno() {
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();

        return new Aluno(nome, telefone, email);
    }

    public  boolean validar(Aluno aluno){

        boolean validado = true;
        if (aluno.getNome().length()<3){
            //se nome tiver menos de 3 letras
            validado = false;
            campoNome.setError("Nome inválido");//adiciona erro no componente na tela
        }
        if (!aluno.getEmail().contains("@")){
            //verifica se tem @ no campo do email, e nega a condição no comeco, se não tiver @ não é valido
            validado = false;
            campoEmail.setError("Email inválido");//adciona erro no componente;
        }

        if (aluno.getTelefone().isEmpty()||aluno.getTelefone().length()!=9){
            //verifica se as senhas sao iguais
            validado = false;
            campoTelefone.setError("Telefone inválido");//add erro no componente
        }
        return validado;
    }



}