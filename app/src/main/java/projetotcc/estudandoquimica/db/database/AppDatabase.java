package projetotcc.estudandoquimica.db.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import projetotcc.estudandoquimica.db.dao.IUsuarioDao;
import projetotcc.estudandoquimica.db.entidade.UsuarioEntity;

@Database(entities = {UsuarioEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract IUsuarioDao usuarioDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                             AppDatabase.class, "estudando_quimica_database")
                                .fallbackToDestructiveMigration()
                                .addCallback(sRoomDatabaseCallback)
                                .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);

            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final IUsuarioDao usuarioDao;

        PopulateDbAsync(AppDatabase db) {

            usuarioDao = db.usuarioDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

           usuarioDao.deleteAll();
           usuarioDao.inserir(new UsuarioEntity(
                   "Iury","iury.iury@iury.com", "123456", false, null, null, true, null));

            return null;
        }
    }

}
