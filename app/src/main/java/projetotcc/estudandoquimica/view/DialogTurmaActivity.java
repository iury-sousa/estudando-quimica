package projetotcc.estudandoquimica.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import projetotcc.estudandoquimica.R;

public class DialogTurmaActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nome;
    private TextView titulo;
    private int opcao; // Opcao = 1 - Inserir, Opcao = 2 - Alterar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_turma);

        //this.setFinishOnTouchOutside(false);

        opcao = 1;

        Button salvar = findViewById(R.id.botao_salvar);
        salvar.setOnClickListener(this);

        Button cancelar = findViewById(R.id.botao_cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        titulo = findViewById(R.id.titulo);
        nome = findViewById(R.id.nome);
        nome.requestFocus();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            nome.setText(getIntent().getExtras().getString("nome"));
            titulo.setText("Alterar turma");
            opcao = 2;
        }

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.botao_salvar){
            String resultado = nome.getText().toString();


            if (TextUtils.isEmpty(resultado.trim())) {
                nome.setError(getString(R.string.error_field_required));
            }else{

                Intent returnIntent = new Intent();
                returnIntent.putExtra("resultado",resultado);
                returnIntent.putExtra("opcao", opcao);
                setResult(RESULT_OK, returnIntent);

                finish();
            }



        }
    }
}
