package projetotcc.estudandoquimica.view.compartilhado;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import projetotcc.estudandoquimica.MyFirebaseMessagingService;
import projetotcc.estudandoquimica.R;
import projetotcc.estudandoquimica.UploadFiles;
import projetotcc.estudandoquimica.componentesPersonalizados.DividerItemDecoration;
import projetotcc.estudandoquimica.databinding.ActivityCadastrarPublicacaoBinding;
import projetotcc.estudandoquimica.model.Publicacao;
import projetotcc.estudandoquimica.model.Turma;
import projetotcc.estudandoquimica.model.Usuario;
import projetotcc.estudandoquimica.view.turma.PesquisarTurmaActivity;
import projetotcc.estudandoquimica.viewmodel.CadastrarPublicacaoViewModel;
import projetotcc.estudandoquimica.viewmodel.PublicacaoViewModel;

public class CadastrarPublicacaoActivity extends AppCompatActivity {

    private static final int IMAGE_GALLERY_REQUEST = 1;
    private Bitmap bitmap;
    private ActivityCadastrarPublicacaoBinding binding;
    private PublicacaoViewModel viewModel;
    private InputStream inputStream;
    private ArrayList<String> idTurmas;
    private ArrayList<String> nomeTurmas;
    private static final int RESULT_TURMAS = 2;
    private Publicacao publicacao;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cadastrar_publicacao);
        idTurmas = new ArrayList<>();
        getSupportActionBar().setTitle("Criar publicação");

        Bundle b = getIntent().getExtras();


        if (b != null) {

            publicacao = (Publicacao) getIntent().getExtras().getParcelable("pub");
            publicacao.setAdmin(getIntent().getExtras().getParcelable("administrador"));
            getSupportActionBar().setTitle("Alterar publicação");

        }

        viewModel = ViewModelProviders.of(this).get(PublicacaoViewModel.class);
        binding.setPub(viewModel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (publicacao != null) {

            viewModel.setPublicacao(publicacao, getApplicationContext());

        }


        binding.btnImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarGaleria();
            }
        });

        binding.btnAddTurma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivityForResult(new Intent(
                        CadastrarPublicacaoActivity.this,
                        PesquisarTurmaActivity.class), RESULT_TURMAS);

            }
        });
    }

    public void selecionarGaleria() {
        Intent abrirGaleria = new Intent(Intent.ACTION_GET_CONTENT);
        abrirGaleria.setType("image/*");
        abrirGaleria.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(abrirGaleria, IMAGE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {

            carregarImagemGaleria(data);

        } else if (requestCode == RESULT_TURMAS && resultCode == RESULT_OK) {

            if (data != null) {
                idTurmas = data.getStringArrayListExtra("idTurmas");
                nomeTurmas = data.getStringArrayListExtra("nomeTurmas");

                if (TextUtils.isEmpty(binding.textoPublicacao.getText().toString().trim())
                        && TextUtils.isEmpty(binding.titulo.getText().toString().trim())
                        && bitmap == null) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setMessage("Insira pelo menos um texto ou uma imagem para poder cadastrar a publicação")
                            .setTitle("Atenção!")

                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {


                                }
                            })
                            .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    notificarUsuarios(inserir(bitmap));
                }
            }

        }


    }

    public void carregarImagemGaleria(Intent data) {

        InputStream stream = null;
        try {
            if (bitmap != null) {
                bitmap.recycle();
            }
            stream = getContentResolver().openInputStream(data.getData());
            bitmap = BitmapFactory.decodeStream(stream);

            inputStream = stream;
            final ImageView imageView = binding.imagemConteudo;
            viewModel.imagemUrl.set(data.getData().toString());
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(bitmap);
//            UploadFiles f = new UploadFiles(CadastrarPublicacaoActivity.this);
//
//            f.upload(bitmap, "publicacoes/iury");


        } catch (FileNotFoundException e) {
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

    public Publicacao inserir(Bitmap bitmap) {

        Publicacao publicacao = new Publicacao();


        Usuario usuario = new Usuario();
        usuario.setId(auth.getCurrentUser().getUid());
        usuario.setNome(auth.getCurrentUser().getDisplayName());
        usuario.setUrlFoto(auth.getCurrentUser().getPhotoUrl().toString());
//        @SuppressLint
//                ("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date date = new Date();
//        String strDate = dateFormat.format(date);

        publicacao.setAdmin(usuario);
        publicacao.setTitulo(viewModel.titulo.get());
        publicacao.setTextoPublicacao(viewModel.textoPublicacao.get());

        List<Turma> turmas = new ArrayList<>();

        HashMap<String, Object> mapList = new HashMap<>();


        if (idTurmas != null) {

            for (int i = 0; i < idTurmas.size(); i++) {

                mapList.put(idTurmas.get(i), true);

            }
        }


        Map<String, Object> map = publicacao.toMap();
        map.put("listaTurmas", mapList);

        Timestamp dataDeHoje = new Timestamp(System.currentTimeMillis());
        map.put("timestamp", dataDeHoje.getTime());

        map.put("timestamp_admin", String.valueOf(dataDeHoje.getTime()) + "_" + auth.getCurrentUser().getUid());

        viewModel.getPublicacaoRef().push().setValue(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError,
                                   @NonNull DatabaseReference databaseReference) {

                if (idTurmas != null) {
                    for (int i = 0; i < idTurmas.size(); i++) {

                        DatabaseReference reference =
                                FirebaseDatabase.getInstance()
                                        .getReference("turmas/" + idTurmas.get(i));

                        reference.child("listaPublicacoes").child(databaseReference.getKey()).setValue(true);

                        DatabaseReference referenceTurma = FirebaseDatabase.getInstance()
                                .getReference("turmas/" + idTurmas.get(i) + "/listaPublicacoes");

                        referenceTurma.child(databaseReference.getKey()).setValue(true);

                    }
                }

                if (bitmap == null) {
                    onBackPressed();
                }
                UploadFiles f = new UploadFiles(CadastrarPublicacaoActivity.this);

                f.upload(bitmap, "publicacoes/" + auth.getCurrentUser().getUid()
                        + "/" + databaseReference.getKey() + "/", databaseReference);

            }
        });
        return publicacao;
    }

    public void alterar() {

        publicacao.setTitulo(viewModel.titulo.get());
        publicacao.setTextoPublicacao(viewModel.textoPublicacao.get());

        HashMap<String, Object> result = new HashMap<>();
        result.put("titulo", publicacao.getTitulo());
        result.put("textoPublicacao", publicacao.getTextoPublicacao());


        viewModel.getPublicacaoRef().child(publicacao.getId()).updateChildren(result, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (bitmap == null) {
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
        overridePendingTransition(R.anim.zoom_in, R.anim.exit_bottom);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_conteudo, menu);
        menu.removeItem(R.id.action_config);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:

                finish();
                overridePendingTransition(R.anim.zoom_in, R.anim.exit_bottom);
                return true;

            case R.id.action_publicacao:

                if(publicacao != null){

                    alterar();


                }else if(idTurmas.size() > 0) {


                    notificarUsuarios(inserir(bitmap));
//                    Intent it = new Intent(
//                            CadastrarPublicacaoActivity.this,
//                            PesquisarTurmaActivity.class);
//                    it.putStringArrayListExtra("id_turmas", idTurmas);
//
//                    startActivityForResult(it, RESULT_TURMAS);
//
//                    overridePendingTransition(R.anim.enter, R.anim.exit);
                } else {

                    startActivityForResult(new Intent(
                            CadastrarPublicacaoActivity.this,
                            PesquisarTurmaActivity.class), RESULT_TURMAS);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }


                return true;

            default:
                return false;
        }

    }

    private final String SENDER_ID = "xxxxxxxxxx";
    private Random random = new Random();

    private void notificarUsuarios(Publicacao publicacao){

//        String[] bodyNotification = new String[]{ publicacao.getAdmin().getNome()};
//        new NotificationAsync().execute(
//                publicacao,
//                "Publicou"
//        );
    }


    private class NotificationAsync extends AsyncTask<Object, Void, Void>{

        @Override
        protected Void doInBackground(Object... voids) {

            try {

                Publicacao p = (Publicacao) voids[0];

                URL obj = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization", "key=AIzaSyCIBkcjl_XsELe_CeVI-vbWO20ogOp25OY");
                con.setRequestProperty("Content-Type", "application/json");


                JSONObject notificacao = new JSONObject();
                notificacao.put("title", "Nova publicação disponível" );
                notificacao.put("body", p.getTextoPublicacao());
                notificacao.put("sound", "default");

                JSONObject data = new JSONObject();
                data.put("imagem", p.getImagemUrl());
                data.put("foto", p.getAdmin().getUrlFoto());

                JSONObject object = new JSONObject();
                object.put("to", "/topics/Iury");
                object.put("collapse_key", "type_a");
                object.put("notification", notificacao);
                object.put("data", data);

                con.setDoOutput(true);

                OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
                os.write(object.toString());
                os.flush();
                os.close();
                int responseCode = con.getResponseCode();

            }catch (Exception e){

                e.printStackTrace();
                //Toast.makeText(CadastrarPublicacaoActivity.this, "Falha" + e.getMessage() + e.getClass(), Toast.LENGTH_SHORT).show();
            }

            return null;
        }
    }

}
