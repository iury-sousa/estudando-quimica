package projetotcc.estudandoquimica;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import projetotcc.estudandoquimica.view.usuario.LoginActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "FirebaseService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.i(TAG, remoteMessage.getData().toString());




        sendNotification(remoteMessage);
        Log.i(TAG, "From: " + remoteMessage.getFrom());


        if (remoteMessage.getData().size() > 0) {
            Log.i(TAG, "Message data payload: " + remoteMessage.getData());
            String mensagem = remoteMessage.getData().get("text");
        }


    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

    private void scheduleJob() {

//        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
//        Job myJob = dispatcher.newJobBuilder()
//                .setService(MyJobService.class)
//                .setTag("my-job-tag")
//                .build();
//        dispatcher.schedule(myJob);

    }

    private void sendRegistrationToServer(String token) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tokens");

        reference.setValue(token);
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
        Log.i(TAG, "messageSend: " + s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
        Log.i(TAG, "onSendError: " + s);
        Log.i(TAG, "Exception: " + e);
    }

    private void sendNotification(RemoteMessage remoteMessage) {

        String message = remoteMessage.getData().get("message");
        String foto = remoteMessage.getData().get("foto");
        String TrueOrFlase = remoteMessage.getData().get("AnotherActivity");
        String imagem = remoteMessage.getData().get("imagem");

        Bitmap fotoPerfil = null;

        Bitmap imagemNotificacao = null;

        if(!imagem.isEmpty()){
            imagemNotificacao = getBitmapfromUrl(imagem);
        }

        if(!foto.isEmpty()){
            fotoPerfil = getBitmapfromUrl(foto);
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       // intent.putExtra("AnotherActivity", TrueOrFlase);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId);

        notificationBuilder
                        .setSmallIcon(R.drawable.logo_quimica_no_title);

        if(fotoPerfil != null) {
            notificationBuilder.setLargeIcon(fotoPerfil);
        }

        if(imagemNotificacao != null){
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(imagemNotificacao).bigLargeIcon(null));

        }else{
            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(remoteMessage.getNotification().getBody()));
        }

        notificationBuilder
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setVibrate(new long[] { 100, 250, 100, 500 })
                .setSound(defaultSoundUri)
                .setColor(Color.parseColor("#8c28ff"))
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        Log.d("NEW_TOKEN",s);

        sendRegistrationToServer(s);
    }
}
