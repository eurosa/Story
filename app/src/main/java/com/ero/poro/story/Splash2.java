package com.ero.poro.story;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash2 extends AppCompatActivity {
    Animation anim;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView=findViewById(R.id.imageView2); // Declare an imageView to show the animation.
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
               // startActivity(new Intent(Splash2.this,Main2Activity.class));
                // HomeActivity.class is the activity to go after showing the splash screen.



                int secondsDelayed = 1;
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        startActivity(new Intent(Splash2.this, Main2Activity.class));
                        finish();
                    }
                }, secondsDelayed * 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        imageView.startAnimation(anim);
    }
}
