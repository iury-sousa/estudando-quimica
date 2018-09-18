package projetotcc.estudandoquimica;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import projetotcc.estudandoquimica.view.compartilhado.CadastrarPublicacaoActivity;

public class UploadFiles {

    private Context context;
    private static String ID_UPLOAD = "UPLOAD";
    private static int ID_NOTIfICACAO = 1;

    private static String url;
    private NotificationManagerCompat notificationManager;
    NotificationCompat.Builder mBuilder;
    //Bitmap icone;
    public UploadFiles(Context context) {

        this.context = context;
//        icone = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.quimica);
    }

    public void upload(Bitmap imagem, String url, DatabaseReference reference) {

        if (imagem != null) {
//            final ProgressDialog progressDialog = new ProgressDialog(context);
//            progressDialog.setTitle("Enviando arquivo...");
//            progressDialog.setCancelable(false);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            progressDialog.show();

            StorageReference storageReference = FirebaseStorage.getInstance().
                    getReference(url);
            Intent intent = new Intent();

            final PendingIntent pendingIntent = PendingIntent.getActivity(
                    context, 0, intent, 0);

            notificationManager = NotificationManagerCompat.from(context);


            mBuilder =  new NotificationCompat.Builder(context, ID_UPLOAD)
                            .setSmallIcon(R.drawable.logo_quimica_no_title)
                            .setColorized(true)
                            .setContentTitle("Enviando imagem")
                            .setContentText("Upload em progresso...")
                            .setColor(Color.parseColor("#8c28ff"))
                            //.setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);


            mBuilder.setContentIntent(pendingIntent);

            final StorageReference ref = storageReference.child(new StringBuilder("images/").append(UUID.randomUUID().toString()).toString());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imagem.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = ref.putBytes(data);

                    uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            Log.i("Upload está ",  progress + "% concluido");

                            int currentprogress = (int) progress;
                            mBuilder.setProgress(100, currentprogress, false);
                            notificationManager.notify(ID_NOTIfICACAO, mBuilder.build());
                            //progressDialog.setProgress(currentprogress);
                        }
                    }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {

                        Log.i("Upload", "Pausado");
                    }
                    }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();

                               // progressDialog.dismiss();
                                reference.child("imagens/url").setValue(downloadUri.toString());

                                mBuilder.setContentText("Upload concluido")
                                        .setProgress(0,0,false);
                                notificationManager.notify(ID_NOTIfICACAO, mBuilder.build());

                                ((CadastrarPublicacaoActivity) context).onBackPressed();

                            } else {
                                Toast.makeText(context, "Falha no UPLOAD", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Falha no UPLOAD", e.getMessage());
                        }
                    });

        }
    }
}
