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
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.VerificarConexaoInternet;
import projetotcc.estudandoquimica.VerificarUsuario;
import projetotcc.estudandoquimica.WrapContentLinearLayoutManager;
import projetotcc.estudandoquimica.componentesPersonalizados.StatefulRecyclerView;
import projetotcc.estudandoquimica.databinding.FragmentConteudoCompartilhadoBinding;
import projetotcc.estudandoquimica.databinding.PublicacaoItemBinding;
import projetotcc.estudandoquimica.model.Comentario;
import projetotcc.estudandoquimica.model.Publicacao;
import projetotcc.estudandoquimica.model.Usuario;
import projetotcc.estudandoquimica.view.compartilhado.CadastrarPublicacaoActivity;
import projetotcc.estudandoquimica.view.compartilhado.ComentariosActivity;
import projetotcc.estudandoquimica.view.compartilhado.ConteudoItemActivity;
import projetotcc.estudandoquimica.view.compartilhado.EditarComentarioActivity;
import projetotcc.estudandoquimica.view.compartilhado.PublicacaoAdapter;
import projetotcc.estudandoquimica.view.usuario.ListaUsuariosActivity;
import projetotcc.estudandoquimica.viewmodel.PublicacaoViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConteudoCompartilhadoFragment extends Fragment
        implements PublicacaoAdapter.BotoesClickListener, PopupMenu.OnMenuItemClickListener {

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
    private PopupMenu popup;
    private Publicacao publicacao;
    private int posicaoPublicacao;
    Usuario usuario;

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
                DataBindingUtil.inflate(inflater, R.layout.fragment_conteudo_compartilhado,
                        container, false);

        setHasOptionsMenu(true);

        auth = FirebaseAuth.getInstance();
        viewModel = ViewModelProviders.of(this).get(PublicacaoViewModel.class);

        recyclerView = binding.conteudoCompartilhado;

        list = new ArrayList<>();//carregarPublicacoes(viewModel);

        wcl = new WrapContentLinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        wcl.setReverseLayout(true);
        wcl.setStackFromEnd(true);
        recyclerView.setLayoutManager(wcl);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        adapter = new PublicacaoAdapter(list, this.getContext(), this);

//        binding.semPublicacao.setVisibility(View.GONE);
//        binding.conteudoCompartilhado.setVisibility(View.VISIBLE);


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

        if (b != null) {
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

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("usuarios/" + auth.getCurrentUser().getUid());

        usuario = new Usuario();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usuario.setProfessor(dataSnapshot.child("professor").getValue(Boolean.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        carregarPublicacoes(viewModel);

        return binding.getRoot();
    }

    private Publicacao getPublicacao(DataSnapshot dataSnapshot, int[] c) {
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

    public List<Publicacao> carregarPorTurmas(DataSnapshot dataSnapshot1, List<Publicacao> lista, int[] c) {

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

    public List<Publicacao> carregarPorAdmin(DataSnapshot dataSnapshot, List<Publicacao> lista, int[] c) {

        dataSnapshot.getRef()
                //.orderByKey()
                .orderByChild("administrador").equalTo(auth.getCurrentUser().getUid())
                .limitToLast(100)
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                dataSnapshot.getRef().child("timestamp_admin").equalTo(
//                        String.valueOf(dataSnapshot.child("timestamp").getValue(Long.class))
//                                + "_" +  auth.getCurrentUser().getUid()).li;

                        lista.add(getPublicacao(dataSnapshot, c));
                        //adapter.addPublicacao(getPublicacao(dataSnapshot, c), 0);


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

    public List<Publicacao> carregarTodos(DataSnapshot dataSnapshot, List<Publicacao> lista, int[] c){

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("estudante_turmas/" + auth.getCurrentUser().getUid());

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                DatabaseReference ref = FirebaseDatabase.getInstance()
                        .getReference("turmas/" + dataSnapshot.getKey() + "/listaPublicacoes");

                ref.limitToFirst(5).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot1, @Nullable String s) {
                        String id = dataSnapshot1.getKey();


                        dataSnapshot.getRef()
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

    public void carregarPublicacoes(PublicacaoViewModel viewModel) {

        if (!VerificarConexaoInternet.verificaConexao(getContext())) {

            // Snackbar.make(binding.principal, "Sem acesso à internet!", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(getContext(), "Sem acesso à internet!", Toast.LENGTH_SHORT).show();
        }

        viewModel.getDataSnapshotLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {

                List<Publicacao> lista = new ArrayList<>();
                final int[] c = {0};

                if (dataSnapshot.exists()) {


                    if (!idTurma.isEmpty()) {

                        lista = carregarPorTurmas(dataSnapshot, lista, c);

                    } else {

                        lista = carregarPorAdmin(dataSnapshot, lista, c);

                    }


                }
                if (adapter.getItemCount() == 0) {

                    adapter.updatePublicacao(lista);

                }

                if (adapter.getItemCount() == 0) {

                    binding.semPublicacao.setVisibility(View.VISIBLE);
                    binding.conteudoCompartilhado.setVisibility(View.GONE);
                } else {

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
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if (menu.findItem(R.id.action_publicacao) != null) {
            menu.removeItem(R.id.action_publicacao);
        }

        if (VerificarUsuario.verificarUsuario()) {

            inflater.inflate(R.menu.menu_conteudo, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

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

    }

    @Override
    public void clickCurtidas(String idPublicacao) {

        Intent it = new Intent(getActivity(), ListaUsuariosActivity.class);

        it.putExtra("idPublicacao", idPublicacao);
        it.putExtra("opcao", 1);

        startActivity(it);
    }

    @Override
    public void comentar(Publicacao publicacao) {
        Intent it = new Intent(getActivity(), ComentariosActivity.class);
        it.putExtra("idPublicacao", publicacao.getId());
        startActivity(it);

    }

    @Override
    public void btnComentariosClick(Publicacao publicacao) {
        Intent it = new Intent(getActivity(), ComentariosActivity.class);
        it.putExtra("idPublicacao", publicacao.getId());
        startActivity(it);
    }

    @Override
    public void onClickPublicacao(Publicacao publicacao) {

//       Intent it = new Intent(getActivity(), ConteudoItemActivity.class );
//       it.putExtra("idPub", publicacao.getId());
//
//       startActivity(it);

    }

    @Override
    public void onClickConfPublicacao(View v, Publicacao publicacao, int posicao) {

        showMenu(v, publicacao, posicao);
    }

    public void showMenu(View v, Publicacao publicacao, int posicao) {

        this.publicacao = publicacao;
        this.posicaoPublicacao = posicao;

        popup = new PopupMenu(getActivity(), v);

        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_manipular_comentario);
        popup.show();
    }

    private DatabaseReference reference;
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){

            case R.id.action_edit:

                Intent it = new Intent(getActivity(), CadastrarPublicacaoActivity.class);

                it.putExtra("pub", publicacao);
                it.putExtra("administrador", publicacao.getAdmin());
                startActivity(it);

                return true;

            case R.id.action_delete:

                if(!VerificarConexaoInternet.verificaConexao(getActivity())){

                    VerificarConexaoInternet.getMensagem(binding.getRoot());
                }else{

                    reference = FirebaseDatabase.getInstance()
                            .getReference("publicacoes/" + publicacao.getId());

                    reference.removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError,
                                               @NonNull DatabaseReference databaseReference) {

                            reference = FirebaseDatabase.getInstance()
                                    .getReference("publicacoes_curtida/" + publicacao.getId());

                            reference.removeValue();

                            reference = FirebaseDatabase.getInstance().getReference("turmas");

                            reference.orderByChild("administradorTurma")
                                    .equalTo(auth.getCurrentUser().getUid())
                                    .addChildEventListener(new ChildEventListener() {

                                @Override
                                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                    dataSnapshot.child("listaPublicacoes/" + publicacao.getId()).getRef().removeValue();

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

                            adapter.remover(posicaoPublicacao);
                        }
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
}
