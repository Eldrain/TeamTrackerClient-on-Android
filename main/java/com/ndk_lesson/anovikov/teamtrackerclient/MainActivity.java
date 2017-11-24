package com.ndk_lesson.anovikov.teamtrackerclient;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String address = "10.0.2.2";
    public static final int PORT = 2222;
    public static final int MSG_COORD = 0;
    public static final String MESSENGER = "messenger";
    public static final String PORT_ID = "port";
    public static final String ADDRESS_ID = "address";
    private TextView mCoordText;
    private ServerCommunication mCom;
    private Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoordText = (TextView) findViewById(R.id.get_text);

        messenger = new Messenger(new UpdateCoord());
       // mCom = new ServerCommunication(, PORT, messenger.getBinder());

        Intent serviceIntent = new Intent(this, ServerCommunication.class);
        serviceIntent.putExtra(MESSENGER, messenger);
        serviceIntent.putExtra(PORT_ID, PORT);
        serviceIntent.putExtra(ADDRESS_ID, address);
        startService(serviceIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isFinishing()) {
            stopService(new Intent(this, ServerCommunication.class));
        }
    }

    class UpdateCoord extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case MSG_COORD:
                    Bundle data = msg.getData();
                    mCoordText.setText("x = " + data.getDouble(ServerCommunication.X) + "; y = " + data.getDouble(ServerCommunication.Y));
                    break;
                default:
                super.handleMessage(msg);
            }
        }
    }
}
