package com.ero.poro.story;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class Splash extends Activity {

    public ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.splash);

        imageView=findViewById(R.id.splash_image);
        imageView.setImageResource(R.drawable.ic_star);

        //ImageView rotate_image =(ImageView) findViewById(R.id.splash_Rotate);
        RotateAnimation rotate = new RotateAnimation(30, 360, Animation.RELATIVE_TO_SELF, 0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3500);
        imageView.startAnimation(rotate);

       int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splash.this, Main2Activity.class));
                finish();
            }
        }, secondsDelayed * 3500);
    }
}
