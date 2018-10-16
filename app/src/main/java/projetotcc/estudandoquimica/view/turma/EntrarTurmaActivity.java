package projetotcc.estudandoquimica.view.turma;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import projetotcc.estudandoquimica.R;

public class EntrarTurmaActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText codigo;
    private CallbacksTurma callbacksTurma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_turma);

        Button salvar = findViewById(R.id.botao_salvar);
        salvar.setOnClickListener(this);

        codigo = findViewById(R.id.cod);

        Button cancelar = findViewById(R.id.botao_cancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        callbacksTurma = new CallbacksTurma() {
            @Override
            public void callback(Boolean b, String id) {
                if(b){
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("resultado",id);
                    setResult(RESULT_OK, returnIntent);
                    finish();

                }else{
                    codigo.setError("Não existe nenhuma turma com o código inserido");
                }
            }
        };
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.botao_salvar){
            String resultado = codigo.getText().toString();


            if (TextUtils.isEmpty(resultado.trim())) {
                codigo.setError(getString(R.string.error_field_required));
            }else if(resultado.trim().length() < 8){
                codigo.setError("O código inserido deve possuir 8 caracteres");
            }else{


                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("turmas");

                ref.orderByChild("codeTurma").equalTo("@" + resultado).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        if(dataSnapshot.child("codeTurma").getValue(String.class).equals("@" + resultado)){

                            callbacksTurma.callback(true, dataSnapshot.getKey());

                        }

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                callbacksTurma.callback(false, null);


//                ref.orderByChild("codeTurma").equalTo(resultado).limitToLast(1)
//                        .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

//                Intent returnIntent = new Intent();
//                returnIntent.putExtra("resultado",resultado);
//                returnIntent.putExtra("opcao", opcao);
//                setResult(RESULT_OK, returnIntent);

                //finish();
            }

        }
    }

    public interface CallbacksTurma{

        void callback(Boolean b, String id);
    }
}
