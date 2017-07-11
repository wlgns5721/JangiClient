package com.example.jihoon.clientsocket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.DecimalFormat;


public class MainActivity extends Activity {

    Context context = this;
    EditText EdtEnterIp;
    ProgressDialog dialog;
    Handler handler;
    Intent service;
    static boolean isDisconnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Page1();

    }
    void Page1() {
        setContentView(R.layout.activity_main);
        EdtEnterIp = (EditText)findViewById(R.id.enterIp);
        ChatActivity.socketInfo = new SocketInfo(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);




        ChatActivity.socketInfo.BtnGameStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nickname = EdtEnterIp.getText().toString();
                if(!nickname.equals(""))
                    SocketInfo.my_nickname = nickname;
                new MyAsyncTask().execute();

            }
        });


    }
    void Init() throws IOException {
        //닉네임과 id정보를 서버에게 전달한 후
        //만약 신규 클라이언트라면 id를 할당받는다.

        DecimalFormat df = new DecimalFormat("000");
        DecimalFormat df2 = new  DecimalFormat("00");

        String nickname = ChatActivity.socketInfo.my_nickname;       //내 아이디
        String nickname_len = df2.format(nickname.length());

        String strlen = df.format(nickname.length() + 3);
        String info = strlen+nickname_len+nickname+"0";

        BufferedWriter out = new BufferedWriter( new OutputStreamWriter(ChatActivity.socketInfo.secondClientSocket.getOutputStream()));
        out.write(info);
        out.flush();

        InputStream in = ChatActivity.socketInfo.secondClientSocket.getInputStream();
        byte bytelen[] = new byte[5];
        in.read(bytelen);
        int len = Integer.parseInt(new String(bytelen));
        byte textBuffer[] = new byte[len];
        in.read(textBuffer);
        String received_text = new String(textBuffer);
        ChatActivity.socketInfo.id = Integer.parseInt(received_text);

    }

    void WaitMatching() {
        try {
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

            String s = received_text.substring(0, 1);
            String opponent_nickname = received_text.substring(1);
            ChatActivity.socketInfo.opponent_nickname = opponent_nickname;
            Gview.your_team=Integer.parseInt(s);
            ChatActivity.StartFlag=true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        ReceiveClientThread thread = new ReceiveClientThread(ChatActivity.socketInfo);
        thread.start();
    }



    public class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            //dialog.show(MainActivity.this, "", "loading....", true);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("로딩중입니다..");
            dialog.show();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            new ConnectSocketThread().start();
            while (!ChatActivity.StartFlag) {
                if (isDisconnected == true) {
                    ChatActivity.StartFlag = false;
                    Toast.makeText(context, "메인화면으로 돌아갑니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                    startActivity(intent);
                }
            }
            ChatActivity.StartFlag = false;
            dialog.dismiss();
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);


            return 0;
        }

        @Override
        protected void onProgressUpdate(Integer... params) {

        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
        }
}








    class ConnectSocketThread extends Thread {
        public void run() {
            ChatActivity.socketInfo.ConnectSocket();
            try {
                Init();
            } catch (IOException e) {
                e.printStackTrace();
            }
            WaitMatching();
            //service = new Intent(MainActivity.this, MyService.class);
            //startService(service);

        }
    }
}

