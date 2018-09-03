package projetotcc.estudandoquimica.view.compartilhado;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import projetotcc.estudandoquimica.MainActivity;
import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.databinding.FragmentConteudoCompartilhadoBinding;
import projetotcc.estudandoquimica.databinding.FragmentPublicacaoBinding;
import projetotcc.estudandoquimica.interfaces.PublicacaoItemCallback;
import projetotcc.estudandoquimica.model.Publicacao;
import projetotcc.estudandoquimica.model.PublicacaoItem;
import projetotcc.estudandoquimica.model.Usuario;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublicacaoFragment extends Fragment {

    private static final String TAG = "PublicacaoListViewModel";
    private PublicacaoAdapter publicacaoAdapter;
    private PublicacaoFragment mBinding;

    public PublicacaoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       FragmentPublicacaoBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_publicacao, container, false);

       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

       publicacaoAdapter = new PublicacaoAdapter(null, getContext());
       mBinding.publicacaoList.setLayoutManager(layoutManager);
       mBinding.publicacaoList.setAdapter(publicacaoAdapter);


        return mBinding.getRoot();
    }

}
