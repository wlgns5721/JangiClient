package com.example.jihoon.clientsocket;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Vector;


/**
 * Created by Ji Hoon on 2016-02-06.
 */

public class Gview extends SurfaceView implements SurfaceHolder.Callback {
    public static int MAIN_MODE = 1;
    public static int GAME_MODE = 2;

    Context context;
    Vector<Piece> piece_vec;
    Vector<Coordinate> path_vec;
    Bitmap zone;
    Bitmap point_img;
    Bitmap background;
    Bitmap Score_img;
    Point point;
    boolean ChoicePiece = false;
    boolean VictoryFlag = false;
    static boolean EndFlag = false;
    boolean JangGoonFlag = false;
    int Turn = 1;
    int Screen_conditioning = 150;
    Handler mHandler;

    static String isRegameMatched = "";
    static String pieceMovement = "";
    static int your_team;
    int score1 = 0;
    int score2 = 0;
    MediaPlayer media = new MediaPlayer();
    MediaPlayer media2 = new MediaPlayer();
    public Gview(Context context,AttributeSet arrts,int defStyle) {
        super(context,arrts,defStyle);
        init(context);
    }
    public Gview(Context context,AttributeSet arrts) {
        super(context,arrts);
        init(context);
    }
    public Gview(Context context) {
        super(context);
        init(context);
    }

    public void EndGame() {
        Turn = 3;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mHandler.sendMessage(Message.obtain(mHandler));


    }

    public boolean RegameMatch(boolean flag) {
        if(flag)
            ChatActivity.socketInfo.SendPacket("*%@^@REGAME");
        else {
            if(isRegameMatched.equals("NO")) {  //상대방이 NO를 먼저 보냈을 경우 나는 메시지를 보내지 않는다.
                return false;
            }
            ChatActivity.socketInfo.SendPacket("*%@^@NO");  //아직 보내지 않았거나 YES를 보냈다면 메시지를 보낸다.
            return false;
        }
        while(isRegameMatched.equals(""));
        if(isRegameMatched.equals("REGAME"))
            return true;
        else
            return false;
    }



    private GThread gthread;

    class GThread extends Thread {
        private SurfaceHolder holder;
        private Context context;
        private int game_mode = 2;

        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
        public GThread(SurfaceHolder _holder, Context _context) {
            this.holder = _holder;
            this.context = _context;
        }

        @Override
        public void run() {
            super.run();
            int count = 3;
            android.graphics.Canvas c = null;
            try {
                while (true) {
                    c = holder.lockCanvas(null);
                    synchronized (holder) {
                        try {
                            android.graphics.Paint p = new android.graphics.Paint();
                            onDraw(c);
                            Thread.sleep(10);
                        } finally {
                            if (c != null)
                                holder.unlockCanvasAndPost(c);
                        }
                    }
                }

            } catch (Exception ex) {
                Log.e("에러", ex.toString());
            }
        }

        protected void onDraw(Canvas canvas) {
            switch (game_mode) {
                case 1:
                    break;
                case 2:
                    OnDrawGame(canvas);
                    break;
            }
        }

        public void OnDrawGame(Canvas canvas) {
            android.graphics.Paint p = new android.graphics.Paint();
            p.setStyle(Paint.Style.FILL);
            p.setTextSize(130);
            p.setColor(Color.BLACK);
            canvas.drawBitmap(background, 0, 0, null);
            canvas.drawBitmap(zone, 0, Screen_conditioning, null);
            canvas.drawBitmap(Score_img, 0, 0, null);

            canvas.drawText(String.valueOf(score1),230,120,p);
            canvas.drawText(String.valueOf(score2),point.x-300,120,p);
            DrawPiece(piece_vec, canvas);
            DrawPath(path_vec, canvas);
            if (!pieceMovement.isEmpty()) {
                ReceivePieceMovement(pieceMovement);
                Turn = Turn % 2 + 1;
                pieceMovement = "";
            }



        }

        public void DrawPath(Vector<Coordinate> vec, android.graphics.Canvas c) {
            int cha_coordinate_x = point.x / 19;
            int cha_coordinate_y = point.y * 239 / 400 + Screen_conditioning;
            int distance_x = point.x * 198 / 1900;
            int distance_y = point.y * 204 / 3200;
            if (ChoicePiece) {
                for (int i = 0; i < vec.size(); i++) {
                    vec.get(i).real_x = cha_coordinate_x + vec.get(i).x * distance_x;
                    vec.get(i).real_y = cha_coordinate_y - vec.get(i).y * distance_y;
                    c.drawBitmap(point_img, vec.get(i).real_x + point_img.getWidth() * 15 / 40, vec.get(i).real_y + point_img.getHeight() / 2, null);
                }
            }
        }

        public void DrawPiece(Vector<Piece> vec, android.graphics.Canvas c) {
            int cha_coordinate_x = point.x / 19;
            int cha_coordinate_y = point.y * 239 / 400 + Screen_conditioning;
            int distance_x = point.x * 198 / 1900;
            int distance_y = point.y * 204 / 3200;
            int real_x, real_y;
            for (int i = 0; i < vec.size(); i++) {
                if (vec.get(i).character.equals("King"))
                    if (vec.get(i).team == 2) {
                        real_x = cha_coordinate_x + distance_x * vec.get(i).x - point.x / 100;
                        real_y = cha_coordinate_y - distance_y * vec.get(i).y - point.y / 500;
                    } else {
                        real_x = cha_coordinate_x + distance_x * vec.get(i).x - point.x / 150;
                        real_y = cha_coordinate_y - distance_y * vec.get(i).y - point.y / 600;
                    }
                else if (vec.get(i).character.equals("Sa") || vec.get(i).character.equals("Jol")) {
                    real_x = cha_coordinate_x + distance_x * vec.get(i).x;
                    real_y = cha_coordinate_y - distance_y * vec.get(i).y + point.y / 150;
                } else {
                    real_x = cha_coordinate_x + distance_x * vec.get(i).x;
                    real_y = cha_coordinate_y - distance_y * vec.get(i).y;
                }
                c.drawBitmap(vec.get(i).img, real_x, real_y, null);
                vec.get(i).real_x = real_x;
                vec.get(i).real_y = real_y;

            }
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void init(Context _context) {
        context = _context;
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        piece_vec = new Vector<Piece>();
        path_vec = new Vector<Coordinate>();
        SetInfo info = new SetInfo(piece_vec, _context);
        point = new Point();
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap zone_img = BitmapFactory.decodeResource(_context.getResources(), R.drawable.img);
        Bitmap background_img = BitmapFactory.decodeResource(_context.getResources(), R.drawable.background);
        Bitmap score_img =  BitmapFactory.decodeResource(_context.getResources(), R.drawable.title);
        options.inJustDecodeBounds = true;
        Display display = ((WindowManager) _context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        display.getSize(point);
        Score_img = Bitmap.createScaledBitmap(score_img,point.x,Screen_conditioning,true);
        zone = Bitmap.createScaledBitmap(zone_img, point.x, point.y * 2 / 3, true);
        point_img = info.Load_Image("point", _context);
        background = Bitmap.createScaledBitmap(background_img, point.x, point.y, true);
        VictoryFlag = false;
        EndFlag = false;
        Turn = 1;
        if (your_team==2) {
            for (int i=0; i<piece_vec.size(); i++) {
                piece_vec.get(i).x = 8 - piece_vec.get(i).x;
                piece_vec.get(i).y = 9 - piece_vec.get(i).y;
            }
        }
        media = MediaPlayer.create(_context, R.raw.putdown);
        media2 = MediaPlayer.create(_context, R.raw.delete);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (RegameMatch(true)) {
                            init(getContext());
                            EndFlag = false;
                        }
                        else {
                            EndFlag = true;
                            ChatActivity.endHandler.sendMessage(ChatActivity.endHandler.obtainMessage());
                        }
                        isRegameMatched = "";
                        dialog.dismiss();
                    }
                });
                alert.setMessage("리겜 ㄱㄱ?");
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EndFlag = true;
                        RegameMatch(false);
                        isRegameMatched = "";
                        ChatActivity.endHandler.sendMessage(ChatActivity.endHandler.obtainMessage());
                        dialog.dismiss();     //닫기
                    }
                });
                alert.show();

            }
        };
    }


    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float x = event.getX();
        final float y = event.getY();
        if (action == MotionEvent.ACTION_DOWN && Turn==your_team) {
            try {
                if (MovePiece(x, y)) {
                    return true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ShowPath(x, y);
        }

        return true;
    }


    public boolean isSameVector(Vector<Coordinate> a, Vector<Coordinate> b) {
        if (a.size() != b.size())
            return false;
        else {
            for (int i = 0; i < a.size(); i++) {
                if (a.get(i).x != b.get(i).x || a.get(i).y != b.get(i).y)
                    return false;
            }
        }
        return true;
    }

    public boolean MovePiece(float x, float y) throws InterruptedException {
        if (ChoicePiece) {
            for (int i = 0; i < path_vec.size(); i++) {
                int Width = path_vec.get(i).real_x + point_img.getWidth() + 50;
                int Height = path_vec.get(i).real_y + point_img.getHeight() + 50;

                if (x >= path_vec.get(i).real_x && x <= Width && y >= path_vec.get(i).real_y && y <= Height) {
                    piece_vec.get(path_vec.get(0).index).x = path_vec.get(i).x;
                    piece_vec.get(path_vec.get(0).index).y = path_vec.get(i).y;
                    // piece_vec.get(path_vec.get(0).index).real_x = path_vec.get(i).real_x;
                    // piece_vec.get(path_vec.get(0).index).real_y = path_vec.get(i).real_y;
                    SendPieceMovement(path_vec.get(i).x,path_vec.get(i).y,path_vec.get(0).index);
                    DeletePiece(path_vec.get(i).x, path_vec.get(i).y, path_vec.get(0).index);
                    CheckJanggoon();
                    ChoicePiece = false;
                    path_vec.clear();
                    Turn = Turn % 2 + 1;
                    return true;
                }
            }
        }
        return false;
    }

    public void DeletePiece(int x, int y, int index) throws InterruptedException {
        for (int i = 0; i < piece_vec.size(); i++) {
            if (i == index)
                continue;
            if (piece_vec.get(i).x == x && piece_vec.get(i).y == y) {
                media2.start();
                if (piece_vec.get(i).character.equals("King")) {
                    VictoryFlag = true;
                    if (piece_vec.get(i).team==1) {
                        score2++;
                    }
                    else{
                        score1++;
                    }
                    EndGame();

                }
                piece_vec.remove(i);
                return;
            }
        }
        media.start();
    }

    public void ShowPath(float x, float y) {
        for (int i = 0; i < piece_vec.size(); i++) {
            if (piece_vec.get(i).img.getWidth() + piece_vec.get(i).real_x >= x && piece_vec.get(i).real_x <= x
                    && piece_vec.get(i).img.getHeight() + piece_vec.get(i).real_y >= y && piece_vec.get(i).real_y <= y && Turn == piece_vec.get(i).team) {
                Vibrator vib = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                vib.vibrate(300);
                Path path = new Path(piece_vec.get(i), piece_vec);
                path.GetPath();
                if (isSameVector(path_vec, path.path_vec))
                    path_vec.clear();
                else {
                    path_vec = path.path_vec;
                    if (!path_vec.isEmpty())
                        path_vec.get(0).index = i;     //선택된 말을 저장
                }
                ChoicePiece = true;
                break;

            } else if (i + 1 == piece_vec.size()) {
                path_vec.clear();
                ChoicePiece = false;
            }
        }
    }

    public void SendPieceMovement(int x,int y,int index) {
        try {
            int X = 8-x;
            int Y = 9-y;
            String str = "$@d^!#d!Movement"+X+":"+Y+":"+index;
            byte[] temp = str.getBytes();
            int len = temp.length;
            DecimalFormat df = new DecimalFormat("000");
            String strlen = df.format(len);
            String string = strlen + str;
            BufferedWriter out = new BufferedWriter( new OutputStreamWriter(ChatActivity.socketInfo.secondClientSocket.getOutputStream()));
            out.write(string);
            out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void ReceivePieceMovement(String string) {
        int size = string.length();
        int x,y,index;
        String temp = string.substring(16,size);
        String[] movement = temp.split(":");
        x = Integer.parseInt(movement[0]);
        y = Integer.parseInt(movement[1]);
        index = Integer.parseInt(movement[2]);
        piece_vec.get(index).x = x;
        piece_vec.get(index).y = y;

        try {
            DeletePiece(x,y,index);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    void CheckJanggoon() {
        int king_x=-1, king_y=-1;
        for (int i=0; i<piece_vec.size(); i++) {
            if (piece_vec.elementAt(i).character.equals("King") && piece_vec.elementAt(i).team!=your_team) {
                king_x = piece_vec.elementAt(i).x;
                king_y = piece_vec.elementAt(i).y;
                break;
            }
        }
        for (int i=0; i<piece_vec.size(); i++) {
            if (piece_vec.elementAt(i).team==your_team)
                continue;

            Path path = new Path(piece_vec.get(i), piece_vec);
            path.GetPath();
            for (int s=0; s<path.path_vec.size(); s++) {
                if (path.path_vec.elementAt(s).x==king_x && path.path_vec.elementAt(s).y==king_y) {
                    JangGoonFlag = true;
                }
            }
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gthread = new GThread(getHolder(), getContext());
        gthread.start();
        /*
        while (true) {
            if (EndFlag) {
                EndGame(getContext());
            }
            if (JangGoonFlag) {
                Toast.makeText(getContext(),"장군!",Toast.LENGTH_LONG).show();
                JangGoonFlag=false;
            }
        }*/

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}


class Piece {
    public int x, y;
    public String character;
    public Bitmap img;
    public int team;
    public int real_x;
    public int real_y;

    public Piece(int piece_x, int piece_y, String piece_character, int piece_team, Bitmap piece_img) {
        x = piece_x;
        y = piece_y;
        character = piece_character;
        team = piece_team;
        img = piece_img;
        real_x = 0;
        real_y = 0;
    }
}