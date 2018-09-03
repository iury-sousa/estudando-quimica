package projetotcc.estudandoquimica.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

@Dao
interface IDaoBase<Entidade> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long inserir(Entidade entidade);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int atualizar(Entidade entidade);

    @Delete
    int deletar(Entidade entidade);

}
