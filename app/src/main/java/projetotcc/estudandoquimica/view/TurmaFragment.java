package projetotcc.estudandoquimica.view;


import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.databinding.FragmentTurmaBinding;
import projetotcc.estudandoquimica.model.Turma;
import projetotcc.estudandoquimica.view.turma.TurmaAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TurmaFragment extends Fragment {

    private List<Turma> list;
    private FragmentTurmaBinding binding;
    private String nome;
    TurmaAdapter adapter;
    public TurmaFragment() {
        // Required empty public constructor
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_turma, container,false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflater.getContext());
        binding.listaTurma.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        adapter = new TurmaAdapter(list, inflater.getContext());

        binding.listaTurma.setAdapter(adapter);

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCadastrarTurma();
            }
        });

        return binding.getRoot();
    }

    public void DialogCadastrarTurma(){
        final android.support.v4.app.DialogFragment dialogFragment = new CadastrarTurmaDialog();
        dialogFragment.show(getFragmentManager(), "CadastrarTurmaDialog");

    }


}
