package projetotcc.estudandoquimica;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import projetotcc.estudandoquimica.view.offline.VisualizarImagemActivity;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        TextView iury = findViewById(R.id.iury);
        TextView thais = findViewById(R.id.thais);

        iury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] email = new String[]{iury.getText().toString()};
                composeEmail(email);
            }
        });

        thais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] email = new String[]{thais.getText().toString()};
                composeEmail(email);
            }
        });

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

    public void composeEmail(String[] addresses) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
