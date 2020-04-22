package com.example.demonotificaciones;
/*
import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.edgesystems.escalas.InitActivity;
import com.edgesystems.escalas.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import com.edgesystems.service.Service;
import com.parse.SaveCallback;
import com.parse.fcm.ParseFCM;
*/
/**
 * Created by omargongoraselem on 03/05/17.
 */
/*
public class MiFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String dataString = remoteMessage.getData().get("data");
        JSONObject data = null;
        if (dataString != null) {
            try {
                data = new JSONObject(dataString);
                Service.app.ReceibeNewNotification(data);
                switch (data.getString("notice_Type")) {
                    case "Circular":
                        String titleCircular = data.getJSONObject("alert").getString("title");
                        String bodyCircular = data.getJSONObject("alert").getString("body");
                        String urlImageStringCircular = data.getString("urlImageString");
                        Service.app.incrementBadge();
                        showNotification(titleCircular,bodyCircular,"Circular",urlImageStringCircular);
                        break;
                    case "Aviso":
                        String titleAviso = data.getJSONObject("alert").getString("title");
                        String bodyAviso = data.getJSONObject("alert").getString("body");
                        showNotification(titleAviso,bodyAviso,"Aviso",null);
                        Service.app.incrementBadge();
                        break;
                    case "Permiso":
                        String titlePermiso = data.getJSONObject("alert").getString("title");
                        String bodyPermiso = data.getJSONObject("alert").getString("body");
                        showNotification(titlePermiso,bodyPermiso,"Permiso",null);
                        break;
                    default:
                        break;
                    case "Anuncio":
                        String titleAnuncio = data.getJSONObject("alert").getString("title");
                        String bodyAnuncio = data.getJSONObject("alert").getString("body");
                        showNotification(titleAnuncio,bodyAnuncio,"Anuncio",null);
                        break;
                }
            } catch (JSONException e) {
                return;
            }
        }

        if (remoteMessage.getData()!= null){
            JSONObject object = new JSONObject(remoteMessage.getData());
            try {
                String d = object.getString("data");
                final JSONObject dataObject = new JSONObject(d);
                if (dataObject.has("badge")){
                    int badgeValue = dataObject.getInt("badge");
                    //ShortcutBadger.applyCount(getApplicationContext(),badgeValue);

                }
                if(dataObject.has("notificationType")){

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        ParseFCM.register(s);
        /*
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        if (installation != null && s != null) {
            installation.setDeviceToken(s);
            //even though this is FCM, calling it gcm will work on the backend
            installation.setPushType("Firebase");
            installation.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {

                    }
                }
            });
        }*/
//    }
/*
    private void showNotification(String title, String message, String typeString, String urlString){
        switch (typeString) {
            case "Circular":
                new generatePictureStyleNotification(this,title, message,
                        urlString,typeString).execute();
                break;
            case "Aviso":
                if (Service.app.getEnable_SoundNotificationsInApp()&&Service.app.getEnable_NotificationsInApp()){
                    Intent intentAviso = new Intent(this, InitActivity.class);
                    PendingIntent pendingAviso = PendingIntent.getActivity(this,0,intentAviso,PendingIntent.FLAG_ONE_SHOT);
                    Uri Uri_soundAviso = Uri.parse("android.resource://" + getPackageName() + "/"
                            + R.raw.notification);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        CharSequence name = "Avisos";
                        NotificationChannel channel = new NotificationChannel(typeString, name, NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationCompat.Builder notificationBuilderAviso =  new NotificationCompat.Builder(this, channel.getId())
                                .setSmallIcon(R.drawable.iconpush)
                                .setGroup(typeString)
                                .setGroupSummary(true)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setAutoCancel(true)
                                .setSound(Uri_soundAviso)
                                .setContentIntent(pendingAviso)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(message));
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                        notificationManager.createNotificationChannel(channel);
                        notificationManager.notify(getRandomNumberInRange(0,100), notificationBuilderAviso.build());
                    }else{
                        NotificationCompat.Builder notificationBuilderAviso =  new NotificationCompat.Builder(this,typeString)
                                .setSmallIcon(R.drawable.iconpush)
                                .setGroup(typeString)
                                .setGroupSummary(true)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setAutoCancel(true)
                                .setSound(Uri_soundAviso)
                                .setContentIntent(pendingAviso)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(message));
                        NotificationManager notificationManagerAviso = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManagerAviso.notify(getRandomNumberInRange(0,100),notificationBuilderAviso.build());
                    }
                }else if (Service.app.getEnable_SoundNotificationsInApp()){
                    Intent intentAviso = new Intent(this, InitActivity.class);
                    PendingIntent pendingAviso = PendingIntent.getActivity(this,0,intentAviso,PendingIntent.FLAG_ONE_SHOT);
                    Uri Uri_soundAviso = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        CharSequence name = "Avisos";
                        NotificationChannel channel = new NotificationChannel(typeString, name, NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationCompat.Builder notificationBuilderAviso =  new NotificationCompat.Builder(this, channel.getId())
                                .setSmallIcon(R.drawable.iconpush)
                                .setGroup(typeString)
                                .setGroupSummary(true)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setAutoCancel(true)
                                .setSound(Uri_soundAviso)
                                .setContentIntent(pendingAviso)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(message));
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                        notificationManager.createNotificationChannel(channel);
                        notificationManager.notify(getRandomNumberInRange(0,100), notificationBuilderAviso.build());

                    }else{
                        NotificationCompat.Builder notificationBuilderAviso =  new NotificationCompat.Builder(this,typeString)
                                .setSmallIcon(R.drawable.iconpush)
                                .setGroup(typeString)
                                .setGroupSummary(true)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setAutoCancel(true)
                                .setSound(Uri_soundAviso)
                                .setContentIntent(pendingAviso)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(message));

                        NotificationManager notificationManagerAviso = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManagerAviso.notify(getRandomNumberInRange(0,100),notificationBuilderAviso.build());
                    }
                }else if(Service.app.getEnable_NotificationsInApp()){
                    Intent intentAviso = new Intent(this, InitActivity.class);
                    PendingIntent pendingAviso = PendingIntent.getActivity(this,0,intentAviso,PendingIntent.FLAG_ONE_SHOT);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        CharSequence name = "Avisos";
                        NotificationChannel channel = new NotificationChannel(typeString, name, NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationCompat.Builder notificationBuilderAviso =  new NotificationCompat.Builder(this, channel.getId())
                                .setSmallIcon(R.drawable.iconpush)
                                .setGroup(typeString)
                                .setGroupSummary(true)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setAutoCancel(true)
                                .setContentIntent(pendingAviso)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(message));
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                        notificationManager.createNotificationChannel(channel);
                        notificationManager.notify(getRandomNumberInRange(0,100), notificationBuilderAviso.build());
                    }else{
                        NotificationCompat.Builder notificationBuilderAviso =  new NotificationCompat.Builder(this,typeString)
                                .setSmallIcon(R.drawable.iconpush)
                                .setGroup(typeString)
                                .setGroupSummary(true)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setAutoCancel(true)
                                .setContentIntent(pendingAviso)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(message));
                        NotificationManager notificationManagerAviso = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManagerAviso.notify(getRandomNumberInRange(0,100),notificationBuilderAviso.build());
                    }
                }

                break;
            case "Permiso":
                if (Service.app.getEnable_SoundNotificationsInApp()&&Service.app.getEnable_NotificationsInApp()){
                    Intent intentPermiso = new Intent(this, InitActivity.class);
                    PendingIntent pendingPermiso = PendingIntent.getActivity(this,0,intentPermiso,PendingIntent.FLAG_ONE_SHOT);
                    Uri Uri_soundPermiso = Uri.parse("android.resource://" + getPackageName() + "/"
                            + R.raw.notification);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        CharSequence name = "Permisos";
                        NotificationChannel channel = new NotificationChannel(typeString, name, NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationCompat.Builder notificationBuilderPermiso =  new NotificationCompat.Builder(this, channel.getId())
                                .setSmallIcon(R.drawable.iconpush)
                                .setGroup(typeString)
                                .setGroupSummary(true)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setAutoCancel(true)
                                .setSound(Uri_soundPermiso)
                                .setContentIntent(pendingPermiso)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(message));
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                        notificationManager.createNotificationChannel(channel);
                        notificationManager.notify(getRandomNumberInRange(0,100), notificationBuilderPermiso.build());
                    }else{
                        NotificationCompat.Builder notificationBuilderPermiso =  new NotificationCompat.Builder(this,typeString)
                                .setSmallIcon(R.drawable.iconpush)
                                .setGroup(typeString)
                                .setGroupSummary(true)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setAutoCancel(true)
                                .setSound(Uri_soundPermiso)
                                .setContentIntent(pendingPermiso)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(message));
                        NotificationManager notificationManagerPermiso = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        notificationManagerPermiso.notify(getRandomNumberInRange(0,100),notificationBuilderPermiso.build());

                    }
                }else if (Service.app.getEnable_SoundNotificationsInApp()){
                    Intent intentPermiso = new Intent(this, InitActivity.class);
                    PendingIntent pendingPermiso = PendingIntent.getActivity(this,0,intentPermiso,PendingIntent.FLAG_ONE_SHOT);
                    Uri Uri_soundPermiso = Uri.parse("android.resource://" + getPackageName() + "/"
                            + R.raw.notification);

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        CharSequence name = "Permisos";
                        NotificationChannel channel = new NotificationChannel(typeString, name, NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationCompat.Builder notificationBuilderPermiso =  new NotificationCompat.Builder(this, channel.getId())
                                .setSmallIcon(R.drawable.iconpush)
                                .setGroup(typeString)
                                .setGroupSummary(true)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setAutoCancel(true)
                                .setSound(Uri_soundPermiso)
                                .setContentIntent(pendingPermiso)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(message));
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                        notificationManager.createNotificationChannel(channel);
                        notificationManager.notify(getRandomNumberInRange(0,100), notificationBuilderPermiso.build());
                    }else{
                        NotificationCompat.Builder notificationBuilderPermiso =  new NotificationCompat.Builder(this,typeString)
                                .setSmallIcon(R.drawable.iconpush)
                                .setGroup(typeString)
                                .setGroupSummary(true)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setAutoCancel(true)
                                .setSound(Uri_soundPermiso)
                                .setContentIntent(pendingPermiso)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(message));
                        NotificationManager notificationManagerPermiso = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManagerPermiso.notify(getRandomNumberInRange(0,100),notificationBuilderPermiso.build());
                    }
                }else if(Service.app.getEnable_NotificationsInApp()){
                    Intent intentPermiso = new Intent(this, InitActivity.class);
                    PendingIntent pendingPermiso = PendingIntent.getActivity(this,0,intentPermiso,PendingIntent.FLAG_ONE_SHOT);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        CharSequence name = "Permisos";
                        NotificationChannel channel = new NotificationChannel(typeString, name, NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationCompat.Builder notificationBuilderPermiso =  new NotificationCompat.Builder(this, channel.getId())
                                .setSmallIcon(R.drawable.iconpush)
                                .setGroup(typeString)
                                .setGroupSummary(true)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setAutoCancel(true)
                                .setContentIntent(pendingPermiso)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(message));
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                        notificationManager.createNotificationChannel(channel);
                        notificationManager.notify(getRandomNumberInRange(0,100), notificationBuilderPermiso.build());
                    }else{
                        NotificationCompat.Builder notificationBuilderPermiso =  new NotificationCompat.Builder(this,typeString)
                                .setSmallIcon(R.drawable.iconpush)
                                .setGroup(typeString)
                                .setGroupSummary(true)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setAutoCancel(true)
                                .setContentIntent(pendingPermiso)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setStyle(new NotificationCompat.BigTextStyle()
                                        .bigText(message));
                        NotificationManager notificationManagerPermiso = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManagerPermiso.notify(getRandomNumberInRange(0,100),notificationBuilderPermiso.build());
                    }
                }
                break;
            case "Anuncio":
                Intent intentAnuncio = new Intent(this, InitActivity.class);
                PendingIntent pendingAnuncio = PendingIntent.getActivity(this,0,intentAnuncio,PendingIntent.FLAG_ONE_SHOT);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    CharSequence name = "Anuncios";
                    NotificationChannel channel = new NotificationChannel(typeString, name, NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationCompat.Builder notificationBuilderAnuncio =  new NotificationCompat.Builder(this, channel.getId())
                            .setSmallIcon(R.drawable.iconpush)
                            .setGroup(typeString)
                            .setGroupSummary(true)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setAutoCancel(true)
                            .setContentIntent(pendingAnuncio)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(message));
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    notificationManager.createNotificationChannel(channel);
                    notificationManager.notify(getRandomNumberInRange(0,100), notificationBuilderAnuncio.build());
                }else{
                    NotificationCompat.Builder notificationBuilderAnuncio =  new NotificationCompat.Builder(this,typeString)
                            .setSmallIcon(R.drawable.iconpush)
                            .setGroup(typeString)
                            .setGroupSummary(true)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setAutoCancel(true)
                            .setContentIntent(pendingAnuncio)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(message));
                    NotificationManager notificationManagerAnuncio = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManagerAnuncio.notify(getRandomNumberInRange(0,100),notificationBuilderAnuncio.build());
                }
                break;
            default:
                break;
        }
    }
    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    public class generatePictureStyleNotification extends AsyncTask<String, Void, Bitmap> {

        private Context mContext;
        private String title, message, imageUrl, type;

        public generatePictureStyleNotification(Context context, String title, String message, String imageUrl,String Type) {
            super();
            this.mContext = context;
            this.title = title;
            this.message = message;
            this.imageUrl = imageUrl;
            this.type = Type;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in;
            try {
                URL url = new URL(this.imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if (Service.app.getEnable_SoundNotificationsInApp()&&Service.app.getEnable_NotificationsInApp()){
                Intent intentCircular = new Intent(mContext, InitActivity.class);
                PendingIntent pendingCircular = PendingIntent.getActivity(mContext,0,intentCircular,PendingIntent.FLAG_ONE_SHOT);
                Uri Uri_soundCircular = Uri.parse("android.resource://" + getPackageName() + "/"
                        + R.raw.notification);
                Bitmap bmAviso = BitmapFactory.decodeResource(getResources(), R.drawable.check_icon);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    CharSequence name = "Circulares";
                    NotificationChannel channel = new NotificationChannel("Circular", name, NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationCompat.Builder notificationBuilderCircular =  new NotificationCompat.Builder(mContext, channel.getId())
                            .setSmallIcon(R.drawable.iconpush)
                            .setLargeIcon(result)
                            .setGroup(type)
                            .setGroupSummary(true)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setAutoCancel(true)
                            .setSound(Uri_soundCircular)
                            .setContentIntent(pendingCircular)
                            .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(result))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(message));
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
                    notificationManager.createNotificationChannel(channel);
                    notificationManager.notify(getRandomNumberInRange(0,100), notificationBuilderCircular.build());
                }else{
                    NotificationCompat.Builder notificationBuilderAviso =  new NotificationCompat.Builder(mContext,type)
                            .setSmallIcon(R.drawable.iconpush)
                            .setLargeIcon(result)
                            .setGroup(type)
                            .setGroupSummary(true)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setAutoCancel(true)
                            .setSound(Uri_soundCircular)
                            .setContentIntent(pendingCircular)
                            .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(result))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(message));
                    NotificationManager notificationManagerCircular = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManagerCircular.notify(getRandomNumberInRange(0,100),notificationBuilderAviso.build());
                }
            }else if (Service.app.getEnable_SoundNotificationsInApp()){
                Intent intentCircular = new Intent(mContext, InitActivity.class);
                PendingIntent pendingCircular = PendingIntent.getActivity(mContext,0,intentCircular,PendingIntent.FLAG_ONE_SHOT);
                Uri Uri_soundCircular = Uri.parse("android.resource://" + getPackageName() + "/"
                        + R.raw.notification);
                Bitmap bmAviso = BitmapFactory.decodeResource(getResources(), R.drawable.check_icon);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    CharSequence name = "Circulares";
                    NotificationChannel channel = new NotificationChannel("Circular", name, NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationCompat.Builder notificationBuilderCircular =  new NotificationCompat.Builder(mContext, channel.getId())
                            .setSmallIcon(R.drawable.iconpush)
                            .setLargeIcon(result)
                            .setGroup(type)
                            .setGroupSummary(true)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setAutoCancel(true)
                            .setSound(Uri_soundCircular)
                            .setContentIntent(pendingCircular)
                            .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(result))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
                    notificationManager.createNotificationChannel(channel);
                    notificationManager.notify(getRandomNumberInRange(0,100), notificationBuilderCircular.build());
                }else{
                    NotificationCompat.Builder notificationBuilderAviso =  new NotificationCompat.Builder(mContext,type)
                            .setSmallIcon(R.drawable.iconpush)
                            .setLargeIcon(result)
                            .setGroup(type)
                            .setGroupSummary(true)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setAutoCancel(true)
                            .setSound(Uri_soundCircular)
                            .setContentIntent(pendingCircular)
                            .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(result))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManager notificationManagerCircular = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManagerCircular.notify(getRandomNumberInRange(0,100),notificationBuilderAviso.build());
                }
            }else if(Service.app.getEnable_NotificationsInApp()){
                Intent intentCircular = new Intent(mContext, InitActivity.class);
                PendingIntent pendingCircular = PendingIntent.getActivity(mContext,0,intentCircular,PendingIntent.FLAG_ONE_SHOT);
                Bitmap bmAviso = BitmapFactory.decodeResource(getResources(), R.drawable.check_icon);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    CharSequence name = "Circulares";
                    NotificationChannel channel = new NotificationChannel("Circular", name, NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationCompat.Builder notificationBuilderCircular =  new NotificationCompat.Builder(mContext, channel.getId())
                            .setSmallIcon(R.drawable.iconpush)
                            .setLargeIcon(result)
                            .setGroup(type)
                            .setGroupSummary(true)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setAutoCancel(true)
                            .setContentIntent(pendingCircular)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(result));
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
                    notificationManager.createNotificationChannel(channel);
                    notificationManager.notify(getRandomNumberInRange(0,100), notificationBuilderCircular.build());
                }else{
                    NotificationCompat.Builder notificationBuilderAviso =  new NotificationCompat.Builder(mContext,type)
                            .setSmallIcon(R.drawable.iconpush)
                            .setLargeIcon(result)
                            .setGroup(type)
                            .setGroupSummary(true)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setAutoCancel(true)
                            .setContentIntent(pendingCircular)
                            .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(result))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManager notificationManagerCircular = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManagerCircular.notify(getRandomNumberInRange(0,100),notificationBuilderAviso.build());
                }
            }
        }
    }

}
*/