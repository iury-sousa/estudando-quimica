package projetotcc.estudandoquimica.db.repositorio;

import android.os.AsyncTask;

public class AsyncTaskBase<Entidade, Dao> extends AsyncTask<Entidade, Void, Void> {

    final static public int INSERIR = 1;
    final static public int ALTERAR = 2;
    final static public int DELETAR = 3;

    protected Dao dao;
    protected int opcao;


    public AsyncTaskBase(Dao dao, int opcao) {

        this.dao = dao;
        this.opcao = opcao;
    }

    @Override
    protected Void doInBackground(Entidade... entidades) {
        return null;
    }

}
