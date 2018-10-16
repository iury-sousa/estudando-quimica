package projetotcc.estudandoquimica.view.offline;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.github.chrisbanes.photoview.PhotoView;

import projetotcc.estudandoquimica.R;

public class VisualizarImagemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_imagem);

        PhotoView view = findViewById(R.id.imagem);

        Bundle b = getIntent().getExtras();

        if(b != null){

            Resources resources = getResources();
            int img = resources.getIdentifier(getIntent()
                    .getExtras().getString("imagem"), "drawable", getPackageName());

            view.setImageResource(img);

        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }
}
