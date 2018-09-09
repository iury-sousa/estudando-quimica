package projetotcc.estudandoquimica.model;

import android.text.TextUtils;

import java.util.Date;

public class Usuario {


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


}
