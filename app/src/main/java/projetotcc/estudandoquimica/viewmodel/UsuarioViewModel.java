package projetotcc.estudandoquimica.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Date;
import projetotcc.estudandoquimica.BR;
import projetotcc.estudandoquimica.model.Usuario;

public class UsuarioViewModel extends BaseObservable {

    private Usuario usuario;

    public UsuarioViewModel(Usuario usuario) {

        this.usuario = usuario;
    }

    @Bindable
    public String getNome(){
        return usuario.getNome();
    }

    @Bindable
    public String getEmail(){
        return usuario.getEmail();
    }

    @Bindable
    public String getSenha(){
        return usuario.getSenha();
    }

    @Bindable
    public boolean getProfessor(){
        return usuario.getProfessor();
    }

    @Bindable
    public String getCelular(){
        return usuario.getCelular();
    }

    @Bindable
    public Date getDataNascimento(){
        return usuario.getDataNascimento();
    }

    @Bindable
    public boolean getSexo(){
        return usuario.getSexo();
    }

    @Bindable
    public String getUrlFoto(){
        return usuario.getUrlFoto();
    }

    @BindingAdapter({"image"})
    public static void loadFoto(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }

    public void setNome(String nome){
        usuario.setNome(nome);
        notifyPropertyChanged(BR.nome);
    }

    public void setEmail(String email){
        usuario.setEmail(email);
        notifyPropertyChanged(BR.email);
    }

    public void setSenha(String senha){
        usuario.setSenha(senha);
        notifyPropertyChanged(BR.senha);
    }

    public void setProfessor(boolean professor){
        usuario.setProfessor(professor);
        notifyPropertyChanged(BR.professor);
    }

    public void setCelular(String celular){
        usuario.setCelular(celular);
        notifyPropertyChanged(BR.celular);
    }

    public void setDataNascimento(Date data){
        usuario.setData_nascimento(data);
        notifyPropertyChanged(BR.dataNascimento);
    }

    public void setSexo(boolean sexo){
        usuario.setSexo(sexo);
        notifyPropertyChanged(BR.sexo);
    }

    public void setUrlFoto(String urlFoto){
        usuario.setUrlFoto(urlFoto);
        notifyPropertyChanged(BR.urlFoto);
    }

}
