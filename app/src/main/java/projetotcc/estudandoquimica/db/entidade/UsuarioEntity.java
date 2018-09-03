package projetotcc.estudandoquimica.db.entidade;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

import projetotcc.estudandoquimica.db.utils.DateConverter;
import projetotcc.estudandoquimica.model.Usuario;

@Entity(tableName = "tbl_usuario")
public class UsuarioEntity{


    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id_usuario")
    private int id;

    @NonNull
    private String nome;

    @NonNull
    private String email;

    @NonNull
    private String senha;

    @NonNull
    private boolean professor;

    @Nullable
    private String celular;

    @Nullable
    @TypeConverters(DateConverter.class)
    private Date dataNascimento;

    @Nullable
    private boolean sexo;

    @Nullable
    private String urlFoto;

    public UsuarioEntity(@NonNull String nome, @NonNull String email, @NonNull String senha, @NonNull boolean professor, String celular, Date dataNascimento, boolean sexo, String urlFoto) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.professor = professor;
        this.celular = celular;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.urlFoto = urlFoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getSenha() {
        return senha;
    }

    public void setSenha(@NonNull String senha) {
        this.senha = senha;
    }

    @NonNull
    public boolean isProfessor() {
        return professor;
    }

    public void setProfessor(@NonNull boolean professor) {
        this.professor = professor;
    }

    @Nullable
    public String getCelular() {
        return celular;
    }

    public void setCelular(@Nullable String celular) {
        this.celular = celular;
    }

    @Nullable
    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(@Nullable Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Nullable
    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(@Nullable boolean sexo) {
        this.sexo = sexo;
    }

    @Nullable
    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(@Nullable String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
