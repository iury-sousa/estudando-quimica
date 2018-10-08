package projetotcc.estudandoquimica.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Constants;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Publicacao implements Parcelable{

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
    private List<Publicacao> publicacaoList;
    HashMap<String, Object> timestamp;



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

//        Calendário cal = Calendário. getInstance (Locale. INGLÊS );
//        cal.setTimeInMillis (timestamp * 1000L);
//        Data da cadeia = DateFormat. formato ("dd-MM-aaaa hh: mm: ss", cal) .toString ();


    }

    protected Publicacao(Parcel in) {
        id = in.readString();
        titulo = in.readString();
        textoPublicacao = in.readString();
        imagemUrl = in.readString();
        dataPublicacao = in.readString();
        numCurtidas = in.readInt();
        numComentarios = in.readInt();
        curtiu = in.readByte() != 0;
        publicacaoList = in.createTypedArrayList(Publicacao.CREATOR);
    }

    public static final Creator<Publicacao> CREATOR = new Creator<Publicacao>() {
        @Override
        public Publicacao createFromParcel(Parcel in) {
            return new Publicacao(in);
        }

        @Override
        public Publicacao[] newArray(int size) {
            return new Publicacao[size];
        }
    };

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

    public List<Publicacao> getPublicacaoList() {
        return publicacaoList;
    }

    public void setPublicacaoList(List<Publicacao> publicacaoList) {
        this.publicacaoList = publicacaoList;
    }

    public HashMap<String, Object> getTimestamp(){
        return timestamp;
    }

    @Exclude
    public Map<String, Object> toMap() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        HashMap<String, Object> result = new HashMap<>();
        result.put("titulo", titulo);
        result.put("textoPublicacao", textoPublicacao);
        result.put("administrador", admin.getId());
        //result.put("dataPublicacao", dataPublicacao);
        result.put("turmasPublicacao", publicacaoList);
        result.put("timestamp", timestamp);

        return result;
    }

    @Exclude
    public long getTimestamp2(){
        return (long)timestamp.get("timestamp");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(titulo);
        dest.writeString(textoPublicacao);
        dest.writeString(imagemUrl);
        dest.writeString(dataPublicacao);
        dest.writeInt(numCurtidas);
        dest.writeInt(numComentarios);
        dest.writeByte((byte) (curtiu ? 1 : 0));
        dest.writeTypedList(publicacaoList);
    }
}
