package projetotcc.estudandoquimica.viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import projetotcc.estudandoquimica.FirebaseQueryLiveData;
import projetotcc.estudandoquimica.view.turma.GerarCodigoTurma;
import projetotcc.estudandoquimica.model.Turma;

public class TurmaViewModel extends ViewModel{

    private Turma turma;

    public final ObservableField<String> nome = new ObservableField<>();
    public final ObservableField<String> nomeProfessor = new ObservableField<>();
    public final ObservableField<String> data = new ObservableField<>();
    public final ObservableField<String> codeTurma = new ObservableField<>();

    private static final DatabaseReference turmas =
            FirebaseDatabase.getInstance().getReference("turmas");

    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(turmas);

    private static final DatabaseReference ESTUDANTE =
            FirebaseDatabase.getInstance().getReference("estudante_turmas");

    private FirebaseQueryLiveData liveDataEstudante = new FirebaseQueryLiveData(ESTUDANTE);

    public void setTurma(Turma turma, Context context) {


        nome.set(turma.getNome());

        if(turma.getProfessor().getNome() != null) {

            nomeProfessor.set("Prof.: " + turma.getProfessor().getNome());
        }
        data.set("Criado em: " + turma.getData_criacao());
        codeTurma.set(turma.getCodeTurma());
    }

//    public Turma getTurma() {
//
//        Turma t = new Turma();
//
//        t.setNome(nome.get());
//        t.setAdministradorTurma(nomeProfessor.get());
//        t.setData_criacao(data.get());
//
//        return turma;
//    }

    public TurmaViewModel() {
        this.turma = new Turma();

    }

//    @BindingAdapter("animatedVisibility")
//    public static void setVisibility(final View view,
//                                     final int visibility) {
//        // Were we animating before? If so, what was the visibility?
//        Integer endAnimVisibility =
//                (Integer) view.getTag(R.id.cardView);
//        int oldVisibility = endAnimVisibility == null
//                ? view.getVisibility()
//                : endAnimVisibility;
//
//        if (oldVisibility == visibility) {
//            // just let it finish any current animation.
//            return;
//        }
//
//        boolean isVisibile = oldVisibility == View.VISIBLE;
//        boolean willBeVisible = visibility == View.VISIBLE;
//
//        view.setVisibility(View.VISIBLE);
//        float startAlpha = isVisibile ? 1f : 0f;
//        if (endAnimVisibility != null) {
//            startAlpha = view.getAlpha();
//        }
//        float endAlpha = willBeVisible ? 1f : 0f;
//
//        // Now create an animator
//        ObjectAnimator alpha = ObjectAnimator.ofFloat(view,
//                View.ALPHA, startAlpha, endAlpha);
//        alpha.setAutoCancel(true);
//
//        alpha.addListener(new AnimatorListenerAdapter() {
//            private boolean isCanceled;
//
//            @Override
//            public void onAnimationStart(Animator anim) {
//                view.setTag(R.id.cardView, visibility);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator anim) {
//                isCanceled = true;
//            }
//
//            @Override
//            public void onAnimationEnd(Animator anim) {
//                view.setTag(R.id.cardView, null);
//                if (!isCanceled) {
//                    view.setAlpha(1f);
//                    view.setVisibility(visibility);
//                }
//            }
//        });
//        alpha.start();
//    }



    @NonNull
    public MutableLiveData<DataSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }

    public LiveData<DataSnapshot> getDataSnapshotLiveDataEstudante() {
        return liveDataEstudante;
    }



    public Turma inserir(String nome){

        FirebaseAuth auth = FirebaseAuth.getInstance();
        Turma turma = new Turma();

        @SuppressLint
                ("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String strDate = dateFormat.format(date);

        turma.setNome(nome);
        turma.setData_criacao(strDate);
        turma.setAdministradorTurma(auth.getCurrentUser().getUid());
        turma.setCodeTurma(GerarCodigoTurma.gerar(8));


//        turma.setProfessor(new Usuario(auth.getCurrentUser().getUid(),
//                auth.getCurrentUser().getDisplayName(), auth.getCurrentUser().getEmail()));

        turmas.push().setValue(turma);

        return turma;
    }

    public void deletar(Turma turma){

        turmas.child(turma.getId()).removeValue(new DatabaseReference.CompletionListener() {

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                DatabaseReference reference = FirebaseDatabase.getInstance()
                        .getReference("estudantes/" + turma.getId());

                reference.removeValue();

                reference = FirebaseDatabase.getInstance().getReference("estudante_turmas");

                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        dataSnapshot.child(turma.getId()).getRef().removeValue();
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

            }

        });

    }

    public String atualizar(Turma turma){
        Map<String, Object> map = turma.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(turma.getId(), map);

        turmas.updateChildren(childUpdates);

        return null;
    }
    
}
