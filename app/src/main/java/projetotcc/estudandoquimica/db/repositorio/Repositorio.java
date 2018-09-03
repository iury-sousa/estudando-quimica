package projetotcc.estudandoquimica.db.repositorio;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import java.util.List;

import projetotcc.estudandoquimica.db.database.AppDatabase;


class Repositorio<Entidade, Dao> {


    private AppDatabase db;
    Dao dao;
    LiveData<List<Entidade>> lista;

    Repositorio(Application application) {

        this.db = AppDatabase.getDatabase(application);

    }

    AppDatabase getDb() {
        return db;
    }

    public LiveData<List<Entidade>> getLista() {
        return lista;
    }
}
