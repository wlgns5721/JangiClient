package com.example.jihoon.clientsocket;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by Ji Hoon on 2016-02-22.
 */
public class IntroActivity extends Activity {
    MediaPlayer media;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void onCreate(Bundle savedInsatanceState) {
        Point point;
        super.onCreate(savedInsatanceState);
        setContentView(R.layout.intro);
        media = new MediaPlayer();
        media = MediaPlayer.create(IntroActivity.this,R.raw.music);
        media.start();
        ImageView intro_img = (ImageView)findViewById(R.id.intro_img);
        point = new Point();
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap intro = BitmapFactory.decodeResource(getResources(), R.drawable.intro);
        options.inJustDecodeBounds = true;
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        display.getSize(point);
        Bitmap resize = Bitmap.createScaledBitmap(intro, point.x, point.y, true);
        intro_img.setImageBitmap(resize);
        intro_img.setScaleType(ImageView.ScaleType.FIT_XY);
        intro_img.setPadding(0,0,0,0);
        intro_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                media.stop();
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}
