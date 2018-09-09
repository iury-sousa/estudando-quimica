package projetotcc.estudandoquimica.view.usuario;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import projetotcc.estudandoquimica.MainActivity;
import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.componentesPersonalizados.DividerItemDecoration;
import projetotcc.estudandoquimica.databinding.ActivityListaUsuariosBinding;
import projetotcc.estudandoquimica.model.Usuario;
import projetotcc.estudandoquimica.view.TurmaFragment;

public class ListaUsuariosActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<Usuario> list;
    private ListaUsuariosAdapter adapter;
    private String idTurma = "";

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

        if(b != null){
           idTurma = getIntent().getExtras().getString("idTurma");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Alunos");

        ActivityListaUsuariosBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_lista_usuarios);

        recyclerView = binding.listaUsuarios;

        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.scrollToPosition(0);
        //recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(
        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, 80));
        adapter = new ListaUsuariosAdapter();

        list = new ArrayList<>();
//        list.add(new Usuario(
//                "1","Ana Carolina", "ana.carolina@gmail.com", "http://2.bp.blogspot.com/-K721x_iAT3U/Tf1xOGZYscI/AAAAAAAAAOg/x7Xjl020KGM/s1600/167450_173610929346868_100000938868379_360714_2839589_n.jpg"));
//        list.add(new Usuario("1","Peterson Vick", "pet.vick@hotmail.com", "http://blogs.odiario.com/cenafashion/wp-content/uploads/sites/69/2013/05/corte-de-cabelo-masculino-arrepiado-5.jpg"));
//        list.add(new Usuario("1","Nicolly Franco", "franco.nick@yahool.com.br", "https://s-media-cache-ak0.pinimg.com/originals/ef/01/b8/ef01b884cc5f6ed2f88e50ebd4caad2c.jpg"));
//        list.add(new Usuario("1","Suellen Vismoker", "vismoker.su@gmail.com", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSPKT1ZyruOrCeEBHchjfjrJoahqiBxsQvmLh6BXdB-SS9OaSpB"));
//        list.add(new Usuario("1","Paulo Sant Anna", "p.sant.anna@gmail.com", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS8aqHAACvxXLeMIFhgyfhlI-UoCObfQY-tDRjPEpevw6OcRyaa"));
//        list.add(new Usuario("1","Angelina Werneck", "werneck.angel@hotmail.com", "https://i.pinimg.com/originals/ef/f5/01/eff501055582c6acf764e197ab5d5902.jpg"));


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("estudantes/" + idTurma);
        final int[] c = {0};

        ref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Usuario usuario = new Usuario(dataSnapshot.getKey(), dataSnapshot.getValue(String.class), null, null);

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuarios/" + usuario.getId());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        usuario.setEmail(dataSnapshot.child("email").getValue(String.class));
                        usuario.setUrlFoto(dataSnapshot.child("urlFoto").getValue(String.class));
                        c[0]++;

                        //list.add(usuario);
                        adapter.add(usuario, adapter.getItemCount());
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




        adapter.setUsuarios(list);

        recyclerView.setAdapter(adapter);

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
