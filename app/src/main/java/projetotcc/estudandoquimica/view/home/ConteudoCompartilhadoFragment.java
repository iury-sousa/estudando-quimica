package projetotcc.estudandoquimica.view.home;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.WrapContentLinearLayoutManager;
import projetotcc.estudandoquimica.databinding.FragmentConteudoCompartilhadoBinding;
import projetotcc.estudandoquimica.model.Publicacao;
import projetotcc.estudandoquimica.model.Usuario;
import projetotcc.estudandoquimica.view.compartilhado.CadastrarPublicacaoActivity;
import projetotcc.estudandoquimica.view.compartilhado.PublicacaoAdapter;
import projetotcc.estudandoquimica.viewmodel.PublicacaoViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConteudoCompartilhadoFragment extends Fragment {

    private PublicacaoAdapter adapter;
    private FragmentConteudoCompartilhadoBinding binding;
    private RecyclerView recyclerView;
    private PublicacaoViewModel viewModel;
    private FirebaseAuth auth;
    private String idTurma = "";
    private boolean isProfessor = false;

    public ConteudoCompartilhadoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding =
                DataBindingUtil.inflate(inflater,R.layout.fragment_conteudo_compartilhado,
                        container,false);

        setHasOptionsMenu(true);

        auth = FirebaseAuth.getInstance();

        recyclerView =  binding.conteudoCompartilhado;
        List<Publicacao> list = new ArrayList<>();

        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new PublicacaoAdapter(this.getContext());
        recyclerView.setAdapter(adapter);

//        Usuario user = new Usuario();
//        user.setNome("Iury Wemerson");
//        user.setUrlFoto("android.resource://projetotcc.estudandoquimica/drawable/iury");
//
//        list.add(new Publicacao(user, "Fazendo teste de interface!", getResources().getString(R.string.large_text),
//                "android.resource://projetotcc.estudandoquimica/drawable/imagem_teste",
//                "5", 10, 2, true));
        adapter.setPublicacao(list);

        viewModel = ViewModelProviders.of(this).get(PublicacaoViewModel.class);


        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("usuarios/" + auth.getCurrentUser().getUid());


        Intent it = getActivity().getIntent();
        Bundle b = it.getExtras();

        if(b != null){
            idTurma = getActivity().getIntent().getExtras().getString("idTurma");
        }

//        reference.child("professor").getRef().addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                isProfessor = dataSnapshot.getValue(Boolean.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        viewModel.getDataSnapshotLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {

                if(dataSnapshot != null){

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                            .getReference("publicacoes/");

//                    if(isProfessor){
//
//                    }

                    dataSnapshot.getRef()
                            .orderByChild("administrador")
                            .equalTo(auth.getCurrentUser().getUid())
                            .addChildEventListener(new ChildEventListener() {

                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            Publicacao p = new Publicacao();
                            p.setId(dataSnapshot.getKey());
                            p.setTextoPublicacao(dataSnapshot.child("textoPublicacao").getValue(String.class));
                            p.setTitulo(dataSnapshot.child("titulo").getValue(String.class));
                            p.setDataPublicacao(dataSnapshot.child("dataPublicacao").getValue(String.class));
                            p.setAdmin(new Usuario(dataSnapshot.child("administrador").getValue(String.class), "", "", ""));

                            p.setImagemUrl(dataSnapshot.child("imagens/url").getValue(String.class));


                            final DatabaseReference ref = FirebaseDatabase.getInstance().
                                    getReference("usuarios/" + dataSnapshot.child("administrador").getValue(String.class));

                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    p.setAdmin(new Usuario(dataSnapshot.getKey(),
                                            dataSnapshot.child("nome").getValue(String.class), null,
                                            dataSnapshot.child("urlFoto").getValue(String.class)  ));
                                    adapter.addPublicacao(p, adapter.getItemCount());

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            //list.add(p);
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
                }
                if(adapter.getItemCount() == 0){

                    //binding.semPublicacao.setVisibility(View.VISIBLE);
                    adapter.setPublicacao(list);
                }else{

                    binding.semPublicacao.setVisibility(View.GONE);
                }

            }
        });
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.setPublicacao(list);
                // list.addAll(adapter.get);
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if(menu.findItem(R.id.action_publicacao) != null){
            menu.removeItem(R.id.action_publicacao);
        }


        inflater.inflate(R.menu.menu_conteudo, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_publicacao:

                Intent it = new Intent(getActivity(), CadastrarPublicacaoActivity.class);
                startActivity(it);
                getActivity().overridePendingTransition(R.anim.enter_top, R.anim.zoom_out);
                return true;

            default:
                return false;
        }
    }

}
