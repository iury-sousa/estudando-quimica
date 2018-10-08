package projetotcc.estudandoquimica.view.turma;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.CardView;
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
import android.view.WindowManager;

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
import projetotcc.estudandoquimica.VerificarUsuario;
import projetotcc.estudandoquimica.databinding.FragmentTurmaBinding;
import projetotcc.estudandoquimica.model.Turma;
import projetotcc.estudandoquimica.viewmodel.TurmaViewModel;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class TurmaFragment extends Fragment implements RecyclerView.OnItemTouchListener,
        View.OnClickListener, TurmaClickListener {

    private List<Turma> list;
    private FragmentTurmaBinding binding;
    private String nome;
    private TurmaAdapter adapter;
    private DatabaseReference ref;
    private TurmaViewModel viewModel;
    private FloatingActionButton fab;

    private int itemCount;
    private GestureDetectorCompat gestureDetector;
    private ActionMode actionMode;
    private RecyclerView recyclerView;
    private static final int DIALOG_REQUEST = 1;

    private FirebaseAuth auth;

    public TurmaFragment() {

    }

    //    private void runLayoutAnimation(final RecyclerView recyclerView) {
//        final Context context = recyclerView.getContext();
//        final LayoutAnimationController controller =
//                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation);
//
//        recyclerView.setLayoutAnimation(controller);
//        recyclerView.getAdapter().notifyDataSetChanged();
//        recyclerView.scheduleLayoutAnimation();
//    }
    public void setNome(String nome) {

        this.nome = nome;
        adapter.addTurma(viewModel.inserir(nome), adapter.getItemCount());

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_turma, container, false);
        list = new ArrayList<>();

//        Turma t = new Turma("Teste1", "13/09/2018 16:10:55", "Iury");
//        t.setProfessor(new Turma.Professor("Iury", "Iury"));
//        list.add(t);
//        list.add(t);
        ref = FirebaseDatabase.getInstance().getReference();

        recyclerView = binding.listaTurma;

        if (VerificarUsuario.verificarUsuario()) {

            binding.fab.setVisibility(View.VISIBLE);
        } else {
            binding.fab.setVisibility(View.GONE);
        }

        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(inflater.getContext(),
                LinearLayoutManager.VERTICAL, false));

        //recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        adapter = new TurmaAdapter(list, inflater.getContext(), this);

        recyclerView.setAdapter(adapter);
//        int resId = R.anim.layout_animation;
//        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(inflater.getContext(), resId);
//        recyclerView.setLayoutAnimation(animation);

//       recyclerView.addItemDecoration(
//                new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));


        recyclerView.addOnItemTouchListener(this);
        gestureDetector =
                new GestureDetectorCompat(this.getContext(), new RecyclerViewDemoOnGestureListener());

        viewModel = ViewModelProviders.of(this).get(TurmaViewModel.class);

        auth = FirebaseAuth.getInstance();


        MainActivity activity = (MainActivity) getActivity();

        activity.getSupportActionBar().setTitle("Turmas");

        fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCadastrarTurma(null);
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(atualizarTurmas()){
                    carregarPorAdmin();
                }else{
                    carregarPorAluno();
                }

                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });

        if(atualizarTurmas()){

        }else{

        }
        // setHasOptionsMenu(true);
        return binding.getRoot();
    }

    private boolean atualizarTurmas(){

        final boolean[] retorno = {false};
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("usuarios/" + auth.getCurrentUser().getUid());

        ref.child("professor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue(Boolean.class)){

                    carregarPorAdmin();
                }else{
                    carregarPorAluno();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return retorno[0];
    }

    private void carregarPorAdmin() {
        viewModel.getDataSnapshotLiveData().observe(this, new Observer<DataSnapshot>() {

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

                if (adapter.getItemCount() == 0) {
                    adapter.updateTurmas(turmas);
                }

                binding.executePendingBindings();
            }

        });


    }

    private void carregarPorAluno() {

        viewModel.getDataSnapshotLiveDataEstudante().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                List<Turma> turmas = new ArrayList<>();
                final int[] c = {0};

                dataSnapshot.child(auth.getCurrentUser().getUid()).getRef().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        dataSnapshot.getRef().addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                                dataSnapshot.getKey();

                                DatabaseReference reference = FirebaseDatabase.getInstance()
                                        .getReference("turmas/" + dataSnapshot.getKey());

                                reference.orderByChild("nome").getRef()
                                        .addListenerForSingleValueEvent(new ValueEventListener() {

                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot t) {
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
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                if (adapter.getItemCount() == 0) {
                    adapter.updateTurmas(turmas);
                }

                binding.executePendingBindings();
            }
        });
    }


    public void DialogCadastrarTurma(Turma turma) {

        Intent it = new Intent(getContext(), DialogTurmaActivity.class);

        if (turma != null) {
            it.putExtra("nome", turma.getNome());
        }

        startActivityForResult(it, DIALOG_REQUEST);


    }

    Turma turma;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DIALOG_REQUEST) {
            if (resultCode == RESULT_OK) {
                String resultado = data.getStringExtra("resultado");
                int opcao = data.getIntExtra("opcao", 0);

                if (opcao == 1) {
                    viewModel.inserir(resultado);
                } else if (opcao == 2) {

                    turma.setNome(resultado);
                    viewModel.atualizar(turma);
                }

            }
        }
        adapter.clearSelections();
    }

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

        if (!(v instanceof CardView)) {
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
        if (adapter.getSelectedItemCount() != 1) {
            // actionMode.getMenu().findItem(R.id.action_membros).setVisible(false);

            if (actionMode.getMenu().findItem(R.id.action_edit) != null)
                actionMode.getMenu().findItem(R.id.action_edit).setVisible(false);
        } else {
            // actionMode.getMenu().findItem(R.id.action_membros).setVisible(true);
            if (actionMode.getMenu().findItem(R.id.action_edit) != null)
                actionMode.getMenu().findItem(R.id.action_edit).setVisible(true);
        }

        if (adapter.getSelectedItemCount() == 0) {

            actionMode.finish();
        }

    }

    private android.support.v7.view.ActionMode.Callback startActionMode = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            MenuInflater inflater = mode.getMenuInflater();
            if (VerificarUsuario.verificarUsuario()) {

                inflater.inflate(R.menu.menu_turma, menu);
            } else {

                inflater.inflate(R.menu.menu_sair, menu);
            }

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {


            switch (item.getItemId()) {

                case R.id.action_delete:
                    List<Integer> selectedItemPositions = adapter.getSelectedItems();
                    int currPos;
                    String delete = null;
                    for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
                        currPos = selectedItemPositions.get(i);
                        Turma t = adapter.getTurma(currPos);
                        t.setNome("Teste Tudo");
                        delete = viewModel.deletar(t);
                        adapter.removerTurma(currPos);

                    }
                    actionMode.finish();

                    return true;
//                case R.id.action_membros:
//                    Intent it = new Intent(getContext(), ListaUsuariosActivity.class);
//
//                    if(adapter.getSelectedItems().size() == 1) {
//                        it.putExtra("idTurma", adapter.getTurma(
//                                adapter.getSelectedItems().get(0)).getId());
//                    }
//
//                    getActivity().startActivity(it);
//
//                    return true;
                case R.id.action_edit:

                    if (adapter.getSelectedItems().size() == 1) {
                        turma = adapter.getTurma(adapter.getSelectedItems().get(0));
                        DialogCadastrarTurma(turma);
                    }
                    actionMode.finish();
                    return true;

//                case R.id.action_exit:
//
//                    return true;
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
    public void onClick(Turma turma) {

        if (adapter.getSelectedItemCount() == 0) {
            Intent it = new Intent(getContext(), TurmaActivity.class);
            it.putExtra("idTurma", turma.getId());
            it.putExtra("nome", turma.getNome());
            getActivity().startActivity(it);
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        }
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

            if (!(view instanceof CardView)) {
                return;
            }

            if (actionMode != null) {
                return;
            }

            actionMode = ((MainActivity) TurmaFragment.this.getActivity()).startSupportActionMode(startActionMode);

            int idx = recyclerView.getChildAdapterPosition(view);
            myToggleSelection(idx);
            super.onLongPress(e);
        }
    }

    public static class WrapContentLinearLayoutManager extends LinearLayoutManager {

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
