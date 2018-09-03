package projetotcc.estudandoquimica.view.offline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import projetotcc.estudandoquimica.model.ConteudoOffline;
import projetotcc.estudandoquimica.R;

public class ConteudoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteudo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        ConteudoOffline cardItem = getIntent().getExtras().getParcelable("conteudo");

        toolbar.setTitle(cardItem.getTitulo());
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
