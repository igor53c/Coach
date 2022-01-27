package igra.paket.coach.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import igra.paket.coach.R;

public class UvodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uvod);

        new Handler().postDelayed(() -> {
            Intent i = new Intent(UvodActivity.this, BiranjeKlubaActivity.class);
            startActivity(i);
            finish();
        }, 3000);
    }
}