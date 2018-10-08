package projetotcc.estudandoquimica.view.usuario;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.componentesPersonalizados.DividerItemDecoration;
import projetotcc.estudandoquimica.databinding.ActivityListaUsuariosBinding;
import projetotcc.estudandoquimica.model.Usuario;
import projetotcc.estudandoquimica.view.TurmaFragment;
import projetotcc.estudandoquimica.viewmodel.ListaUsuarioViewModel;

public class ListaUsuariosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        ListaUsuariosAdapter.ClickAddListener{

    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<Usuario> list;
    private ListaUsuariosAdapter adapter;
    private String idTurma = "";
    private ListaUsuarioViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //doMySearch(query);
        }

        Intent it = getIntent();
        Bundle b = it.getExtras();

        if (b != null) {
            idTurma = getIntent().getExtras().getString("idTurma");
        }

        list = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Adicionar alunos");

        ActivityListaUsuariosBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_lista_usuarios);

        binding.fab.setVisibility(View.GONE);

        recyclerView = binding.listaUsuarios;

        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.scrollToPosition(0);
        //recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, 80));

        adapter = new ListaUsuariosAdapter(list, ListaUsuariosAdapter.Opcao.ADD, this, this);

        adapter.setOnItemClickListener(this);

        recyclerView.setAdapter(adapter);

        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usuarios");


        viewModel = ViewModelProviders.of(this).get(ListaUsuarioViewModel.class);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                updateUsuarios();
                // list.addAll(adapter.get);
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
        binding.executePendingBindings();
        updateUsuarios();
    }


        private void updateUsuarios(){


            viewModel.getDataSnapshotLiveData().observe(this, new Observer<DataSnapshot>() {
                @Override
                public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                    List<Usuario> list = new ArrayList<>();
                    List<String> ids = new ArrayList<>();
                    if(dataSnapshot.exists()){

                        dataSnapshot.getRef().orderByChild("nome").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                final int[] c = {0};

                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("estudantes/" + idTurma);
                                String id = dataSnapshot.getKey();
                                Usuario usuario = new Usuario(dataSnapshot.getKey(), null, null, null);

                                ids.add("");

                                reference.addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                        if(dataSnapshot.exists()){

                                            Log.i("TAG", id + "_" + dataSnapshot.getKey() + " " + String.valueOf(id.equals(dataSnapshot.getKey())));
                                            // Log.i("TAG", "Not Existe " + id + "_" + dataSnapshot.getKey());

                                            if(!id.equals(dataSnapshot.getKey())) {
                                                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("usuarios/" + id);

                                                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        usuario.setId(dataSnapshot.getKey());
                                                        usuario.setEmail(dataSnapshot.child("email").getValue(String.class));
                                                        usuario.setUrlFoto(dataSnapshot.child("urlFoto").getValue(String.class));
                                                        usuario.setNome(dataSnapshot.child("nome").getValue(String.class));
                                                        //adapter.add(usuario, adapter.getItemCount());
                                                        adapter.notifyItemChanged(c[0]);

                                                        c[0]++;

                                                        if (!ids.contains(usuario.getId()))
                                                            list.add(usuario);

                                                        ids.add(usuario.getId());
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });


                                            }
                                            else{
                                                ids.add(id);
                                            }


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

                                if(list.isEmpty()){
                                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("usuarios/" + id);

                                    reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            usuario.setId(dataSnapshot.getKey());
                                            usuario.setEmail(dataSnapshot.child("email").getValue(String.class));
                                            usuario.setUrlFoto(dataSnapshot.child("urlFoto").getValue(String.class));
                                            usuario.setNome(dataSnapshot.child("nome").getValue(String.class));
                                            //adapter.add(usuario, adapter.getItemCount());
                                            adapter.notifyItemChanged(c[0]);

                                            c[0]++;

                                            if (!ids.contains(usuario.getId()))
                                                list.add(usuario);

                                            ids.add(usuario.getId());
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }

                                if(adapter.getItemCount() == 0){
                                    adapter.setUsuarios(list);
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
                    }




                }
            });


//        ref.orderByChild("nome").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                List<Usuario> list = new ArrayList<>();
//                final int[] c = {0};
//                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("estudantes/" + idTurma);
//                String id = dataSnapshot.getKey();
//                Usuario usuario = new Usuario(dataSnapshot.getKey(), null, null, null);
//
//                reference.addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                        if(dataSnapshot.exists()){
//
//                            Log.i("TAG", "Existe " + String.valueOf(id.equals(dataSnapshot.getKey())));
//                            Log.i("TAG", "Not Existe " + id + "_" + dataSnapshot.getKey());
//
//                            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("usuarios/" + id);
//
//                            if(!id.equals(dataSnapshot.getKey())) {
//                                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                        usuario.setId(dataSnapshot.getKey());
//                                        usuario.setEmail(dataSnapshot.child("email").getValue(String.class));
//                                        usuario.setUrlFoto(dataSnapshot.child("urlFoto").getValue(String.class));
//                                        usuario.setNome(dataSnapshot.child("nome").getValue(String.class));
//                                        //adapter.add(usuario, adapter.getItemCount());
//                                        adapter.notifyItemChanged(c[0]);
//
//                                        c[0]++;
//
//                                        list.add(usuario);
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                    }
//                                });
//                            }
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//                if(adapter.getItemCount() == 0){
//                    adapter.setUsuarios(list);
//                }
//
//                binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
//                        adapter.setUsuarios(list);
//                        // list.addAll(adapter.get);
//                        binding.swipeRefreshLayout.setRefreshing(false);
//                    }
//                });
//                binding.executePendingBindings();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



    }

//    private void whiteNotificationBar(View view) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            int flags = view.getSystemUiVisibility();
//            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//            view.setSystemUiVisibility(flags);
//            getWindow().setStatusBarColor(Color.WHITE);
//        }
//    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }

        super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        adapter.getFilter().filter(query.trim());

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        adapter.getFilter().filter(newText.trim());
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pesquisa, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener((android.support.v7.widget.SearchView.OnQueryTextListener) this);
        return true;

    }

    @Override
    public void click(Usuario usuario, int position) {

        if(usuario != null){

            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("estudantes/" + idTurma);

            ref.child(usuario.getId()).setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    Toast.makeText(ListaUsuariosActivity.this,
                            "Aluno adicionado com sucesso!", Toast.LENGTH_SHORT).show();

                    adapter.removerItem(position);
                }
            });
        }
    }


    public class WrapContentLinearLayoutManager extends LinearLayoutManager {

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("probe", "meet a IOOBE in RecyclerView");
            }
        }
    }
}
