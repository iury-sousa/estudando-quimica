package projetotcc.estudandoquimica.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Weeks;
import org.joda.time.Years;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import projetotcc.estudandoquimica.TempoCadastro;
import projetotcc.estudandoquimica.model.Comentario;

public class ComentarioViewModel extends ViewModel {

    //    public ObservableField<String> id = new ObservableField<>();
    public ObservableField<String> tempoComentario = new ObservableField<>();
    public ObservableField<String> texto = new ObservableField<>();
    public ObservableField<String> urlFoto = new ObservableField<>();
    public ObservableField<String> nomeUsuario = new ObservableField<>();


    public void setComentario(Comentario comentario) {

        texto.set(comentario.getTexto());
        nomeUsuario.set(comentario.getUsuario().getNome());
        urlFoto.set(comentario.getUsuario().getUrlFoto());

        long timesStamp = Long.valueOf(comentario.getTimestamp());

        tempoComentario.set(TempoCadastro.getTempo(timesStamp));
    }

    @BindingAdapter({"image"})
    public static void loadFoto(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }


}
