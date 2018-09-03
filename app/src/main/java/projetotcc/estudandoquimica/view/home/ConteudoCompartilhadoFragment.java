package projetotcc.estudandoquimica.view.home;


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

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.databinding.FragmentConteudoCompartilhadoBinding;
import projetotcc.estudandoquimica.model.Publicacao;
import projetotcc.estudandoquimica.model.Usuario;
import projetotcc.estudandoquimica.view.compartilhado.PublicacaoAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConteudoCompartilhadoFragment extends Fragment {

    public ConteudoCompartilhadoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentConteudoCompartilhadoBinding binding =
                DataBindingUtil.inflate(inflater,R.layout.fragment_conteudo_compartilhado,
                        container,false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflater.getContext());
        binding.conteudoCompartilhado.setLayoutManager(layoutManager);

        List<Publicacao> list = new ArrayList<>();

        Usuario user = new Usuario();
        user.setNome("Iury Wemerson");
        user.setUrlFoto("android.resource://projetotcc.estudandoquimica/drawable/iury");

        list.add(new Publicacao(user, "Fazendo teste de interface!", getResources().getString(R.string.large_text),
                "android.resource://projetotcc.estudandoquimica/drawable/imagem_teste",
                5, 10, 2, true));

        /*for (int i=0; i < 500; i++){
        list.add(new Publicacao(user, "Fazendo teste de interface!", getResources().getString(R.string.large_text),
                "android.resource://projetotcc.estudandoquimica/drawable/imagem_teste",
                5, 10, 2, false));
        }*/



        PublicacaoAdapter adapter = new PublicacaoAdapter(list, inflater.getContext());
        binding.conteudoCompartilhado.setAdapter(adapter);

        return binding.getRoot();
    }

}
