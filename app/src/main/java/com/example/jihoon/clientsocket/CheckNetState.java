package com.example.jihoon.clientsocket;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.io.IOException;
import java.net.SocketException;
import java.text.DecimalFormat;

public class CheckNetState extends BroadcastReceiver {
    String action;
    Context parameter_context;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        action = intent.getAction();
        parameter_context = context;
        if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION))
        {
            ConnectivityManager manager =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (mobile.isConnected() || wifi.isConnected()){
                Toast.makeText(context, "네트워크가 연결되었습니다", Toast.LENGTH_LONG).show();
                //여기에 재연결 부분 구현
                Reconnect();
            }

            else{
                Toast.makeText(context, "네트워크가 끊어졌습니다", Toast.LENGTH_LONG).show();
                try {

                    ChatActivity.socketInfo.secondClientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void Reconnect() {
        ChatActivity.socketInfo.ConnectSocket();
        Intent service = new Intent(parameter_context, MyService.class);
        parameter_context.startService(service);

    }
}
