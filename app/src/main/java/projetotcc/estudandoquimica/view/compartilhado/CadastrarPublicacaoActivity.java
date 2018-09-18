package projetotcc.estudandoquimica.view.compartilhado;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.UploadFiles;
import projetotcc.estudandoquimica.databinding.ActivityCadastrarPublicacaoBinding;
import projetotcc.estudandoquimica.model.Publicacao;
import projetotcc.estudandoquimica.model.Usuario;
import projetotcc.estudandoquimica.view.turma.PesquisarTurmaActivity;
import projetotcc.estudandoquimica.viewmodel.CadastrarPublicacaoViewModel;
import projetotcc.estudandoquimica.viewmodel.PublicacaoViewModel;

public class CadastrarPublicacaoActivity extends AppCompatActivity {

    private static final int IMAGE_GALLERY_REQUEST = 1;
    private Bitmap bitmap;
    private ActivityCadastrarPublicacaoBinding binding;
    private PublicacaoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cadastrar_publicacao);




        viewModel = ViewModelProviders.of(this).get(PublicacaoViewModel.class);
        binding.setPub(viewModel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle("Criar publicação");

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
    InputStream inputStream;
    public void carregarImagemGaleria(Intent data){

        InputStream stream = null;
        try {
            if(bitmap != null){
                bitmap.recycle();
            }
            stream = getContentResolver().openInputStream(data.getData());
            bitmap = BitmapFactory.decodeStream(stream);

            inputStream = stream;
            final ImageView imageView = binding.imagemConteudo;
            viewModel.imagemUrl.set("true");
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(bitmap);
//            UploadFiles f = new UploadFiles(CadastrarPublicacaoActivity.this);
//
//            f.upload(bitmap, "publicacoes/iury");


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

//    private void upload() {
//        if (bitmap!=null) {
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//
//            StorageReference storageReference = FirebaseStorage.getInstance().
//                    getReference("publicacoes/");
//
//            final StorageReference ref = storageReference.child(new StringBuilder("images/").append(UUID.randomUUID().toString()).toString());
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] data = baos.toByteArray();
//
//            UploadTask uploadTask = ref.putBytes(data);
//
//            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                @Override
//                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                    if (!task.isSuccessful()) {
//                        throw task.getException();
//                    }
//
//                    return ref.getDownloadUrl();
//                }
//            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                @Override
//                public void onComplete(@NonNull Task<Uri> task) {
//                    if (task.isSuccessful()) {
//                        Uri downloadUri = task.getResult();
//                        progressDialog.dismiss();
//
//                        String url = downloadUri.toString();
//                    } else {
//                        Toast.makeText(CadastrarPublicacaoActivity.this, "Falha no UPLOAD", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }).addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    progressDialog.setMessage("Enviando: ");
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    progressDialog.dismiss();
//                    Toast.makeText(CadastrarPublicacaoActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }

    public void inserir(Bitmap bitmap){

        Publicacao publicacao = new Publicacao();


        FirebaseAuth auth = FirebaseAuth.getInstance();
        Usuario usuario = new Usuario();
        usuario.setId("-LLumZs9sVwOTqlQqRHU");
        @SuppressLint
                ("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String strDate = dateFormat.format(date);

        publicacao.setAdmin(usuario);
        publicacao.setTitulo(viewModel.titulo.get());
        publicacao.setTextoPublicacao(viewModel.textoPublicacao.get());
        publicacao.setDataPublicacao(strDate);

        Map<String, Object> map = publicacao.toMap();

        viewModel.getPublicacaoRef().push().setValue(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError,
                                   @NonNull DatabaseReference databaseReference) {

                if(bitmap == null){
                    onBackPressed();
                }
                UploadFiles f = new UploadFiles(CadastrarPublicacaoActivity.this);

                f.upload(bitmap, "publicacoes/" + auth.getCurrentUser().getUid()
                        + "/" + databaseReference.getKey() + "/", databaseReference);
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.exit_bottom  );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_conteudo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();
                overridePendingTransition(R.anim.zoom_in, R.anim.exit_bottom  );
                return true;

            case R.id.action_publicacao:

                inserir(bitmap);
                return true;

            default:
                return false;
        }

    }



}
