package projetotcc.estudandoquimica.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import projetotcc.estudandoquimica.BR;
import projetotcc.estudandoquimica.model.Usuario;

public class CadastrarUsuarioViewModel extends ViewModel {

    public final ObservableField<String> nome = new ObservableField<>();
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> senha = new ObservableField<>();


    public void setUsuarioMutableLiveData(Usuario usuario) {
        this.usuarioMutableLiveData.setValue(usuario);
    }

    private MutableLiveData<Usuario> usuarioMutableLiveData = new MutableLiveData<>();

    private Usuario usuario;
//    private Context context;

    public CadastrarUsuarioViewModel() {
        usuario = new Usuario();
    }

    public MutableLiveData<Usuario> getUsuarioMutableLiveData() {

        Usuario usuario = new Usuario();
        usuario.setNome(nome.get());
        usuario.setEmail(email.get());
        usuario.setSenha(senha.get());

        usuarioMutableLiveData.setValue(usuario);

        return usuarioMutableLiveData;
    }

    public Usuario getUsuario() {

        usuario.setNome(nome.get());
        usuario.setEmail(email.get());
        usuario.setSenha(senha.get());

        return usuario;
    }


    /*    @Bindable
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
    }*/

    public View.OnClickListener onLoginClicked(){

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("Usuario ", getEmail() + getNome());
                Toast.makeText(v.getContext(), "Login username" + email.get() + nome.get(), Toast.LENGTH_SHORT).show();
               // Toast.makeText(v.getContext(), "Usuario: " + getEmail() + getNome(), Toast.LENGTH_SHORT).show();
            //Usuario user = new Usuario(nome.get(),email.get(),senha.get());


            }
        };
    }



}
