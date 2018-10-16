package projetotcc.estudandoquimica.view.offline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.componentesPersonalizados.TextViewEx;
import projetotcc.estudandoquimica.model.ConteudoOffline;

public class ExibirConteudoActivity extends AppCompatActivity {


    private ListaArquivoOffline listaArquivoOffline;
    private ConteudoOffline conteudoOffline;
    private TextViewEx textView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibir_conteudo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        ImageView iv = findViewById(R.id.image);

        linearLayout = findViewById(R.id.relative);
        listaArquivoOffline = new ListaArquivoOffline();
        Bundle b = getIntent().getExtras();

        if (b != null) {

            toolbar.setTitle(getIntent().getExtras().getString("titulo"));
            int idConteudo = getIntent().getExtras().getInt("id");
            int idAssunto = getIntent().getExtras().getInt("idAssunto");

            if(idConteudo == 1) {

                iv.setImageResource(getImageDrawableResId("tabela_pe"));

            }if(idConteudo == 2){

                iv.setImageResource(getImageDrawableResId("lig_quimica"));

            }else if(idConteudo == 3){
                iv.setImageResource(getImageDrawableResId("organica_wall"));
            }

            try {
                conteudoOffline = listaArquivoOffline.getConteudoOffiline(idAssunto, idConteudo);


                //textView = findViewById(R.id.texto);

                initializeText(conteudoOffline.getTexto());

            }catch (Exception e){

                Log.e("Erro", e.getMessage());
            }

        }


//        if(cardItem.getId() == 1){
//
//        }

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    private void initializeText(String texto){

        int posicaoFim = 0;
        String tempTexto = "";
        List<String> textos = new ArrayList<>();
        List<String> imagens = new ArrayList<>();

        for (int i = 0; i < texto.length(); i++) {

            if (texto.charAt(i) == '[' && texto.charAt(i + 1) == '*') {

                for (int j = i; j < texto.length(); j++){

                    if(texto.charAt(j) == '*' && texto.charAt(j + 1) == ']'){

                        posicaoFim = j + 2;

                        String teste = texto.substring(i, posicaoFim);
                        teste = teste.replace("[*", "");
                        teste = teste.replace("*]", "");

                        //linearLayout.addView(getImageView(teste));
                        imagens.add(teste);
                        i = posicaoFim;
                        break;
                    }
                }
                textos.add(tempTexto.trim());
                //linearLayout.addView(getTextView(tempTexto));
                tempTexto = "";

            } else {

                tempTexto += texto.charAt(i);
            }

        }

        for (int k = 0; k < textos.size(); k++){

            linearLayout.addView(getTextView("\t\t\t\t\t\t" + textos.get(k)));
            linearLayout.addView(getImageView(imagens.get(k)));
        }

        if(!tempTexto.trim().isEmpty()){
            //textos.add(tempTexto);
            linearLayout.addView(getTextView(tempTexto));
        }

        //textView.setText(texto, true);

        //linearLayout.addView(getButton());

    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private TextView getTextView(String texto){

        TextView textViewEx = new TextViewEx(this);
        //textViewEx.setId(View.generateViewId());
        textViewEx.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        textViewEx.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.tamanha_texto));
        textViewEx.setTextColor(Color.BLACK);
        textViewEx.setLineSpacing(0, 1.25f);
        textViewEx.setTypeface(Typeface.SANS_SERIF);
        //textViewEx = findViewById(R.id.texto);
        textViewEx.setText(texto);

        return textViewEx;
    }

    private ImageView getImageView(String imagem){

        ImageView iv = new ImageView(this);
        iv.setAdjustViewBounds(true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(0,(int)convertDpToPixel(15, this),0, (int)convertDpToPixel(15, this));
        iv.setLayoutParams(params);
        iv.setImageResource(getImageDrawableResId(imagem));

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ExibirConteudoActivity.this, VisualizarImagemActivity.class);
                it.putExtra("imagem", imagem);
                startActivity(it);
            }
        });

        return iv;

    }

    private Button getButton(){

        Button b = new Button(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, (int) convertDpToPixel(42, this));

        params.setMargins(0,(int)convertDpToPixel(10, this),0, 0);
        b.setLayoutParams(params);

        b.setText("Prosseguir");
        b.setBackground(getDrawable(R.drawable.seletor_button_login));
        b.setTextColor(Color.WHITE);
        b.setTypeface(Typeface.DEFAULT_BOLD);

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


            }
        });

        return b;

    }

    public int getImageDrawableResId(String imageId) {
        Resources resources = getResources();
        return resources.getIdentifier(imageId, "drawable", getPackageName());
    }

    public static class ViewIdGenerator {
        private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

        @SuppressLint("NewApi")
        public static int generateViewId() {

//            if (Build.VERSION.SDK_INT < 17) {
//                for (;;) {
//                    final int result = sNextGeneratedId.get();
//                    // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
//                    int newValue = result + 1;
//                    if (newValue > 0x00FFFFFF)
//                        newValue = 1; // Roll over to 1, not 0.
//                    if (sNextGeneratedId.compareAndSet(result, newValue)) {
//                        return result;
//                    }
//                }
//            } else {
                return View.generateViewId();
            //}

        }
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
