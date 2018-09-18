package projetotcc.estudandoquimica.model;

import android.graphics.drawable.Drawable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Publicacao {

    private String id;
    private Usuario admin;
    private String titulo;
    private String textoPublicacao;
    private String imagemUrl;
    private String dataPublicacao;
    private int numCurtidas;
    private int numComentarios;
    private boolean curtiu;
    private Drawable iconeCurtida;

    public Publicacao() {
    }


    public Publicacao(Usuario admin, String titulo, String textoPublicacao, String imagemUrl, String dataPublicacao, int numCurtidas, int numComentarios, boolean curtiu) {
        this.admin = admin;
        this.titulo = titulo;
        this.textoPublicacao = textoPublicacao;
        this.imagemUrl = imagemUrl;
        this.dataPublicacao = dataPublicacao;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getAdmin() {
        return admin;
    }

    public void setAdmin(Usuario admin) {
        this.admin = admin;
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

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("titulo", titulo);
        result.put("textoPublicacao", textoPublicacao);
        result.put("administrador", admin.getId());
        result.put("dataPublicacao", dataPublicacao);

        return result;
    }
}
