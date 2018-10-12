package projetotcc.estudandoquimica.view.offline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.model.ConteudoOffline;

public class ExibirConteudoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_conteudo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        Bundle b = getIntent().getExtras();

        if(b != null) {

            toolbar.setTitle(getIntent().getExtras().getString("titulo"));
            ListaArquivoOffline offline = new ListaArquivoOffline();

            ConteudoOffline off = offline.getConteudoOffiline(0);

            Log.i("Contem", String.valueOf(off.getTexto().contains("[*link*]")));

//            for (int i = 0; i <= off.getTexto().length(); i++){
//
//                if(off.getTexto().charAt(i) == '[' && off.getTexto().charAt(i+1) == '*'){

//                    int ini = off.getTexto().indexOf("[*");
//                    int fim = off.getTexto().lastIndexOf("*]");
//
//                    String result = off.getTexto().substring(ini + 2, fim);
//
//                    Log.i("Contem", result);
//
//                    String r = off.getTexto().replace(result, "");
//                    r = r.replace("[*", "");
//                    r = r.replace("*] ", "");

                    //Log.i("Contem", r);
//                }
//
//            }

            for (int i = 0; i < off.getTexto().length(); i++) {
                char charAt = off.getTexto().charAt(i);
                char charAt2 = off.getTexto().charAt(i + 1);
                if (charAt == '[' && charAt2 == '*' ) {
                    Log.i("Contem", "true");
                }
            }


            TextView textView = findViewById(R.id.texto);
            textView.setText(off.getTexto());

        }



//        if(cardItem.getId() == 1){
//
//        }

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }
}
