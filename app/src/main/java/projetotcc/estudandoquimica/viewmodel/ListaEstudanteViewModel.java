package projetotcc.estudandoquimica.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import projetotcc.estudandoquimica.FirebaseQueryLiveData;
import projetotcc.estudandoquimica.model.Usuario;

public class ListaEstudanteViewModel extends ViewModel {

    public final ObservableField<String> nome = new ObservableField<>();
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<Integer> visibilidadeBotaoAdd = new ObservableField<>();

    private Usuario usuario;

    private static final DatabaseReference estudantes =
            FirebaseDatabase.getInstance().getReference("estudantes");

    private static final DatabaseReference usuarios =
            FirebaseDatabase.getInstance().getReference("usuarios");

    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(estudantes);

    public ListaEstudanteViewModel() {

        this.usuario = new Usuario();

    }

    public void setViewModel(Usuario usuario) {

        nome.set(usuario.getNome());
        email.set(usuario.getEmail());
        imageUrl.set(usuario.getUrlFoto());
    }

    @BindingAdapter({"imageUrl"})
    public static void loadFoto(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }

    @NonNull
    public MutableLiveData<DataSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }

    @NonNull
    public MutableLiveData<DataSnapshot> getDataSnapshotLiveDataEstudantes() {

        liveData = new FirebaseQueryLiveData(usuarios);
        return liveData;
    }



}
