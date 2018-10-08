package projetotcc.estudandoquimica.view.compartilhado;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.VerificarConexaoInternet;

public class EditarComentarioActivity extends AppCompatActivity {

    private String texto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_comentario);

        EditText editText = findViewById(R.id.comentario);
        Bundle bundle = getIntent().getExtras();

        if(bundle != null){

            texto = getIntent().getExtras().getString("texto");
        }

        editText.setText(texto);
        editText.setSelection(editText.getText().length());

        Button cancel = findViewById(R.id.btn_cancel);
        Button atualizar = findViewById(R.id.btn_atualizar);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //String texto = editText.getText().toString().trim();
                if (TextUtils.isEmpty(s.toString().trim()) || ( s.toString().trim().equals(texto.trim()))) {

                    atualizar.setEnabled(false);

                    atualizar.setBackgroundTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(getApplicationContext(), R.color.colorBtnAtualizar)));

                }else{
                    atualizar.setEnabled(true);
                    atualizar.setBackgroundTintList(ColorStateList.valueOf(
                            ContextCompat.getColor(getApplicationContext(), R.color.colorButtonPrincipal)));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String texto = editText.getText().toString().trim();

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("resultado", texto);
                    setResult(RESULT_OK, returnIntent);

                    finish();
                }

        });
    }
}
