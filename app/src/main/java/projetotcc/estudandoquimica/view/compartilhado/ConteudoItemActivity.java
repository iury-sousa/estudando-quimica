package projetotcc.estudandoquimica.view.compartilhado;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.databinding.ActivityConteudoItemBinding;
import projetotcc.estudandoquimica.model.Publicacao;
import projetotcc.estudandoquimica.model.Usuario;
import projetotcc.estudandoquimica.viewmodel.PublicacaoViewModel;

public class ConteudoItemActivity extends AppCompatActivity {

    private ActivityConteudoItemBinding binding;
    private FirebaseAuth auth;
    private PublicacaoViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_conteudo_item);
        String idPublicacao = null;
        viewModel = new PublicacaoViewModel();
        auth = FirebaseAuth.getInstance();

        Bundle b = getIntent().getExtras();
        if (b != null) {

            idPublicacao = getIntent().getExtras().getString("idPub");
            conteudo(idPublicacao);
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Publicação");


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){

            onBackPressed();
        }
        return true;
    }

    public void conteudo(String idPublicacao) {

//        DatabaseReference reference = FirebaseDatabase.getInstance()
//                .getReference("publicacoes/" + idPublicacao);

        viewModel.getDataSnapshotLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {

                dataSnapshot.child(idPublicacao).getRef().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        Publicacao p = new Publicacao();
                        p.setId(dataSnapshot.getKey());
                        p.setTextoPublicacao(dataSnapshot.child("textoPublicacao").getValue(String.class));
                        p.setTitulo(dataSnapshot.child("titulo").getValue(String.class));
                        p.setDataPublicacao(String.valueOf(dataSnapshot.child("timestamp").getValue(Long.class)));
                        p.setAdmin(new Usuario(dataSnapshot.child("administrador").getValue(String.class), "", "", ""));

                        p.setImagemUrl(dataSnapshot.child("imagens/url").getValue(String.class));

                        DatabaseReference reference = FirebaseDatabase.getInstance()
                                .getReference("publicacoes_curtida/" + p.getId());

                        reference.child("listaCurtidas")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        Boolean curtiuUser = dataSnapshot.child(auth.getCurrentUser().getUid()).getValue(Boolean.class);

                                        p.setNumCurtidas((int) dataSnapshot.getChildrenCount());

                                        if (curtiuUser != null) {

                                            p.setCurtiu(true);


                                        } else {

                                            p.setCurtiu(false);

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                        dataSnapshot.child("comentarios").getRef().addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                Log.i("TAG", String.valueOf(dataSnapshot.getChildrenCount()));
                                p.setNumComentarios((int) dataSnapshot.getChildrenCount());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        final DatabaseReference ref = FirebaseDatabase.getInstance().
                                getReference("usuarios/" + dataSnapshot.child("administrador").getValue(String.class));

                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                p.setAdmin(new Usuario(dataSnapshot.getKey(),
                                        dataSnapshot.child("nome").getValue(String.class), null,
                                        dataSnapshot.child("urlFoto").getValue(String.class)));


                                viewModel.setPublicacao(p, getApplicationContext());
                                binding.setPublicacao(viewModel);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });



    }
}
