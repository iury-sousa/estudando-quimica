package projetotcc.estudandoquimica.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import projetotcc.estudandoquimica.view.usuario.LibraryClass;

public class Usuario implements Parcelable{

    public static String PROVIDER = "projetotcc.estudandoquimica.model.Usuario.PROVIDER";

    private String id;
    private String nome;
    private String email;
    private String senha;
    private boolean professor;
    private String celular;
    private Date dataNascimento;
    private boolean sexo;
    private String urlFoto;

    public Usuario() {

    }

    public Usuario(String id, String nome, String email, String urlFoto) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.urlFoto = urlFoto;
    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome, String urlFoto) {
        this.nome = nome;
        this.urlFoto = urlFoto;
    }

    public Usuario(String nome,
                   String email,
                   String senha,
                   boolean is_professor,
                   String celular,
                   Date data_nascimento,
                   boolean sexo,
                   String urlFoto) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.professor = is_professor;
        this.celular = celular;
        this.dataNascimento = data_nascimento;
        this.sexo = sexo;
        this.urlFoto = urlFoto;
    }

    protected Usuario(Parcel in) {
        id = in.readString();
        nome = in.readString();
        email = in.readString();
        senha = in.readString();
        professor = in.readByte() != 0;
        celular = in.readString();
        sexo = in.readByte() != 0;
        urlFoto = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean getProfessor() {
        return professor;
    }

    public void setProfessor(boolean is_professor) {
        this.professor = is_professor;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setData_nascimento(Date data_nascimento) {
        this.dataNascimento = data_nascimento;
    }

    public boolean getSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public boolean emailValido(String email) {

        return email.contains("@");
    }

    public boolean senhaValida(String senha) {

        if(senha == null){
            return false;
        }
        return senha.length() > 6;
    }

    public void saveProviderSP(Context context, String token ){

        LibraryClass.saveSP( context, PROVIDER, token );
    }
    public String getProviderSP(Context context ){

        return( LibraryClass.getSP( context, PROVIDER) );
    }

    public void saveDB( DatabaseReference.CompletionListener... completionListener ){
        DatabaseReference firebase = LibraryClass.getFirebase().child("usuarios").child( getId() );

        if( completionListener.length == 0 ){
            firebase.setValue(this);
        }
        else{
            firebase.setValue(this, completionListener[0]);
        }
    }

    public void contextDataDB( Context context ){
        DatabaseReference firebase = LibraryClass.getFirebase().child("usuarios").child( getId() );

        firebase.addListenerForSingleValueEvent( (ValueEventListener) context );
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(nome);
        dest.writeString(email);
        dest.writeString(senha);
        dest.writeByte((byte) (professor ? 1 : 0));
        dest.writeString(celular);
        dest.writeByte((byte) (sexo ? 1 : 0));
        dest.writeString(urlFoto);
    }
}
