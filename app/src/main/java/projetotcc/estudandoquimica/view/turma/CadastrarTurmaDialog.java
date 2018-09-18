package projetotcc.estudandoquimica.view.turma;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import projetotcc.estudandoquimica.R;

public class CadastrarTurmaDialog extends DialogFragment {

    private String nome;
    private int opcao;

    public int getOpcao() {
        return opcao;
    }

    public void setOpcao(int opcao) {
        this.opcao = opcao;
    }

    public interface CadastrarTurmaListener {
        void onDialogPositiveClick(CadastrarTurmaDialog dialog);
        void onDialogNegativeClick(CadastrarTurmaDialog dialog);
    }

    CadastrarTurmaListener listener;

/*    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        try {

            listener = (CadastrarTurmaListener) childFragment;
        } catch (ClassCastException e) {

            throw new ClassCastException(childFragment.toString()
                    + " must implement NoticeDialogListener");
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {

           listener = (CadastrarTurmaListener) context;
        } catch (ClassCastException e) {

            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        android.app.Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_cadastrar_turma, container, false);

        Button botaoCancelar = view.findViewById(R.id.botao_cancelar);
        Button botaoSalvar = view.findViewById(R.id.botao_salvar);


        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView turma = getDialog().findViewById(R.id.turma);

                if (TextUtils.isEmpty(turma.getText().toString())) {
                    turma.setError(getString(R.string.error_field_required));
                }else{

                    nome = turma.getText().toString();
                }
                listener.onDialogPositiveClick(CadastrarTurmaDialog.this);


            }
        });

        botaoCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDialogNegativeClick(CadastrarTurmaDialog.this);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        android.app.Dialog dialog = getDialog();
        if (dialog != null) {
            Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    public String getNome(){

        return this.nome;
    }
}
