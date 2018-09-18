package projetotcc.estudandoquimica.view.compartilhado;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import projetotcc.estudandoquimica.MainActivity;
import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.WrapContentLinearLayoutManager;
import projetotcc.estudandoquimica.databinding.FragmentConteudoCompartilhadoBinding;
import projetotcc.estudandoquimica.databinding.FragmentPublicacaoBinding;
import projetotcc.estudandoquimica.interfaces.PublicacaoItemCallback;
import projetotcc.estudandoquimica.model.Publicacao;
import projetotcc.estudandoquimica.model.PublicacaoItem;
import projetotcc.estudandoquimica.model.Usuario;
import projetotcc.estudandoquimica.view.usuario.ListUsuariosFragment;
import projetotcc.estudandoquimica.viewmodel.PublicacaoViewModel;


public class PublicacaoFragment extends Fragment {

    private static final String TAG = "PublicacaoListViewModel";
    private PublicacaoAdapter publicacaoAdapter;
    private FragmentPublicacaoBinding mBinding;
    private RecyclerView recyclerView;
    private List<Publicacao> list;

    public PublicacaoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         mBinding = DataBindingUtil.inflate(inflater,
               R.layout.fragment_publicacao, container, false);

        recyclerView = mBinding.publicacaoList;
        list = new ArrayList<>();

        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        publicacaoAdapter = new PublicacaoAdapter(getContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(publicacaoAdapter);

        PublicacaoViewModel viewModel = ViewModelProviders.of(this).get(PublicacaoViewModel.class);
        viewModel.getDataSnapshotLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {

                if(dataSnapshot != null){
                    dataSnapshot.getRef().addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                            Publicacao p = new Publicacao();
                            p.setId(dataSnapshot.getKey());
                            p.setTextoPublicacao(dataSnapshot.child("textoPublicacao").getValue(String.class));
                            p.setTitulo(dataSnapshot.child("titulo").getValue(String.class));
                            p.setDataPublicacao(dataSnapshot.child("dataPublicacao").getValue(String.class));

                            Usuario u = new Usuario();
                            u.setId(dataSnapshot.child("administrador").getValue(String.class));

                            p.setAdmin(u);

                            list.add(p);

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
                if(publicacaoAdapter.getItemCount() == 0){
                    publicacaoAdapter.setPublicacao(list);
                }

                mBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        publicacaoAdapter.setPublicacao(list);
                        // list.addAll(adapter.get);
                        mBinding.swipeRefreshLayout.setRefreshing(false);
                    }
                });
                mBinding.executePendingBindings();
            }
        });
        return mBinding.getRoot();
    }

}
