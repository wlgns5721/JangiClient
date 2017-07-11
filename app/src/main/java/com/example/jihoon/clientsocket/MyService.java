package com.example.jihoon.clientsocket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MyService extends Service {
    String text;
    int index;
    String name;
    public MyService() {
    }

    @Override
    public void onStart(Intent intent, int startId) {
/*        try {
            if(!ChatActivity.socketInfo.opponent_nickname.equals("")) {
                ReceiveClientThread thread = new ReceiveClientThread(ChatActivity.socketInfo);
                thread.start();
                return;
            }
            InputStream in = ChatActivity.socketInfo.secondClientSocket.getInputStream();
            byte arrlen[] = new byte[3];
            if(in.read(arrlen)==-1) {    //매칭이 될 때까지 기다린다.
                MainActivity.isDisconnected = true;
                return;

            }
            int len = Integer.parseInt(new String(arrlen));
            byte textBuffer[] = new byte[len];
            if(in.read(textBuffer)==-1) {
                MainActivity.isDisconnected = true;
                return;
            }
            String received_text = new String(textBuffer);

            ChatActivity.StartFlag=true;
            String s = received_text.substring(0, 1);
            String opponent_nickname = received_text.substring(1);
            ChatActivity.socketInfo.opponent_nickname = opponent_nickname;
            Gview.your_team=Integer.parseInt(s);


        } catch (IOException e) {
            e.printStackTrace();
        }

        ReceiveClientThread thread = new ReceiveClientThread(ChatActivity.socketInfo);
        thread.start();
*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
