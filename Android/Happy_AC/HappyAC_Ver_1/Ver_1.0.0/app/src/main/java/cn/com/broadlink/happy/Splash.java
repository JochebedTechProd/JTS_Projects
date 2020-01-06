package cn.com.broadlink.happy;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

public class Splash extends Activity {
    Handler handler;
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler= new Handler();

        runnable=new Runnable() {

            @Override
            public void run() {

                //startActivity(new Intent(SplashActivity.this,DashboardActivity.class));
                // startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                Intent intent = new Intent(Splash.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        };

        handler.postDelayed(runnable,2000);
    }

    }

