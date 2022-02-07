package igra.ipcoding.coach.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import igra.ipcoding.coach.R;

public class GlavniActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    Fragment fragment;

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.item_pocetna:
                    selectedFragment = new PocetnaFragment();
                    break;
                case R.id.item_trening:
                    selectedFragment = new TreningFragment();
                    break;
                case R.id.item_taktika:
                    selectedFragment = new TaktikaFragment();
                    break;
                case R.id.item_tabela:
                    selectedFragment = new TabelaFragment();
                    break;
                case R.id.item_raspored:
                    selectedFragment = new RasporedFragment();
                    break;
            }

            ucitajFragment(selectedFragment);

            return true;
        });

        int broj = 1;

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            broj = bundle.getInt("broj");
        }

        if (broj == 5) {
            fragment = new RasporedFragment();
            bottomNavigationView.setSelectedItemId(R.id.item_raspored);
        } else {
            fragment = new PocetnaFragment();
        }

        ucitajFragment(fragment);

    }

    private void ucitajFragment(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}