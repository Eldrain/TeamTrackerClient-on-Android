package com.ndk_lesson.anovikov.teamtrackerclient;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements ConnectFragment.OnConnectFragment, MainMenuFragment.OnMainMenuListener {
    enum Screen { CONNECT, MAIN_MENU}

    /**
     * TODO: delete PORT and adress
     */
    public static final String address = "10.0.2.2";
    public static final int PORT = 2222;
    public static final int MSG_COORD = 0;
    public static final String MESSENGER = "messenger";
    public static final String PORT_ID = "port";
    public static final String ADDRESS_ID = "address";

    private TextView mCoordText;
    private ServerCommunication mCom;
    private Messenger messenger;
    private Screen mState;

    private String mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            try {
                setScreen(Screen.CONNECT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mCoordText = null;
        mLogin = null;

        messenger = new Messenger(new UpdateCoord());
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

    private void setScreen(Screen newScreen) throws Exception {
        mState = newScreen;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = null;

        switch(mState) {
            case CONNECT:
                fragment = new ConnectFragment();
                break;
            case MAIN_MENU:
                fragment = new MainMenuFragment();
                break;
            default:
                throw new Exception("Error on set new screen. New screen is not define.");
        }

        transaction.replace(R.id.start_layout, fragment);
        transaction.commit();
    }

    @Override
    public void connect(String login) {
        Intent serviceIntent = new Intent(this, ServerCommunication.class);

        serviceIntent.putExtra(MESSENGER, messenger);
        serviceIntent.putExtra(PORT_ID, PORT);
        serviceIntent.putExtra(ADDRESS_ID, address);
        startService(serviceIntent);

        try {
            setScreen(Screen.MAIN_MENU);
        } catch (Exception e) {
            System.out.println("connect() in MainActivity!");
            e.printStackTrace();
        }

        mLogin = login;
    }

    @Override
    public void bindCoordTextView() {
        mCoordText = (TextView) findViewById(R.id.text_coords);
    }

    @Override
    public void unbindCoordTextView() {
        mCoordText = null;
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
