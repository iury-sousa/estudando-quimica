package projetotcc.estudandoquimica.model;

import com.google.firebase.database.Exclude;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Comentario {

    private String id;
    private String timestamp;
    private String texto;
    private Usuario usuario;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Exclude
    public Map<String, Object> toMap() {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        HashMap<String, Object> result = new HashMap<>();
        result.put("textoComentario", texto);
        result.put("usuario", usuario.getId());
        result.put("timestamp", timestamp.getTime());

        return result;
    }
}
