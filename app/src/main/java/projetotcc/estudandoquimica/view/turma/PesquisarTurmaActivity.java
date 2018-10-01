package projetotcc.estudandoquimica.view.turma;

import android.app.SearchManager;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
//import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
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
import projetotcc.estudandoquimica.model.Turma;
import projetotcc.estudandoquimica.view.compartilhado.CadastrarPublicacaoActivity;
import projetotcc.estudandoquimica.viewmodel.TurmaViewModel;

public class PesquisarTurmaActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
TurmaClickListener, RecyclerView.OnItemTouchListener, View.OnClickListener{

    private RecyclerView recyclerView;
    private PesquisarTurmaAdapter adapter;
    private GestureDetectorCompat gestureDetector;
    private ActionMode actionMode;
    private TurmaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisar_turma);

        recyclerView = findViewById(R.id.lista_turma);
        recyclerView.setLayoutManager(new TurmaFragment.WrapContentLinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));

        //recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, 0));

        adapter = new PesquisarTurmaAdapter(null, getApplicationContext(), this);

        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(this);
        gestureDetector =
                new GestureDetectorCompat(getApplicationContext(), new PesquisarTurmaActivity.RecyclerViewDemoOnGestureListener());

        FirebaseAuth auth = FirebaseAuth.getInstance();

//        Intent intent = getIntent();
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            //doMySearch(query);
//        }

//        Intent it = getIntent();
//        Bundle b = it.getExtras();
//
//        if(b != null){
//
//           ArrayList<String> listaTurmas =  b.getStringArrayList("id_turmas");
//
//
//        }

        adapter.getSelectedItems();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle("Selecionar turmas");

        viewModel = ViewModelProviders.of(this).get(TurmaViewModel.class);
        MutableLiveData<DataSnapshot> liveData = viewModel.getDataSnapshotLiveData();

        final String[] nome = new String[1];

        liveData.observe(this, new Observer<DataSnapshot>() {


            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                List<Turma> turmas = new ArrayList<>();
                final int[] c = {0};

                if (dataSnapshot.exists()) {

                    dataSnapshot.getRef().orderByChild("administradorTurma")
                            .equalTo(auth.getCurrentUser().getUid())
                            .addChildEventListener(new ChildEventListener() {

                        @Override
                        public void onChildAdded(@NonNull DataSnapshot t, @Nullable String s) {
                            Turma turma = new Turma();
                            turma.setNome(t.child("nome").getValue(String.class));
                            turma.setId(t.getKey());
                            turma.setData_criacao(t.child("data_criacao").getValue(String.class));
                            turma.setAdministradorTurma(t.child("administradorTurma").getValue(String.class));
                            turma.setCodeTurma(t.child("codeTurma").getValue(String.class));
                            turma.setProfessor(new Turma.Professor(turma.getAdministradorTurma(), null));


                            final DatabaseReference reference =
                                    FirebaseDatabase.getInstance().getReference("usuarios/" +
                                            t.child("administradorTurma").getValue(String.class));

                            reference
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            turma.setProfessor(new Turma.Professor(t.child("administradorTurma").
                                                    getValue(String.class), dataSnapshot.child("nome").getValue(String.class)));
                                            // turma.setAdministradorTurma(dataSnapshot.child("nome").getValue(String.class));
                                            adapter.notifyItemChanged(c[0]);
                                            c[0]++;


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                            //turma.setAdministradorTurma(nome[0]);
                            turmas.add(turma);
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
                    adapter.updateTurmas(turmas);
                }

                SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        adapter.updateTurmas(turmas);
                        // list.addAll(adapter.get);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });


            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left  );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                onBackPressed();
                //overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left  );
                return true;

            case R.id.action_info:
                AlertDialog.Builder builder = new AlertDialog.Builder(PesquisarTurmaActivity.this);

                builder.setMessage("Selecione pelo menos uma turma para concluir a operação.")
                        .setTitle("Informação")
                        .setIcon(R.drawable.ic_info)

                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {


                            }
                        })
                        .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;

            default:
                return false;
        }
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
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
        inflater.inflate(R.menu.menu_info, menu);
//
//        // Get the SearchView and set the searchable configuration
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.search).getActionView();
////        // Assumes current activity is the searchable activity
//       // searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
////        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
//        searchView.setOnQueryTextListener((android.support.v7.widget.SearchView.OnQueryTextListener) this);
        return true;

    }


    private android.support.v7.view.ActionMode.Callback startActionMode = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_selecionar_turmas, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            ArrayList<String> idTurmas = new ArrayList<>();
            ArrayList<String> nomeTurmas = new ArrayList<>();
            switch (item.getItemId()){

                case R.id.action_add_turmas:


                    for (int i : adapter.getSelectedItems()) {

                        Turma turma = adapter.getTurma(i);
                        idTurmas.add(turma.getId());
                        nomeTurmas.add(turma.getNome());

                    }
                    Intent it = new
                            Intent(PesquisarTurmaActivity.this, CadastrarPublicacaoActivity.class);

                    it.putStringArrayListExtra("idTurmas", idTurmas);
                    it.putStringArrayListExtra("nomeTurmas", nomeTurmas);

                    setResult(RESULT_OK, it);
                    finish();

                    return true;

                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
            adapter.clearSelections();
        }

    };

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public void onClick(View v) {
        if(!(v instanceof CardView)){
            return;
        }

        int idx = recyclerView.getChildAdapterPosition(v);

        if (actionMode != null) {
            myToggleSelection(idx);
            return;
        }
    }

    private void myToggleSelection(int idx) {
        adapter.toggleSelection(idx);
        String title = String.valueOf(adapter.getSelectedItemCount());

        actionMode.setTitle(title);

        if(adapter.getSelectedItemCount() == 0){

            actionMode.finish();
        }

    }

    @Override
    public void onClick(Turma turma) {

    }

    private class RecyclerViewDemoOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            onClick(view);
            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());

            if(!(view instanceof CardView)){
                return;
            }

            if (actionMode != null) {
                return;
            }

            actionMode = startSupportActionMode(startActionMode);

            int idx = recyclerView.getChildAdapterPosition(view);
            myToggleSelection(idx);
            super.onLongPress(e);
        }
    }
}
