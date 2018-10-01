package projetotcc.estudandoquimica.view.home;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.VerificarConexaoInternet;
import projetotcc.estudandoquimica.VerificarUsuario;
import projetotcc.estudandoquimica.WrapContentLinearLayoutManager;
import projetotcc.estudandoquimica.componentesPersonalizados.StatefulRecyclerView;
import projetotcc.estudandoquimica.databinding.FragmentConteudoCompartilhadoBinding;
import projetotcc.estudandoquimica.databinding.PublicacaoItemBinding;
import projetotcc.estudandoquimica.model.Publicacao;
import projetotcc.estudandoquimica.model.Usuario;
import projetotcc.estudandoquimica.view.compartilhado.CadastrarPublicacaoActivity;
import projetotcc.estudandoquimica.view.compartilhado.PublicacaoAdapter;
import projetotcc.estudandoquimica.viewmodel.PublicacaoViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConteudoCompartilhadoFragment extends Fragment
implements PublicacaoAdapter.CurtirClickListener{

    private PublicacaoAdapter adapter;
    private FragmentConteudoCompartilhadoBinding binding;
    private StatefulRecyclerView recyclerView;
    private PublicacaoViewModel viewModel;
    private FirebaseAuth auth;
    private String idTurma = "";
    private boolean isProfessor = false;
    private List<Publicacao> list;
    private WrapContentLinearLayoutManager wcl;
    private Parcelable listState;

    public ConteudoCompartilhadoFragment() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("ListState", recyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding =
                DataBindingUtil.inflate(inflater,R.layout.fragment_conteudo_compartilhado,
                        container,false);

        setHasOptionsMenu(true);

        auth = FirebaseAuth.getInstance();
        viewModel = ViewModelProviders.of(this).get(PublicacaoViewModel.class);
        recyclerView =  binding.conteudoCompartilhado;

        list = new ArrayList<>();//carregarPublicacoes(viewModel);

        wcl = new WrapContentLinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(wcl);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        adapter = new PublicacaoAdapter(list, this.getContext(), this);

//        binding.semPublicacao.setVisibility(View.GONE);
//        binding.conteudoCompartilhado.setVisibility(View.VISIBLE);
        carregarPublicacoes(viewModel);

        recyclerView.setAdapter(adapter);

//        Usuario user = new Usuario();
//        user.setNome("Iury Wemerson");
//        user.setUrlFoto("android.resource://projetotcc.estudandoquimica/drawable/iury");
//
//        list.add(new Publicacao(user, "Fazendo teste de interface!", getResources().getString(R.string.large_text),
//                "android.resource://projetotcc.estudandoquimica/drawable/imagem_teste",
//                "5", 10, 2, true));
        //adapter.setPublicacao(list);

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




        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregarPublicacoes(viewModel);
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });

        return binding.getRoot();
    }

    private Publicacao getPublicacao(DataSnapshot dataSnapshot, int[] c){
        Publicacao p = new Publicacao();
        p.setId(dataSnapshot.getKey());
        p.setTextoPublicacao(dataSnapshot.child("textoPublicacao").getValue(String.class));
        p.setTitulo(dataSnapshot.child("titulo").getValue(String.class));
        p.setDataPublicacao(dataSnapshot.child("dataPublicacao").getValue(String.class));
        p.setAdmin(new Usuario(dataSnapshot.child("administrador").getValue(String.class), "", "", ""));
        p.setNumComentarios(0);
        p.setNumCurtidas(0);

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("publicacoes_curtida/" + p.getId());

        reference.child("listaCurtidas")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Boolean curtiuUser = dataSnapshot.child(auth.getCurrentUser().getUid()).getValue(Boolean.class);

                        if(curtiuUser != null){

                            p.setCurtiu(true);

                        }else {

                            p.setCurtiu(false);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        p.setImagemUrl(dataSnapshot.child("imagens/url").getValue(String.class));

        final DatabaseReference ref = FirebaseDatabase.getInstance().
                getReference("usuarios/" + dataSnapshot.child("administrador").getValue(String.class));

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                p.setAdmin(new Usuario(dataSnapshot.getKey(),
                        dataSnapshot.child("nome").getValue(String.class), null,
                        dataSnapshot.child("urlFoto").getValue(String.class)  ));
                //adapter.addPublicacao(p, adapter.getItemCount());
                adapter.notifyItemChanged(c[0]);
                c[0]++;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return p;
    }

    public List<Publicacao> carregarPorTurmas(DataSnapshot dataSnapshot1, List<Publicacao> lista, int[] c){

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance()
                .getReference("turmas/" + idTurma + "/listaPublicacoes");


        databaseReference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String id = dataSnapshot.getKey();


                dataSnapshot1.getRef()
                        .orderByKey()
                        .equalTo(id).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                        dataSnapshot.getRef().equalTo(
//                                String.valueOf(dataSnapshot.child("timestamp").getValue(Long.class))
//                                        + "_" + id).limitToFirst(10);

                        lista.add(getPublicacao(dataSnapshot, c));
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

        return lista;
    }

    public List<Publicacao> carregarPorAdmin(DataSnapshot dataSnapshot, List<Publicacao> lista, int[] c){

        dataSnapshot.getRef()
                .orderByChild("timestamp_admin")
                .limitToLast(100)
                .addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                dataSnapshot.getRef().child("timestamp_admin").equalTo(
//                        String.valueOf(dataSnapshot.child("timestamp").getValue(Long.class))
//                                + "_" +  auth.getCurrentUser().getUid()).li;

                lista.add(getPublicacao(dataSnapshot, c));
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

        return lista;
    }

    List<Publicacao> listaRetorno = null;
    public List<Publicacao> carregarPublicacoes(PublicacaoViewModel viewModel){

        if(!VerificarConexaoInternet.verificaConexao(getContext())){

            Snackbar.make(binding.getRoot(), "Sem acesso à internet!", Snackbar.LENGTH_SHORT).show();
        }

        viewModel.getDataSnapshotLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {

                List<Publicacao> lista = new ArrayList<>();
                final int[] c = {0};

                if(dataSnapshot.exists()){


                    if(!idTurma.isEmpty()){

                        lista = carregarPorTurmas(dataSnapshot, lista, c);

                    }else{


                        lista = carregarPorAdmin(dataSnapshot, lista, c);

                    }


                }

                if(adapter.getItemCount() == 0){

                    adapter.updatePublicacao(lista);
                }

                if(adapter.getItemCount() == 0) {

                    binding.semPublicacao.setVisibility(View.VISIBLE);
                    binding.conteudoCompartilhado.setVisibility(View.GONE);
                }else{

                    binding.semPublicacao.setVisibility(View.GONE);
                    binding.conteudoCompartilhado.setVisibility(View.VISIBLE);

                }
                binding.executePendingBindings();


//                binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        adapter.updatePublicacao(lista);
//                        binding.swipeRefreshLayout.setRefreshing(false);
//                    }
//                });
//                binding.executePendingBindings();
                listaRetorno = lista;
            }
        });

        return listaRetorno;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if(menu.findItem(R.id.action_publicacao) != null){
            menu.removeItem(R.id.action_publicacao);
        }

        if(VerificarUsuario.verificarUsuario()){

            inflater.inflate(R.menu.menu_conteudo, menu);
        }
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

    @Override
    public void curtir(PublicacaoItemBinding binding, Publicacao publicacao, PublicacaoViewModel publicacaoViewModel) {




//        binding.viewSwitcher.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                ViewSwitcher switcher = (ViewSwitcher) v;
//                //switcher.setDisplayedChild(viewModel.curtiu.get());
//                //viewModel.addCurtida();
//                if(publicacaoViewModel.iconeCurtida.get().getConstantState().
//                        equals(getActivity().getDrawable(R.drawable.ic_flask_vazio).getConstantState())){
//
//                    // switcher.findViewById(R.id.)
//                    if(publicacaoViewModel.curtiu.get() == 0){
//
//                        publicacaoViewModel.setI(true);
//
//
//                    }else {
//
//                        publicacaoViewModel.setI(false);
//                    }
//                    publicacaoViewModel.addCurtida();
//                    switcher.showNext();
//
//
//                } else {
//
//                    if(publicacaoViewModel.iconeCurtida.get().getConstantState().
//                            equals(getActivity().getDrawable(R.drawable.ic_flask_vazio).getConstantState())){
//
//
//                        publicacaoViewModel.setI(true);
//
//
//                    }else {
//
//
//                        publicacaoViewModel.setI(false);
//                    }
//
//                    publicacaoViewModel.addCurtida();
//                    switcher.showPrevious();
//
//                }
//            }
//        });
    }

}
