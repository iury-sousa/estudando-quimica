package projetotcc.estudandoquimica.viewmodel;

import android.databinding.ObservableField;

import projetotcc.estudandoquimica.model.Publicacao;

public class CadastrarPublicacaoViewModel {

    public final ObservableField<String> titulo = new ObservableField<>();
    public final ObservableField<String> textoPublicacao = new ObservableField<>();
    public final ObservableField<String> imagemUrl = new ObservableField<>();

    public Publicacao getPublicacao(){

        Publicacao p = new Publicacao();
        p.setTitulo(titulo.get());
        p.setTextoPublicacao(textoPublicacao.get());
        return p;
    }
}
