package projetotcc.estudandoquimica.model;

import java.util.List;

public class Turma {

    private int id;
    private String nome;
    private List<Usuario> usuarioList;
    private Usuario administradorTurma;

    public Turma(String nome) {
        this.nome = nome;
    }
}
