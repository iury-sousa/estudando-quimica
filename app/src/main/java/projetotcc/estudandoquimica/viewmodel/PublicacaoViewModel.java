package projetotcc.estudandoquimica.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import projetotcc.estudandoquimica.FirebaseQueryLiveData;
import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.model.Publicacao;
import projetotcc.estudandoquimica.model.Usuario;

public class PublicacaoViewModel extends ViewModel {

    private Drawable iconeCurtida;
    public final ObservableField<Integer> curtiu = new ObservableField<>();
    public final ObservableField<String> nomeUsuario = new ObservableField<>();
    public final ObservableField<String> fotoUsuario = new ObservableField<>();
    public final ObservableField<String> titulo = new ObservableField<>();
    public final ObservableField<String> textoPublicacao = new ObservableField<>();
    public final ObservableField<String> imagemUrl = new ObservableField<>();
    public final ObservableField<String> dataPublicacao = new ObservableField<>();
    public final ObservableField<String> numCurtidas = new ObservableField<>();
    public final ObservableField<String> numComentarios = new ObservableField<>();

    private static final DatabaseReference PUBLICACAO_REF =
            FirebaseDatabase.getInstance().getReference("publicacoes");

    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(PUBLICACAO_REF);

    public DatabaseReference getPublicacaoRef() {
        return PUBLICACAO_REF;
    }

    public PublicacaoViewModel() {

//        if(publicacao.getCurtiu()){
//            setIconeCurtida(context.getDrawable(R.drawable.ic_flask_cheio));
//        }else {
//            setIconeCurtida(context.getDrawable(R.drawable.ic_flask_vazio));
//        }

        //titulo.set("Iury");
    }
    Context context;

    public void setPublicacao(Publicacao publicacao, Context context){

        nomeUsuario.set(publicacao.getAdmin().getNome());
        fotoUsuario.set(publicacao.getAdmin().getUrlFoto());
        titulo.set(publicacao.getTitulo());
        textoPublicacao.set(publicacao.getTextoPublicacao());
        imagemUrl.set(publicacao.getImagemUrl());
        dataPublicacao.set(String.valueOf(publicacao.getDataPublicacao()));
        numCurtidas.set(String.valueOf(publicacao.getNumCurtidas()));
        numComentarios.set(String.valueOf(publicacao.getNumCurtidas()));
       // curtiu.set(Integer.parseInt(String.valueOf(publicacao.getCurtiu())));
        curtiu.set(1);
//        if(publicacao.getCurtiu()){
//            setIconeCurtida(context.getDrawable(R.drawable.ic_flask_cheio));
//        }else {
//            setIconeCurtida(context.getDrawable(R.drawable.ic_flask_vazio));
//        }
        this.context = context;

    }

    @NonNull
    public MutableLiveData<DataSnapshot> getDataSnapshotLiveData() {

        return liveData;
    }

    public Publicacao getPublicacao(){

        Publicacao p = new Publicacao();
        p.setTitulo(titulo.get());
        p.setTextoPublicacao(textoPublicacao.get());
        return p;
    }

    public void inserir(Bitmap bitmap){

        Publicacao publicacao = new Publicacao();


        FirebaseAuth auth = FirebaseAuth.getInstance();
        Usuario usuario = new Usuario();
        usuario.setId(auth.getCurrentUser().getUid());
        @SuppressLint
                ("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String strDate = dateFormat.format(date);

        publicacao.setAdmin(usuario);
        publicacao.setTitulo(titulo.get());
        publicacao.setTextoPublicacao(textoPublicacao.get());
//        publicacao.setNumComentarios(numComentarios.get() != null ? 0 : Integer.parseInt(numComentarios.get()));
        publicacao.setDataPublicacao(strDate);

        Map<String, Object> map = publicacao.toMap();
        final String[] teste = new String[1];
        PUBLICACAO_REF.push().setValue(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                teste[0] = databaseReference.getKey();


            }
        });



    }

//    public String getNomeUsuario(){
//        return publicacao.getAdmin().getNome();
//    }
//
//    public String getTitulo() {
//        return publicacao.getTitulo();
//    }
//
//    /*public void setTitulo(String titulo) {
//        publicacao.setTitulo(titulo);
//        notifyPropertyChanged(BR.titulo);
//    }*/
//
//    public String getTextoPublicacao() {
//        return publicacao.getTextoPublicacao();
//    }
//
///*    public void setTextoPublicacao(String textoPublicacao) {
//        publicacao.setTextoPublicacao(textoPublicacao);
//        notifyPropertyChanged(BR.textoPublicacao);
//    }*/
//
//    public String getImagemUrl() {
//        return publicacao.getImagemUrl();
//    }
//
    @BindingAdapter({"image"})
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }
//
//    public String getUrlFoto(){
//        return publicacao.getAdmin().getUrlFoto();
//    }
//
    @BindingAdapter({"image"})
    public static void loadFoto(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }
//
//
//    public Drawable getIconeCurtiu(){
//        return publicacao.getCurtiu() ? context.getDrawable(R.drawable.ic_flask_cheio) :
//                context.getDrawable(R.drawable.ic_flask_vazio);
//    }
//
//    public void setCurtida(boolean curtida){
//
//        publicacao.setCurtiu(curtida);
//
//    }

    @BindingAdapter({"image"})
    public static void loadIcone(ImageView view, Drawable id) {
        Glide.with(view.getContext()).load(id).into(view);
    }

    public Drawable getIconeCurtida(){

        return curtiu.get() == 1 ? context.getDrawable(R.drawable.ic_flask_cheio) : context.getDrawable(R.drawable.ic_flask_vazio);
    }
//
//    @Bindable
//    public String getCurtida(){
//
//        return publicacao.getCurtiu() ? "1" : "0";
//    }
//
//    public String getDataPublicacao() {
//        return String.valueOf(publicacao.getDataPublicacao()) + " h";
//    }
//
///*    public void setDataPublicacao(int dataPublicacao) {
//        publicacao.setDataPublicacao(dataPublicacao);
//        notifyPropertyChanged(BR.dataPublicacao);
//    }*/
//
//    public String getNumCurtidas() {
//        return String.valueOf(publicacao.getNumCurtidas());
//    }
//
///*    public void setNumCurtidas(int numCurtidas) {
//        publicacao.setNumCurtidas(numCurtidas);
//        notifyPropertyChanged(BR.numCurtidas);
//    }*/
//
//    public String getNumComentarios() {
//        return String.valueOf(publicacao.getNumComentarios());
//    }
//
///*    public void setNumComentarios(int numComentarios) {
//        publicacao.setNumComentarios(numComentarios);
//        notifyPropertyChanged(BR.numComentarios);
//    }*/
//


    public int getVisibilidadeImagem(){

        return imagemUrl.get() == null ? View.GONE : View.VISIBLE;
    }

    public int getVisibilidadeTitulo(){

        return titulo.get() == null ? View.GONE : View.VISIBLE;
    }

    public int getVisibilidadeTexto(){
        return textoPublicacao.get() == null ? View.GONE : View.VISIBLE;
    }
//
//   // Drawable iconeCurtiu;
//    /*public void setIconeCurtiu(Drawable icone){
//
//        if (Objects.equals(icone.getConstantState(),
//                Objects.requireNonNull(context.getDrawable(R.drawable.ic_flask_cheio)).getConstantState())){
//
//            publicacao.setCurtiu(true);
//            iconeCurtiu = context.getDrawable(R.drawable.ic_flask_cheio);
//        }else{
//            publicacao.setCurtiu(false);
//            iconeCurtiu = context.getDrawable(R.drawable.ic_flask_vazio);
//        }
//        notifyPropertyChanged(BR.iconeCurtiu);
//    }*/
//
//    @Bindable
//    public Drawable getIconeCurtida() {
//        return iconeCurtida;
//    }
//
//    public void setIconeCurtida(Drawable iconeCurtida) {
//        this.iconeCurtida = iconeCurtida;
//        notifyPropertyChanged(BR.iconeCurtida);
//    }
}
