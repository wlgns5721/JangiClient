package com.example.jihoon.clientsocket;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.Enumeration;

/**
 * Created by Ji Hoon on 2016-02-20.
 */
public class Network{
    Context context;
    public Network(Context _context) throws SocketException {
        context = _context;
    }
    public String getPhoneNumber() {
        TelephonyManager mng = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String num = mng.getLine1Number();
        return "0"+num.substring(3);
    }

    public String getLocalHost() throws SocketException {
        try {
            Enumeration<NetworkInterface> en =  NetworkInterface.getNetworkInterfaces();
            while(en.hasMoreElements()) {
                NetworkInterface interf = en.nextElement();
                Enumeration<InetAddress> ips = interf.getInetAddresses();
                while (ips.hasMoreElements()) {
                    InetAddress inetAddress = ips.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        if (!inetAddress.getHostAddress().toString().contains(":"))
                            return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("Error", ex.toString());
        }
        return null;
    }
}


class SocketInfo {
    EditText EdtClientSendText;
    Context context;
    String strClientSendText = "";
    String strReceiveText = "";
    String strTotReceiveText = "";
    String strServerIp;
    static String my_nickname = "guest" + Integer.toString((int)(Math.random()*100));
    String opponent_nickname = "";
    int id;
    int firstServerPort = 52000;
    int secondServerPort;

    ServerSocket serverSocket= null;
    // Socket socket = null;
    Socket firstClientSocket = null;
    Socket secondClientSocket = null;
    Handler mHandler = null;
    TextView txtReceiveText;
    Button BtnClientSend;
    Button BtnGameStart;
    ScrollView scroll;
    boolean isReadyServer = false;
    boolean isReadyClient = false;
    public SocketInfo(Context _context) {
        context = _context;
        BtnGameStart = (Button)((Activity)context).findViewById(R.id.btnGameStart);
        scroll = (ScrollView)((Activity)context).findViewById(R.id.scroll);
        strServerIp = "125.189.7.250";
    }

    public void ConnectSocket() {
        InputStream in;
        try {
            //처음 공통된 포트로 연결을 한 후에 서버로부터 두번째 포트 번호를 전송받으면
            //그 포트로 다시 연결한다.

            firstClientSocket = new Socket(strServerIp, firstServerPort);   //첫 연결은 공통된 포트를 통해 이루어진다.

            //두번째 포트번호를 받는다.
            byte arrlen[] = new byte[3];
            in = firstClientSocket.getInputStream();
            in.read(arrlen);
            int portNum_len = Integer.parseInt(new String(arrlen));
            byte textBuffer[] = new byte[portNum_len];
            in.read(textBuffer);
            secondServerPort = Integer.parseInt(new String(textBuffer));

            //두번째 포트번호로 연결한다.
            secondClientSocket = new Socket(strServerIp, secondServerPort);   //처음
            isReadyClient = true;
            firstClientSocket.close();
        } catch (Exception ex) {
            Log.e("태그", ex.toString());
            Toast.makeText(context, "연결오류", Toast.LENGTH_LONG).show();
        }

    }

    public void SendFromClient(String input) {
        try {
            Log.i("socket", "보낸문자 : " + input);
            BufferedWriter out = new BufferedWriter( new OutputStreamWriter(secondClientSocket.getOutputStream()));
            out.write(input);
            out.flush();
            String message = input.substring(3);
            strTotReceiveText = strTotReceiveText + "\n"+ my_nickname +" : "+message;
            txtReceiveText.setText(strTotReceiveText);
            EdtClientSendText.setText("");
            scroll.post(new Runnable() {
                @Override
                public void run() {
                    scroll.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        } catch (Exception e) {
            Log.e("태그", e.toString());
            e.printStackTrace();
        }
    }

    public void ReceiveFromClient() {
        strTotReceiveText = strTotReceiveText + "\n"+opponent_nickname+" : "+strReceiveText;
        txtReceiveText.setText(strTotReceiveText);
        scroll.post(new Runnable() {
            @Override
            public void run() {
                scroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public void SendPacket(String string) {
        byte[] temp = string.getBytes();
        int len = temp.length;
        DecimalFormat df = new DecimalFormat("000");
        String strlen = df.format(len);
        BufferedWriter out = null;
        try {
            out = new BufferedWriter( new OutputStreamWriter(secondClientSocket.getOutputStream()));
            out.write(strlen+string);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}