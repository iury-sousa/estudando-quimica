package projetotcc.estudandoquimica.db.repositorio;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import projetotcc.estudandoquimica.db.dao.IUsuarioDao;
import projetotcc.estudandoquimica.db.entidade.UsuarioEntity;
import projetotcc.estudandoquimica.db.repositorio.irepositorio.IUsuarioRepositorio;

public class UsuarioRepositorio extends Repositorio<UsuarioEntity, IUsuarioDao> implements IUsuarioRepositorio {


    public UsuarioRepositorio(Application application) {
        super(application);

        this.dao = getDb().usuarioDao();
        lista = dao.getUsuarios();
    }

    @Override
    public long inserir(UsuarioEntity entidades) {
        new UsuarioAsyncTaskBaseTask(dao, AsyncTaskBase.INSERIR).execute(entidades);

       /* try {
            result = (long) task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }*/

        return (long) 0;
    }

    @Override
    public int atualizar(UsuarioEntity entidades) {
        new UsuarioAsyncTaskBaseTask(dao, AsyncTaskBase.ALTERAR).execute(entidades);
        return 0;
    }

    @Override
    public int deletar(UsuarioEntity usuarioEntity) {
        new UsuarioAsyncTaskBaseTask(dao, AsyncTaskBase.DELETAR).execute(usuarioEntity);
        return 0;
    }

    @Override
    public LiveData<List<UsuarioEntity>> getAll() {
        return lista;
    }

    @Override
    public LiveData<UsuarioEntity> pesquisarPorID(int id) {
        return dao.getUsuario(id);
    }


    private static class UsuarioAsyncTaskBaseTask extends AsyncTaskBase<UsuarioEntity, IUsuarioDao> {

        UsuarioAsyncTaskBaseTask(IUsuarioDao iUsuarioDao, int opcao) {
            super(iUsuarioDao, opcao);
        }

        @Override
        protected Void doInBackground(UsuarioEntity... usuarios) {
            super.doInBackground(usuarios);

            int result = 0;
            switch (opcao){

                case AsyncTaskBase.INSERIR:
                     dao.inserir(usuarios[0]);
                    break;

                case AsyncTaskBase.ALTERAR:
                     dao.atualizar(usuarios[0]);
                    break;

                case AsyncTaskBase.DELETAR:
                     dao.deletar(usuarios[0]);
                    break;
                default:
            }
            return null;
        }


    }
}
