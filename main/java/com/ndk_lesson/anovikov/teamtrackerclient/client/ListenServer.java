package com.ndk_lesson.anovikov.teamtrackerclient.client;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ndk_lesson.anovikov.teamtrackerclient.MainActivity;
import com.ndk_lesson.anovikov.teamtrackerclient.ServerCommunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


public class ListenServer implements Runnable {
	private final int mPort;
	private Gson mGson;
	private DataOutputStream mOs;
	private DataInputStream mIs;
    private boolean mStop;
    private String mServerAddr;
    private Messenger mActivityMessenger;

	public ListenServer(String serverAddr, int port, IBinder binder) {
		mServerAddr = serverAddr;
		mPort = port;
		mGson = new Gson();
        mActivityMessenger = new Messenger(binder);

		mOs = null;
		mIs = null;
	}
	
	public void run() {
		Socket socket = null;
		try {
			socket = new Socket(mServerAddr, mPort);
			mOs = new DataOutputStream(socket.getOutputStream());
			mIs = new DataInputStream(socket.getInputStream());
			
			Coordinate coord = null;
			
			while(!socket.isClosed()) {
                if(mStop)
                    break;
				String data = mIs.readUTF();
				coord = mGson.fromJson(data, Coordinate.class);
                Message msg = Message.obtain(null, MainActivity.MSG_COORD, 0, 0);
                Bundle bundle = new Bundle();
                bundle.putDouble(ServerCommunication.X, coord.mX);
                bundle.putDouble(ServerCommunication.Y, coord.mY);
                msg.setData(bundle);
                mActivityMessenger.send(msg);
				
				/**
				 * Response to server
				 */
				mOs.writeUTF("Get = " + data);
				mOs.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
            Log.e("Messenger", "Messenger send error!");
            e.printStackTrace();
        } finally {
            try {
                if(socket == null)
                    Log.e("Socket", "Failed to connect port: " + mPort);
                else
                    socket.close();
            } catch (IOException e) {
                Log.e("Socket", "Closing socket error!");
                System.exit(1);
            }
        }
    }

    public void stopListen() {
        mStop = true;
    }
}
