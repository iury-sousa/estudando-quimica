package projetotcc.estudandoquimica.view.compartilhado;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.VerificarConexaoInternet;
import projetotcc.estudandoquimica.WrapContentLinearLayoutManager;
import projetotcc.estudandoquimica.databinding.ActivityComentariosBinding;
import projetotcc.estudandoquimica.model.Comentario;
import projetotcc.estudandoquimica.model.Usuario;

public class ComentariosActivity extends AppCompatActivity implements View.OnClickListener,
        PopupMenu.OnMenuItemClickListener, ComentarioAdapter.ClickComentarioListener {


    private RecyclerView recyclerView;
    private ComentarioAdapter adapter;
    private FirebaseUser usuario;
    private ActivityComentariosBinding binding;
    private String idPublicacao;
    private PopupMenu popup;
    private static int RESULT = 1;
    private Comentario comentario;
    private int posicaoComentario;
    private static FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_comentarios);
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        user = FirebaseAuth.getInstance().getCurrentUser();
        TextView tv = findViewById(R.id.sem_registro);
        Bundle b = getIntent().getExtras();

        if (b != null) {
            idPublicacao = b.getString("idPublicacao");
        }

        binding.comentario.requestFocus();
        recyclerView = binding.listaComentarios;
        WrapContentLinearLayoutManager wcl = new WrapContentLinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        wcl.setStackFromEnd(true);
        recyclerView.setLayoutManager(wcl);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new ComentarioAdapter(this);

        Comentario c = new Comentario();
        c.setTexto("Primeiro comentario");
        c.setUsuario(new Usuario(usuario.getDisplayName(), usuario.getPhotoUrl().toString()));

        List<Comentario> lista = new ArrayList<>();
        final int[] cont = {0};
        adapter.setComentarios(lista);
//        adapter.addComentario(c);
//        adapter.addComentario(c);
//        adapter.addComentario(c);
//        adapter.addComentario(c);
//        adapter.addComentario(c);

        DatabaseReference reference = FirebaseDatabase
                .getInstance().getReference("publicacoes/" + idPublicacao);

        reference.child("comentarios").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Comentario comentario = new Comentario();
                comentario.setTexto(dataSnapshot.child("textoComentario").getValue(String.class));
                comentario.setTimestamp(String.valueOf(dataSnapshot.child("timestamp").getValue(Long.class)));
                String idUsuario = dataSnapshot.child("usuario").getValue(String.class);
                comentario.setId(dataSnapshot.getKey());

                DatabaseReference databaseReference =
                        FirebaseDatabase.getInstance().getReference("usuarios/" + idUsuario);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Usuario usuario = new Usuario(idUsuario,
                                dataSnapshot.child("nome").getValue(String.class), null,
                                dataSnapshot.child("urlFoto").getValue(String.class));

                        comentario.setUsuario(usuario);

                        adapter.addComentario(comentario);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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



        recyclerView.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Comentários");

        binding.btnAddComentario.setOnClickListener(this);

        binding.comentario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s.toString().trim())){
                    binding.btnAddComentario.setVisibility(View.VISIBLE);
                }else{
                    binding.btnAddComentario.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();
                return true;

            default:

                return false;

        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_add_comentario:

                String texto = binding.comentario.getText().toString().trim();

                Comentario comentario = new Comentario();
                comentario.setUsuario(
                        new Usuario(usuario.getUid(),
                                usuario.getDisplayName(), null,
                                usuario.getPhotoUrl().toString()));

                comentario.setTexto(texto);

                Map map = comentario.toMap();

                if (!texto.isEmpty()) {

                    DatabaseReference database = FirebaseDatabase
                            .getInstance().getReference("publicacoes/" + idPublicacao);

                    database.child("comentarios").push().setValue(map);

                    binding.comentario.setText("");
                    InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow( binding.comentario.getWindowToken(), 0);

                }


                break;
        }
    }

    public void showMenu(View v, Comentario comentario, int posicao) {
        popup = new PopupMenu(this, v);

        this.comentario = comentario;
        this.posicaoComentario = posicao;
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_manipular_comentario);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_edit:

                Intent it = new Intent(this, EditarComentarioActivity.class);
                it.putExtra("texto", comentario.getTexto());
                startActivityForResult(it, RESULT);

                return true;

            case R.id.action_delete:

                if(!VerificarConexaoInternet.verificaConexao(getApplicationContext())){

                    VerificarConexaoInternet.getMensagem(binding.getRoot());
                }else {

                    DatabaseReference reference = FirebaseDatabase
                            .getInstance().getReference("publicacoes/" + idPublicacao);

                    reference.child("comentarios").child(comentario.getId()).removeValue((databaseError, databaseReference) -> {

                        adapter.removerComentario(posicaoComentario);

                        Snackbar mySnackbar = Snackbar.make(binding.getRoot(),
                                "Comentário excluido com sucesso!", Snackbar.LENGTH_SHORT);

                        mySnackbar.show();

                    });
                }

                return true;

            case R.id.action_cancel:
                popup.dismiss();
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onPressComentario(View v, Comentario comentario, int posicao) {


//        DatabaseReference ref = FirebaseDatabase.getInstance()
//                .getReference("publicacoes/" + idPublicacao);

        if(comentario.getUsuario().getId().equals(user.getUid())){
            showMenu(v, comentario, posicao);
        }

//        ref.child("comentarios").child(comentario.getId())
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if(dataSnapshot.child("usuario").getValue(String.class).equals(user.getUid())){
//                    showMenu(v, comentario, posicao);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT){

            if(resultCode == RESULT_OK){

                String resultado = data.getStringExtra("resultado");

                DatabaseReference reference = FirebaseDatabase.getInstance()
                        .getReference("publicacoes/" + idPublicacao);

                comentario.setTexto(resultado);
                Map<String, Object> map = comentario.toMap();


                reference.child("comentarios").child(comentario.getId()).updateChildren(map, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                        adapter.removerComentario(posicaoComentario);
                        adapter.addComentario(comentario, posicaoComentario);
                    }
                });




            }
        }
    }
}
