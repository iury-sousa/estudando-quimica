package projetotcc.estudandoquimica;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import projetotcc.estudandoquimica.model.Usuario;

public class VerificarUsuario {

    private static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static boolean isProfessor;


    public static boolean verificarUsuario(){

       // isProfessor = false;

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("usuarios/" + user.getUid() + "/professor");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                isProfessor = dataSnapshot.getValue(Boolean.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return isProfessor;
    }

    public static boolean verificarUsuarioTurma(){

        isProfessor = false;

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("turmas");

        ref.limitToFirst(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String id = dataSnapshot.getRef().getKey();

                DatabaseReference ref = FirebaseDatabase
                            .getInstance().getReference("turmas/" + id);

                ref.child("administradorTurma").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String idAdmin = dataSnapshot.getValue(String.class);

                        isProfessor = idAdmin.equals(user.getUid());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return isProfessor;
    }


    public static void verificarTipoUsuario(CallbackVerificarUsuario<Boolean> callback){

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("usuarios/" + user.getUid() + "/professor");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //isProfessor = dataSnapshot.getValue(Boolean.class);
                callback.callback(dataSnapshot.getValue(Boolean.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static void verificarCadastroUsuario(CallbackVerificarUsuario<Boolean> callback, String idUsuario){
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("usuarios/" + idUsuario);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue() == null) {

                    callback.callback(false);
                }else{
                    callback.callback(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface CallbackVerificarUsuario<T> {

        void callback(T retorno);
    }

}
