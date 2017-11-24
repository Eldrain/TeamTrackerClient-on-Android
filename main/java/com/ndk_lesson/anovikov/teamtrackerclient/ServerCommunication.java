package com.ndk_lesson.anovikov.teamtrackerclient;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.IntDef;
import android.util.Log;

import com.ndk_lesson.anovikov.teamtrackerclient.client.ListenServer;

public class ServerCommunication extends Service {
    public final static String X = "x";
    public final static String Y = "y";
    private Thread mThread;
    private ListenServer client;

    public ServerCommunication() {
        Log.d("SC", "SC empty constructor called.");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int port = -1;
        String address = "";
        Messenger mess = null;

        mess = (Messenger)intent.getExtras().get(MainActivity.MESSENGER);
        port = intent.getExtras().getInt(MainActivity.PORT_ID);
        address = intent.getExtras().getString(MainActivity.ADDRESS_ID);

        client = new ListenServer(address, port, mess.getBinder());
        mThread = new Thread(client);
        mThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("SERVICE", "onDestroy()");
        client.stopListen();
        try {
            mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
