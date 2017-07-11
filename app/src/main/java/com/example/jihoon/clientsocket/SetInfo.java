package com.example.jihoon.clientsocket;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import java.util.Vector;

/**
 * Created by Ji Hoon on 2016-02-16.
 */
public class SetInfo {
    public SetInfo(Vector<Piece> piece_vec,Context context) {
        Put_piece_info(piece_vec,2,context);
        Put_piece_info(piece_vec,1,context);


    }
    public Bitmap Load_Image(String image_name,Context context) {
        Bitmap img = BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier(image_name, "drawable", context.getPackageName()));
        return img;
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void Put_piece_info(Vector<Piece> vec,int team,Context context) {
        String str;
        int conditioning_value=9;
        if (team==2) {
            str = "_red";
        }
        else {
            conditioning_value=0;
            str = "";
        }
        Piece piece;
        piece = new Piece(0,0+conditioning_value,"Cha",team,Load_Image("piece3"+str,context));
        vec.add(piece);
        piece = new Piece(8,0+conditioning_value,"Cha",team,Load_Image("piece3"+str,context));
        vec.add(piece);
        piece = new Piece(1,0+conditioning_value,"Ma",team,Load_Image("piece5"+str,context));
        vec.add(piece);
        piece = new Piece(7,0+conditioning_value,"Ma",team,Load_Image("piece5"+str,context));
        vec.add(piece);
        piece = new Piece(2,0+conditioning_value,"Sang",team,Load_Image("piece6"+str,context));
        vec.add(piece);
        piece = new Piece(6,0+conditioning_value,"Sang",team,Load_Image("piece6"+str,context));
        vec.add(piece);
        piece = new Piece(3,0+conditioning_value,"Sa",team,Load_Image("piece1"+str,context));
        vec.add(piece);
        piece = new Piece(5,0+conditioning_value,"Sa",team,Load_Image("piece1"+str,context));
        vec.add(piece);
        if (conditioning_value!=0)
            conditioning_value-=2;
        piece = new Piece(4,1+conditioning_value,"King",team,Load_Image("piece0"+str,context));
        vec.add(piece);
        if (conditioning_value!=0)
            conditioning_value-=2;
        piece = new Piece(1,2+conditioning_value,"Po",team,Load_Image("piece4"+str,context));
        vec.add(piece);
        piece = new Piece(7,2+conditioning_value,"Po",team,Load_Image("piece4"+str,context));
        vec.add(piece);
        if (conditioning_value!=0)
            conditioning_value-=2;
        for (int i=0; i<5; i++) {
            piece = new Piece(i*2,3+conditioning_value,"Jol",team,Load_Image("piece2"+str,context));
            vec.add(piece);
        }
    }
}
