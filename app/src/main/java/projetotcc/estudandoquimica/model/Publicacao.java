package projetotcc.estudandoquimica.model;

import android.graphics.drawable.Drawable;

import projetotcc.estudandoquimica.BR;

public class Publicacao {

    private int id;
    private Usuario usuario;
    private String titulo;
    private String textoPublicacao;
    private String imagemUrl;
    private int tempoPublicacao;
    private int numCurtidas;
    private int numComentarios;
    private boolean curtiu;
    private Drawable iconeCurtida;

    public Publicacao(Usuario usuario, String titulo, String textoPublicacao, String imagemUrl, int tempoPublicacao, int numCurtidas, int numComentarios, boolean curtiu) {
        this.usuario = usuario;
        this.titulo = titulo;
        this.textoPublicacao = textoPublicacao;
        this.imagemUrl = imagemUrl;
        this.tempoPublicacao = tempoPublicacao;
        this.numCurtidas = numCurtidas;
        this.numComentarios = numComentarios;
        this.curtiu = curtiu;

    }

    public Drawable getIconeCurtida() {
        return iconeCurtida;
    }

    public void setIconeCurtida(Drawable iconeCurtida) {
        this.iconeCurtida = iconeCurtida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTextoPublicacao() {
        return textoPublicacao;
    }

    public void setTextoPublicacao(String textoPublicacao) {
        this.textoPublicacao = textoPublicacao;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public int getTempoPublicacao() {
        return tempoPublicacao;
    }

    public void setTempoPublicacao(int tempoPublicacao) {
        this.tempoPublicacao = tempoPublicacao;
    }

    public int getNumCurtidas() {
        return numCurtidas;
    }

    public void setNumCurtidas(int numCurtidas) {
        this.numCurtidas = numCurtidas;
    }

    public int getNumComentarios() {
        return numComentarios;
    }

    public void setNumComentarios(int numComentarios) {
        this.numComentarios = numComentarios;
    }

    public boolean getCurtiu() {
        return curtiu;
    }

    public void setCurtiu(boolean curtiu) {
        this.curtiu = curtiu;
    }
}
