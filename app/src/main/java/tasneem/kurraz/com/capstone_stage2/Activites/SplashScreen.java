package tasneem.kurraz.com.capstone_stage2.Activites;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tasneem.kurraz.com.capstone_stage2.R;

public class SplashScreen extends AppCompatActivity {

    private static final long DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish();

        }, DURATION);

    }
}
