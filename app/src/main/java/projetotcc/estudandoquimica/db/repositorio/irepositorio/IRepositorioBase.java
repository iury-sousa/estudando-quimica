package projetotcc.estudandoquimica.db.repositorio.irepositorio;

import android.arch.lifecycle.LiveData;

import java.util.List;

public interface IRepositorioBase<Entidade> {

    long inserir(Entidade entidades);

    int atualizar(Entidade entidades);

    int deletar(Entidade entidade);

    LiveData<List<Entidade>> getAll();

    LiveData<Entidade> pesquisarPorID(int id);

}


