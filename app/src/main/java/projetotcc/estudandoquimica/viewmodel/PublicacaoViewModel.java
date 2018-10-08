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
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import projetotcc.estudandoquimica.TempoCadastro;
import projetotcc.estudandoquimica.model.Publicacao;
import projetotcc.estudandoquimica.model.Usuario;

public class PublicacaoViewModel extends ViewModel {

    public final ObservableField<Drawable> iconeCurtida = new ObservableField<>();
    public final ObservableField<Integer> curtiu = new ObservableField<>();
    public final ObservableField<String> nomeUsuario = new ObservableField<>();
    public final ObservableField<String> fotoUsuario = new ObservableField<>();
    public final ObservableField<String> titulo = new ObservableField<>();
    public final ObservableField<String> textoPublicacao = new ObservableField<>();
    public final ObservableField<String> imagemUrl = new ObservableField<>();
    public final ObservableField<String> dataPublicacao = new ObservableField<>();
    public final ObservableField<String> numCurtidas = new ObservableField<>();
    public final ObservableField<String> numComentarios = new ObservableField<>();
    private String id;
    private Publicacao publicacao;

    private static DatabaseReference PUBLICACAO_REF =
            FirebaseDatabase.getInstance().getReference("publicacoes");

    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(PUBLICACAO_REF);

    public DatabaseReference getPublicacaoRef() {
        return PUBLICACAO_REF;
    }

    private FirebaseAuth auth;

    private Context context;

    public PublicacaoViewModel() {

//        if(publicacao.getCurtiu()){
//            setIconeCurtida(context.getDrawable(R.drawable.ic_flask_cheio));
//        }else {
//            setIconeCurtida(context.getDrawable(R.drawable.ic_flask_vazio));
//        }

        //titulo.set("Iury");
        auth = FirebaseAuth.getInstance();
    }

    public void setPublicacao(Publicacao publicacao, Context context){

        iconeCurtida.set(!publicacao.getCurtiu() ? context.getDrawable(R.drawable.ic_flask_vazio) :
                context.getDrawable(R.drawable.ic_flask_cheio)
        );
        this.publicacao = publicacao;

        nomeUsuario.set(publicacao.getAdmin().getNome());
        fotoUsuario.set(publicacao.getAdmin().getUrlFoto());
        titulo.set(publicacao.getTitulo());
        textoPublicacao.set(publicacao.getTextoPublicacao());
        imagemUrl.set(publicacao.getImagemUrl());

        numCurtidas.set(String.valueOf(publicacao.getNumCurtidas()) +
                ( publicacao.getNumCurtidas() == 1 ? " curtida" : " curtidas" ));

        numComentarios.set(String.valueOf(publicacao.getNumComentarios()) +
                ( publicacao.getNumComentarios() == 1 ? " comentário" : " comentários" ));

        id = publicacao.getId();
        curtiu.set(publicacao.getCurtiu() ? 1 : 0);
        //curtida();

        long timesStamp = Long.valueOf(publicacao.getDataPublicacao());
        dataPublicacao.set(TempoCadastro.getTempo(timesStamp));

        this.context = context;

    }

    public void setI(boolean c){

        if(c){
            iconeCurtida.set(context.getDrawable(R.drawable.ic_flask_cheio));
        }else{
            iconeCurtida.set(context.getDrawable(R.drawable.ic_flask_vazio));
        }
//
//        addCurtida();
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

    public void addCurtida(){

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("publicacoes_curtida/" + id);

        reference.child("listaCurtidas")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Boolean curtiuUser = dataSnapshot.child(auth.getCurrentUser().getUid()).getValue(Boolean.class);

                            if(curtiuUser != null){
                                reference.child("listaCurtidas/" + auth.getCurrentUser().getUid()).removeValue();

                               // curtiu.set(0);


                            }else {

                                reference.child("listaCurtidas/" + auth.getCurrentUser().getUid())
                                        .setValue(true);


                               // curtiu.set(1);
                            }

                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private boolean isCurtiu = false;
    public boolean curtida(){

//        DatabaseReference reference = FirebaseDatabase.getInstance()
//                .getReference("publicacoes/" + id);

        PUBLICACAO_REF.child(id).child("listaCurtidas")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Boolean curtiuUser = dataSnapshot.child(auth.getCurrentUser().getUid()).getValue(Boolean.class);

                        if(curtiuUser != null){

                            curtiu.set(0);
                            isCurtiu = true;

                        }else {


                            curtiu.set(1);
                            isCurtiu = false;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        return isCurtiu;
    }

    @BindingAdapter({"image"})
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }

    @BindingAdapter({"image"})
    public static void loadFoto(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }

//    @BindingAdapter({"image"})
//    public static void loadIcone(ImageView view, Drawable id) {
//        Glide.with(view.getContext()).load(id).into(view);
//    }

//    public Drawable getIconeCurtida(){
//
////        if(curtiu.get() == null){
////
////            return context.getDrawable(R.drawable.ic_flask_vazio);
////        }
//
//        return curtiu.get() == 0 ? context.getDrawable(R.drawable.ic_flask_cheio) : context.getDrawable(R.drawable.ic_flask_vazio);
//    }

    public int getVisibilidadeImagem(){

        return imagemUrl.get() == null ? View.GONE : View.VISIBLE;
    }

    public int getVisibilidadeTitulo(){

        return titulo.get() == null ? View.GONE : View.VISIBLE;
    }

    public int getVisibilidadeTexto(){
        return textoPublicacao.get() == null ? View.GONE : View.VISIBLE;
    }

    public int getVisibilidadeNumCurtidas(){

        return publicacao.getNumCurtidas() < 1 ? View.GONE : View.VISIBLE;
    }

    public int getVisibilidadeNumComentarios(){
        return publicacao.getNumComentarios() < 1 ? View.GONE : View.VISIBLE;
    }

    public int getVisibilidadeLikeComment(){

        return publicacao.getNumComentarios() == 0 && publicacao.getNumCurtidas() == 0 ?
                View.GONE : View.VISIBLE;
    }


    public int getVisibilidadeMenuAcoes(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        return user.getUid().equals(publicacao.getAdmin().getId()) ? View.VISIBLE : View.GONE;
    }


}
