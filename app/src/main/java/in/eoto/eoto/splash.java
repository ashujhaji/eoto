package in.eoto.eoto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends AppCompatActivity {
    private static final boolean DEFAULT = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread  =new Thread(){
            public void run(){
                try {
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyData",0);
                    boolean loginStatus = sharedPreferences.getBoolean("hasLoggedIn",DEFAULT);
                    if (loginStatus == true){
                        Intent intent = new Intent(splash.this, trending.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(splash.this, login.class);
                        startActivity(intent);
                    }
                }
            }
        };
        thread.start();
    }
}
