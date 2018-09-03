package projetotcc.estudandoquimica.model;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import projetotcc.estudandoquimica.R;

public class PublicacaoItem {

    private int id;
    private Usuario usuario;
    private String titulo;
    private String textoPublicacao;
    private String imagemUrl;
    private int tempoPublicacao;
    private int numCurtidas;
    private int numComentarios;
    private boolean curtiu;

    public int getVisibilidadeTitulo() {
        return visibilidadeTitulo;
    }

    public void setVisibilidadeTitulo(int visibilidadeTitulo) {
        this.visibilidadeTitulo = visibilidadeTitulo;
    }

    public int getVisibilidadeImagem() {
        return visibilidadeImagem;
    }

    public void setVisibilidadeImagem(int visibilidadeImagem) {
        this.visibilidadeImagem = visibilidadeImagem;
    }

    private int visibilidadeTitulo;
    private int visibilidadeImagem;

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

    public boolean isCurtiu() {
        return curtiu;
    }

    public void setCurtiu(boolean curtiu) {
        this.curtiu = curtiu;
    }


    public String getImagemUrl() {
        return getImagemUrl();
    }

    @BindingAdapter({"image"})
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }

    public String getUrlFoto(){
        return getUsuario().getUrlFoto();
    }

    @BindingAdapter({"image"})
    public static void loadFoto(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }


    @BindingAdapter({"image"})
    public static void loadIcone(ImageView view, Drawable id) {
        Glide.with(view.getContext()).load(id).into(view);
    }

    public String getNomeUsuario(){
        return getUsuario().getNome();
    }
}
