package projetotcc.estudandoquimica.viewmodel;

import android.content.Context;

import java.util.List;
import java.util.Observable;

import projetotcc.estudandoquimica.model.Turma;

public class TurmaViewModel extends Observable {

    private Turma turma;
    private Context context;

    public TurmaViewModel(Turma turma, Context context) {
        this.turma = turma;
        this.context = context;
    }


}
