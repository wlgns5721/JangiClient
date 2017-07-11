package com.example.jihoon.clientsocket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * Created by Ji Hoon on 2016-02-22.
 */
public class ChatActivity extends Activity {
    static SocketInfo socketInfo;
    static boolean StartFlag=false;
    static Handler endHandler;
    public void onCreate(Bundle savedInsatanceState) {
        super.onCreate(savedInsatanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //Intent service = new Intent(ChatActivity.this,MyService.class);
        //startService(service);



        setContentView(R.layout.game);

        Gview gview = (Gview)findViewById(R.id.surface);
        gview.setZOrderOnTop(false);
        socketInfo.isReadyClient = true;
        socketInfo.BtnClientSend = (Button)findViewById(R.id.game_button);
        socketInfo.EdtClientSendText = (EditText)findViewById(R.id.game_text);
        socketInfo.txtReceiveText = (TextView)findViewById(R.id.game_chat);
        socketInfo.scroll = (ScrollView)findViewById(R.id.scroll);
        socketInfo.BtnClientSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                socketInfo.strClientSendText = socketInfo.EdtClientSendText.getText().toString();
                byte[] temp = (socketInfo.strClientSendText).getBytes();
                int len = temp.length;
                DecimalFormat df = new DecimalFormat("000");
                String strlen = df.format(len);
                socketInfo.SendFromClient(strlen + socketInfo.strClientSendText);
            }
        });



        socketInfo.mHandler = new Handler() {

            public void handleMessage(Message msg) {
                socketInfo.ReceiveFromClient();

            }
        };

        endHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(Gview.EndFlag==true) {
                    Gview.EndFlag = false;
                    try {
                        socketInfo.secondClientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(ChatActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
    };



}

class ReceiveClientThread extends Thread {
    public SocketInfo socketInfo;
    ReceiveClientThread(SocketInfo info) {socketInfo = info;}
    public void run() {
        while (true) {
            try {
                if (socketInfo.isReadyClient == true) {
                    InputStream in = socketInfo.secondClientSocket.getInputStream();
                    byte arrlen[] = new byte[3];
                    if(in.read(arrlen)==-1) {
                        return;
                    }
                    String strlen = new String(arrlen);
                    int len = Integer.parseInt(strlen);
                    byte arrcont[] = new byte[len];
                    if(in.read(arrcont)==-1) {
                        return;
                    }
                    String strArrCont = new String(arrcont);
                    socketInfo.strReceiveText = strArrCont;
                    if (strArrCont.contains("$@d^!#d!Movement"))
                        Gview.pieceMovement = strArrCont ;
                    else if(strArrCont.contains("*%@^@"))
                        Gview.isRegameMatched = strArrCont.substring(5);

                    else
                        socketInfo.mHandler.sendMessage(Message.obtain(socketInfo.mHandler));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }  finally{

            }
        }


    };
}