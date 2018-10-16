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

    private TextView pergunta;
    private RadioGroup rgResposta;
    private int pontos = 0;
    private RadioButton rbResposta1, rbResposta2, rbResposta3, rbResposta4;
    private int respostaCerta;

    List<Questoes> questoes = new ArrayList<Questoes>() {
        {
            add(new Questoes("Qual item é uma função orgânica?", R.id.rbResposta1, "Acetona", "Carbonos", "Hidro", "Oxigênio"));
            add(new Questoes("Qual das Funções orgânicas possuem a Hidroxila OH?", R.id.rbResposta4, "Acetato", "Nitrato", "Benzeno", "Álcoois"));
            add(new Questoes("Qual dos itens é um tipo de ligação de átomos?", R.id.rbResposta2, "telefônica", "iônica", "Hidrocarbonetos", "Carbonila"));
            add(new Questoes("Qual tipo de ligação doa seus elétrons?", R.id.rbResposta3, "Iônica", "Metálica", "Covalente", "Todas Anteriores"));
            add(new Questoes("O Octano pertence a qual função Orgânica?", R.id.rbResposta4, "Acetona", "Aldeído", "Ester", "Hidrocarbonetos"));
            add(new Questoes("Qual função possui a carbonila CHO no Carbono primário?", R.id.rbResposta2, "Éter", "Aldeídos", "Ester", "Acido carboxílico"));
            add(new Questoes("Quais são as partículas fundamentais que compõe um átomo?", R.id.rbResposta1, "elétrons, nêutrons e prótons", "Elétrons e prótons", "Nêutrons", "Prótons e nêutrons"));
            add(new Questoes("Os elementos Hidrogênio, mercúrio e prata são representados por qual símbolo na tabela periódica?", R.id.rbResposta4, "H,M e P", "H, Hg e Au", "H, Ag e Mn", "H, Hg, Ag"));
            add(new Questoes("Qual regra diz que os elementos químicos devem conter 8 elétrons na camada de valência?", R.id.rbResposta2, "regra de nomenclatura ", "regra do octeto", "regra dos átomos", "regra dos elétrons"));
            add(new Questoes("Qual nome recebe a mudança do estado liquido para gasoso?", R.id.rbResposta1, "Condensação", "Solidificação", "Sublimação", "Vaporização"));
            add(new Questoes("Sublimação é que tipo de mudança de estado físico da água?", R.id.rbResposta3, "liquido para gasoso", "líquido para sólido", "solido para gasoso", "Sólido para líquido"));
            add(new Questoes("Qual o principal composto estudado pela química organica?", R.id.rbResposta1, "Carbono", "Cálcio", "Cobalto", "Cobre"));
            add(new Questoes("Qual o nome do composto CH4 segundo as regras de nomenclatura do IUPAC?", R.id.rbResposta1, "Metano", "Etano", "Etanol", "Metanol"));
            add(new Questoes("Qual característica faz parte das ligações Iônicas?", R.id.rbResposta1, "Perda e ganho de elétrons", "somente perde elétrons", "doa elétrons", "Nenhuma das anteriores"));
        }

    };

    private void carregarQuestao() {

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
            overridePendingTransition(R.anim.zoom_in, R.anim.exit_bottom);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    protected void onRestart() {
        super.onRestart();
        carregarQuestao();
    }

    public void btnResponderOnClick(View view) {

        RadioButton rb = (RadioButton) findViewById(rgResposta.getCheckedRadioButtonId());
        int selectItem = rgResposta.getCheckedRadioButtonId();

        if (selectItem > 0) {
            Intent intent = new Intent(this, RespostaActivity.class);
            if (rgResposta.getCheckedRadioButtonId() == respostaCerta) {
                intent.putExtra("acertou", true);
                pontos++;
            } else {
                intent.putExtra("acertou", false);
            }

            intent.putExtra("pontos", pontos);
            startActivity(intent);
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            //rb.setChecked(false);
            rgResposta.clearCheck();
        } else {
            Toast.makeText(this, "Selecione uma resposta", Toast.LENGTH_SHORT).show();
        }
        //  RadioGroup rgRespostas = (RadioGroup)findViewById(R.id.rgResposta);
        //    Intent intent = new Intent(this, resposta.class);
        //     intent.putExtra("acertou", rgRespostas.getCheckedRadioButtonId() == R.id.rbResposta2);
        //     startActivity(intent);

    }

}
