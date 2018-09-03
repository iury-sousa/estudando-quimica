package projetotcc.estudandoquimica.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ConteudoOffline implements Parcelable {

    private String titulo;
    private String assunto;
    private String texto;
    private int[] imagensArray;

    public ConteudoOffline() {

    }

    public ConteudoOffline(String titulo, String assunto, String texto, int[] imagensArray) {
        this.titulo = titulo;
        this.assunto = assunto;
        this.texto = texto;
        this.imagensArray = imagensArray;
    }

    protected ConteudoOffline(Parcel in) {
        titulo = in.readString();
        assunto = in.readString();
        texto = in.readString();
        imagensArray = in.createIntArray();
    }

    public static final Creator<ConteudoOffline> CREATOR = new Creator<ConteudoOffline>() {
        @Override
        public ConteudoOffline createFromParcel(Parcel in) {
            return new ConteudoOffline(in);
        }

        @Override
        public ConteudoOffline[] newArray(int size) {
            return new ConteudoOffline[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(assunto);
        dest.writeString(texto);
        dest.writeIntArray(imagensArray);
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAssunto() {
        return assunto;
    }

    public String getTexto() {
        return texto;
    }

    public int[] getImagensArray() {
        return imagensArray;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setImagensArray(int[] imagensArray) {
        this.imagensArray = imagensArray;
    }

}
