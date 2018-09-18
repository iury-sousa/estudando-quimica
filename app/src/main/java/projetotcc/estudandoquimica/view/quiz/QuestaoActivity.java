package projetotcc.estudandoquimica.view.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.model.Questoes;

public class QuestaoActivity extends AppCompatActivity {

        TextView pergunta;
        RadioGroup rgResposta;
        int pontos = 0;
        RadioButton rbResposta1, rbResposta2, rbResposta3, rbResposta4;
        int respostaCerta;

        List<Questoes> questoes = new ArrayList<Questoes>() {
          {
            add(new Questoes("Qual item é uma função organica?", R.id.rbResposta1, "Acetona", "Carbonos", "Hidro", "Oxigenio"));
            add(new Questoes("Qual das Funções organicas possue a Hidroxila OH?", R.id.rbResposta4, "Acetato", "Nitrato", "Fenol", "Alcoois"));
            add(new Questoes("Qual dos itens é um tipo de ligação de atomos?", R.id.rbResposta2, "telefonica", "ionica", "Hidrocarbonetos", "Carbonila"));
            add(new Questoes("Qual tipo de ligação doa seus eletrons?", R.id.rbResposta3, "Ionica", "Metalica", "Covalente", "Todas Anteriores"));
            add(new Questoes("O Octano pertence a qual função Organica?", R.id.rbResposta4, "Acetona", "Aldeido", "Ester", "Hidrocarbonetos"));
            add(new Questoes("Qual função possui a carbonila CHO no Carbono primário?", R.id.rbResposta2, "Eter", "Aldeidos", "Ester", "Acido carboxilico"));
            add(new Questoes("Qual caracteristica faz parte das ligações Ionicas?", R.id.rbResposta1, "Perda e ganho de életrons", "somente perde eletrons", "doa eletrons", "Nenhuma das anteriores"));
          }

        };
       private void carregarQuestao () {

             Collections.shuffle(questoes);
             if (questoes.size() > 0) {
             Questoes q = questoes.remove(0);
             Collections.shuffle(questoes);
             pergunta.setText(q.getPergunta());
             List<String> resposta = q.getRespostas();
             rbResposta1.setText(resposta.get(0));
             rbResposta2.setText(resposta.get(1));
             rbResposta3.setText(resposta.get(2));
             rbResposta4.setText(resposta.get(3));
             respostaCerta = q.getRespostaCerta();
             rgResposta.setSelected(false);



       } else {
          Intent intent = new Intent(this, RespostaActivity.class);
          intent.putExtra("pontos", pontos);
          startActivity(intent);
          finish();
       }
    }

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questao);
        getSupportActionBar().hide();


        pergunta = (TextView) findViewById(R.id.pergunta);
        rbResposta1 = (RadioButton) findViewById(R.id.rbResposta1);
        rbResposta2 = (RadioButton) findViewById(R.id.rbResposta2);
        rbResposta3 = (RadioButton) findViewById(R.id.rbResposta3);
        rbResposta4 = (RadioButton) findViewById(R.id.rbResposta4);
        rgResposta = (RadioGroup) findViewById(R.id.rgResposta);
      carregarQuestao();
    }

        @Override
        protected void onRestart () {
        super.onRestart();
      carregarQuestao();
    }

        public void btnResponderOnClick (View view){

       RadioButton rb = (RadioButton) findViewById(rgResposta.getCheckedRadioButtonId());
       int selectItem = rgResposta.getCheckedRadioButtonId();

        if(selectItem > 0) {
            Intent intent = new Intent(this, RespostaActivity.class);
            if (rgResposta.getCheckedRadioButtonId() == respostaCerta) {
                intent.putExtra("acertou", true);
                pontos++;
            } else intent.putExtra("acertou", false);
            intent.putExtra("pontos", pontos);
            startActivity(intent);
            //rb.setChecked(false);
            rgResposta.clearCheck();
        } else{
           Toast.makeText(this, "Selecione uma resposta",Toast.LENGTH_SHORT).show();}
          //  RadioGroup rgRespostas = (RadioGroup)findViewById(R.id.rgResposta);
        //    Intent intent = new Intent(this, resposta.class);
       //     intent.putExtra("acertou", rgRespostas.getCheckedRadioButtonId() == R.id.rbResposta2);
       //     startActivity(intent);

        }

}
