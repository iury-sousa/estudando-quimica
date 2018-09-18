package projetotcc.estudandoquimica.view.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import projetotcc.estudandoquimica.R;

public class RespostaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resposta);
        getSupportActionBar().hide();

        ImageView imgResposta = (ImageView)findViewById(R.id.imgResposta);
        TextView resposta = (TextView)findViewById(R.id.resposta);
        Button btnJogarNovamente = (Button)findViewById(R.id.btnJogarNovamente);
        Button btnVoltarTela = (Button)findViewById(R.id.btnVoltarTela);
        Intent intent = getIntent();
        int pontos = intent.getIntExtra("pontos", 0);

        if(intent.hasExtra("acertou")) {
            btnJogarNovamente.setVisibility(View.INVISIBLE);
            btnVoltarTela.setVisibility(View.INVISIBLE);
            boolean acertou = intent.getBooleanExtra("acertou", false);
            if (acertou) {
                imgResposta.setImageResource(R.drawable.acertou);
                resposta.setText("Pontos: " + pontos );
            } else {
                imgResposta.setImageResource(R.drawable.errou);
                resposta.setText("Pontos: " + pontos);
            }

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            });
            thread.start();
        }
        else{
            btnJogarNovamente.setVisibility(View.VISIBLE);
            btnVoltarTela.setVisibility(View.VISIBLE);


            if(pontos >= 4) {
                imgResposta.setImageResource(R.drawable.happy);
                resposta.setText("Parabéns você fez " + pontos + " pontos!");
            }
            else {
                imgResposta.setImageResource(R.drawable.triste);
                resposta.setText("Que pena você fez " + pontos +" Pontos!");
                 }
        }
    }
    public void btnJogarNovamenteOnClick(View v){
        Intent intent = new Intent(this, QuestaoActivity.class);
        startActivity(intent);
        finish();
    }

    public void btnVoltarTelaOnClick(View view) {

        onBackPressed();
    }
}
