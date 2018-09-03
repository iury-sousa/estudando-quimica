package projetotcc.estudandoquimica.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.View;
import projetotcc.estudandoquimica.BR;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Objects;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.databinding.PublicacaoItemBinding;
import projetotcc.estudandoquimica.model.Publicacao;

public class PublicacaoViewModel extends BaseObservable {

    private Publicacao publicacao;
    private Context context;
    private Drawable iconeCurtida;

    public PublicacaoViewModel(Publicacao publicacao, Context context) {
        this.publicacao = publicacao;
        this.context = context;

        if(publicacao.getCurtiu()){
            setIconeCurtida(context.getDrawable(R.drawable.ic_flask_cheio));
        }else {
            setIconeCurtida(context.getDrawable(R.drawable.ic_flask_vazio));
        }
    }

    public String getNomeUsuario(){
        return publicacao.getUsuario().getNome();
    }

    public String getTitulo() {
        return publicacao.getTitulo();
    }

    /*public void setTitulo(String titulo) {
        publicacao.setTitulo(titulo);
        notifyPropertyChanged(BR.titulo);
    }*/

    public String getTextoPublicacao() {
        return publicacao.getTextoPublicacao();
    }

/*    public void setTextoPublicacao(String textoPublicacao) {
        publicacao.setTextoPublicacao(textoPublicacao);
        notifyPropertyChanged(BR.textoPublicacao);
    }*/

    public String getImagemUrl() {
        return publicacao.getImagemUrl();
    }

    @BindingAdapter({"image"})
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }

    public String getUrlFoto(){
        return publicacao.getUsuario().getUrlFoto();
    }

    @BindingAdapter({"image"})
    public static void loadFoto(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }


    public Drawable getIconeCurtiu(){
        return publicacao.getCurtiu() ? context.getDrawable(R.drawable.ic_flask_cheio) :
                context.getDrawable(R.drawable.ic_flask_vazio);
    }

    public void setCurtida(boolean curtida){

        publicacao.setCurtiu(curtida);

    }

    @BindingAdapter({"image"})
    public static void loadIcone(ImageView view, Drawable id) {
        Glide.with(view.getContext()).load(id).into(view);
    }

    @Bindable
    public String getCurtida(){

        return publicacao.getCurtiu() ? "1" : "0";
    }

    public String getTempoPublicacao() {
        return String.valueOf(publicacao.getTempoPublicacao()) + " h";
    }

/*    public void setTempoPublicacao(int tempoPublicacao) {
        publicacao.setTempoPublicacao(tempoPublicacao);
        notifyPropertyChanged(BR.tempoPublicacao);
    }*/

    public String getNumCurtidas() {
        return String.valueOf(publicacao.getNumCurtidas());
    }

/*    public void setNumCurtidas(int numCurtidas) {
        publicacao.setNumCurtidas(numCurtidas);
        notifyPropertyChanged(BR.numCurtidas);
    }*/

    public String getNumComentarios() {
        return String.valueOf(publicacao.getNumComentarios());
    }

/*    public void setNumComentarios(int numComentarios) {
        publicacao.setNumComentarios(numComentarios);
        notifyPropertyChanged(BR.numComentarios);
    }*/

    public int getVisibilidadeImagem(){

        return publicacao.getImagemUrl() == null ? View.GONE : View.VISIBLE;
    }

    public int getVisibilidadeTitulo(){

        return publicacao.getTitulo() == null ? View.GONE : View.VISIBLE;
    }

   // Drawable iconeCurtiu;
    /*public void setIconeCurtiu(Drawable icone){

        if (Objects.equals(icone.getConstantState(),
                Objects.requireNonNull(context.getDrawable(R.drawable.ic_flask_cheio)).getConstantState())){

            publicacao.setCurtiu(true);
            iconeCurtiu = context.getDrawable(R.drawable.ic_flask_cheio);
        }else{
            publicacao.setCurtiu(false);
            iconeCurtiu = context.getDrawable(R.drawable.ic_flask_vazio);
        }
        notifyPropertyChanged(BR.iconeCurtiu);
    }*/

    @Bindable
    public Drawable getIconeCurtida() {
        return iconeCurtida;
    }

    public void setIconeCurtida(Drawable iconeCurtida) {
        this.iconeCurtida = iconeCurtida;
        notifyPropertyChanged(BR.iconeCurtida);
    }
}
