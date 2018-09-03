package projetotcc.estudandoquimica.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import projetotcc.estudandoquimica.db.entidade.UsuarioEntity;

@Dao
public interface IUsuarioDao extends IDaoBase<UsuarioEntity> {

    @Query("SELECT * from tbl_usuario ORDER BY id_usuario ASC")
    LiveData<List<UsuarioEntity>> getUsuarios();

    @Query("SELECT * from tbl_usuario where id_usuario = :id")
    LiveData<UsuarioEntity> getUsuario(int id);

    @Query("SELECT * from tbl_usuario  where nome Like :nome ORDER BY nome ASC")
    LiveData<UsuarioEntity> getUsuario(String nome);

    @Query("DELETE FROM tbl_usuario")
    void deleteAll();
}
