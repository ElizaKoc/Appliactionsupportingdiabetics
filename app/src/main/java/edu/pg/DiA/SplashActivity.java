package edu.pg.DiA;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SplashActivity extends AppCompatActivity {

    //Constant Time Delay
    private final int SPLASH_DELAY = 5000;

    //Fields
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setBackgroundDrawable(null);

        initializeView();
        animateLogo();
        changeActivity();
    }

    private void initializeView() {
        iv = (ImageView) findViewById(R.id.logo);
        iv.setImageResource(R.drawable.ic_logo_dia);
    }

    private void animateLogo() {

        Animation fadingInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadingInAnimation.setInterpolator(new LinearInterpolator());
        fadingInAnimation.setDuration(SPLASH_DELAY);
        iv.startAnimation(fadingInAnimation);
    }

    private void changeActivity() {

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            this.finish();
        }, SPLASH_DELAY);
    }
}