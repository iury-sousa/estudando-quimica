package projetotcc.estudandoquimica.model;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Turma {

    private String id;

    private String nome;
    private List<Usuario> usuarios;
    private String data_criacao;
    private String administradorTurma;
    private Professor professor;
    private String codeTurma;

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Turma() {

    }



    public Turma(String nome, String data_criacao, String administradorTurma) {
        this.nome = nome;
        this.data_criacao = data_criacao;
        this.administradorTurma = administradorTurma;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getData_criacao() {
        return data_criacao;
    }

    public void setData_criacao(String data_criacao) {
        this.data_criacao = data_criacao;
    }

    public String getAdministradorTurma() {
        return administradorTurma;
    }

    public void setAdministradorTurma(String administradorTurma) {
        this.administradorTurma = administradorTurma;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodeTurma() {
        return codeTurma;
    }

    public void setCodeTurma(String codeTurma) {
        this.codeTurma = codeTurma;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nome", nome);
        result.put("administradorTurma", administradorTurma);
        result.put("data_criacao", data_criacao);
        result.put("codeTurma", codeTurma);

        return result;
    }

    public static class Professor{

        private String id;
        private String nome;

        public Professor(String id, String nome) {
            this.id = id;
            this.nome = nome;
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
    }

}
