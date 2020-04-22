package com.example.demonotificaciones;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class Fcm extends FirebaseMessagingService {
    //acceso al nuevo token de autentificacion
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e("token: ",s);
        guardarToken(s);
    }

    //guardamos el token para tener un orden en ves de mytoken colocaremos el id del usuario con el cual se a loggeado
    private void guardarToken(String token){
        DatabaseReference  reference = FirebaseDatabase.getInstance().getReference().child("token");
        reference.child("myToken").setValue(token);
    }

    //
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String from =remoteMessage.getFrom();
        Log.e("FROM","Message receiver :"+from);
        if (remoteMessage.getNotification() != null){
            String titulo = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
           notificacion(titulo,body);
        }

        if (remoteMessage.getData().size() >0){
            String titulo = remoteMessage.getData().get("titulo");
            String detalle = remoteMessage.getData().get("desc");
            String color = remoteMessage.getData().get("color");
            String click_action = remoteMessage.getNotification().getClickAction();
            mayorOreo(titulo,detalle);
        }
    }


    private void notificacion(String titulo, String detalle){
        String id ="mensaje";
        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.descarga);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(this,id);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Get the layouts to use in the custom notification

            NotificationChannel notificationChannel = new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setShowBadge(true);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
            builder.setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(titulo)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(detalle)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(myBitmap))
                 //   .setCustomBigContentView(notificationLayoutExpanded)
                    .setContentIntent(tercero())
                    .setContentInfo("nuevo");

            Random random = new Random();
            int idNofify = random.nextInt(8000);
            assert notificationManager != null;
            notificationManager.notify(idNofify,builder.build());
        }else {
            NotificationCompat.Builder notificationBuilderAviso =  new NotificationCompat.Builder(this,id)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(titulo)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(detalle)
                    .setContentIntent(clickNoty())
                    .setContentInfo("nuevo");
            NotificationManager notificationManagerCircular = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Random random = new Random();
            int idNofify = random.nextInt(8000);
            assert notificationManagerCircular != null;
            notificationManagerCircular.notify(idNofify,notificationBuilderAviso.build());

        }
    }
    private void mayorOreo(String titulo, String detalle){
        String id ="mensaje";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(this,id);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setShowBadge(true);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
            builder.setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(titulo)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(detalle)
                    .setContentIntent(clickNoty())
                    .setContentInfo("nuevo")
                    .addAction(R.drawable.ic_notificarion, "Actividad 2",
                    c())
                    .addAction(R.drawable.ic_notificarion, "Actividad 3",
                            c2());

            Random random = new Random();
            int idNofify = random.nextInt(8000);
            assert notificationManager != null;
            notificationManager.notify(idNofify,builder.build());
        }else {
            NotificationCompat.Builder notificationBuilderAviso =  new NotificationCompat.Builder(this,id)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle(titulo)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(detalle)
                    .setContentIntent(clickNoty())
                    .setContentInfo("nuevo");
            NotificationManager notificationManagerCircular = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Random random = new Random();
            int idNofify = random.nextInt(8000);
            assert notificationManagerCircular != null;
            notificationManagerCircular.notify(idNofify,notificationBuilderAviso.build());

        }
    }



    private PendingIntent tercero(){
        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(this, TerceraActivity.class);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        return  resultPendingIntent;
    }


    private PendingIntent clickNoty() {
        Intent nf = new Intent(getApplicationContext(),MainActivity.class);
        nf.putExtra("color","rojo");
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this,0,nf,0);
    }
    private PendingIntent c() {
        Intent nf = new Intent(getApplicationContext(),SecondActivity.class);
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this,0,nf,0);
    }
    private PendingIntent c2() {
        Intent nf = new Intent(getApplicationContext(),TerceraActivity.class);
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(this,0,nf,0);
    }
}
