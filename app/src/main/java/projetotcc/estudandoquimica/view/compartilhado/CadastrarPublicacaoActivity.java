package projetotcc.estudandoquimica.view.compartilhado;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.databinding.ActivityCadastrarPublicacaoBinding;
import projetotcc.estudandoquimica.view.turma.PesquisarTurmaActivity;

public class CadastrarPublicacaoActivity extends AppCompatActivity {

    private static final int IMAGE_GALLERY_REQUEST = 1;
    private Bitmap bitmap;
    private ActivityCadastrarPublicacaoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cadastrar_publicacao);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        binding.btnImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarGaleria();
            }
        });

        binding.btnAddTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CadastrarPublicacaoActivity.this, PesquisarTurmaActivity.class));
            }
        });
    }

    public void selecionarGaleria(){
        Intent abrirGaleria = new Intent(Intent.ACTION_GET_CONTENT);
        abrirGaleria.setType("image/*");
        abrirGaleria.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(abrirGaleria,IMAGE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {

            carregarImagemGaleria(data);
        }
    }

    public void carregarImagemGaleria(Intent data){
        InputStream stream = null;

        try {
            if(bitmap != null){
                bitmap.recycle();
            }

            stream = getContentResolver().openInputStream(data.getData());
            bitmap = BitmapFactory.decodeStream(stream);

            final ImageView imageView = binding.imagemConteudo;

            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(bitmap);

        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
