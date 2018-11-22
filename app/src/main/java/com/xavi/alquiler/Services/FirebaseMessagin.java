package com.xavi.alquiler.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.xavi.alquiler.Detalle_Explorer_Activity;
import com.xavi.alquiler.R;
import com.xavi.alquiler.Utiles.Contexto;


public class FirebaseMessagin extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getData().size()==0){
            return;
        }
        switch (remoteMessage.getData().get("evento")){
            case "mensaje":
                mensaje(remoteMessage);
                break;

            case "casa_insertada":
                casa_insertada(remoteMessage);
                break;
        }
        return;
    }

    private void casa_insertada(RemoteMessage remoteMessage) {
        Intent notificationIntent = new Intent(this, Detalle_Explorer_Activity.class);
        notificationIntent.putExtra("obj",remoteMessage.getData().get("json"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        Notification notification= new NotificationCompat.Builder(this, Contexto.CHANNEL_ID)
                .setContentTitle("Servicasas")
                .setContentText("Se a agregado una casa cerca.")
                .setSmallIcon(R.drawable.ic_ofertar_casa_icon_foreground)
                .setContentIntent(pendingIntent)
                .build();
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(2,notification);
        
//        Intent intent = new Intent();
//        intent.putExtra("obj_carrera",remoteMessage.getData().get("json"));
//        intent.setAction("cancelo_carrera");
//        sendBroadcast(intent);
    }


    private void mensaje(RemoteMessage remoteMessage){
        Intent intent = new Intent();
        intent.putExtra("message",remoteMessage.getData().get("mensaje"));
        intent.setAction("Message");
        sendBroadcast(intent);
    }

}
