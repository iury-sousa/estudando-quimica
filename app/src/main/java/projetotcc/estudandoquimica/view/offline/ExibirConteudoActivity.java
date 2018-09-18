package projetotcc.estudandoquimica.view.offline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import projetotcc.estudandoquimica.model.ConteudoOffline;
import projetotcc.estudandoquimica.R;

public class ExibirConteudoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_conteudo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        ConteudoOffline cardItem = getIntent().getExtras().getParcelable("conteudo");
        toolbar.setTitle(cardItem.getTitulo());

        TextView textView = findViewById(R.id.texto);
       // textView.setText(cardItem.getAssunto());

        if(cardItem.getId() == 1){

        }

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
