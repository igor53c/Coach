package igra.ipcoding.coach.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.atomic.AtomicReference;

import igra.ipcoding.coach.R;
import igra.ipcoding.coach.database.entity.Klub;
import igra.ipcoding.coach.database.entity.Mec;
import igra.ipcoding.coach.database.viewmodel.ObjasnjenjeViewModel;
import igra.ipcoding.coach.pomocne.Konstante;

public class ObjasnjenjeActivity extends AppCompatActivity {

    FloatingActionButton btn_dalje;

    TextView tv_objasnjenje;

    int numberWeek, godina, kolo;
    boolean rejtingUcitan;
    String liga;
    String klub;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ObjasnjenjeViewModel objasnjenjeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objasnjenje);

        init();

        ispisTeksta();

        btn_dalje.setOnClickListener(v -> {

            AtomicReference<Intent> intent = new AtomicReference<>();

            String navigiranje = "";

            if ((numberWeek > 24 && numberWeek < 30) || (numberWeek >= 1 && numberWeek < 4)) {

                navigiranje = "transfer";
            }

            if (numberWeek == 32) {

                navigiranje = "raspored";

            }

            if ((numberWeek >= 34 && numberWeek < 53) || (numberWeek >= 4 && numberWeek < 23)) {

                navigiranje = "mec";

            }

            switch (navigiranje) {
                case "transfer":
                    intent.set(new Intent(ObjasnjenjeActivity.this, TransferActivity.class));
                    startActivity(intent.get());
                    finish();
                    break;
                case "raspored":
                    Bundle b = new Bundle();
                    b.putInt("broj", 5);
                    Intent intent2 = new Intent(ObjasnjenjeActivity.this, GlavniActivity.class);
                    intent2.putExtras(b);
                    startActivity(intent2);
                    finish();
                    break;
                case "mec":
                    intent.set(new Intent(ObjasnjenjeActivity.this, PredMecActivity.class));
                    startActivity(intent.get());
                    finish();
                    break;
                default:
                    intent.set(new Intent(ObjasnjenjeActivity.this, GlavniActivity.class));
                    startActivity(intent.get());
                    finish();
                    break;
            }

        });
    }

    private void ispisTeksta() {

        if(numberWeek == 23) {

            new Thread(() -> {

                Klub mojKlub = objasnjenjeViewModel.uzmiKlub(klub);

                String text = liga + "\n\n"
                        + "The season is over!" + "\n\n\n"
                        + mojKlub.getIme() + "\n"
                        + "won\n"
                        + mojKlub.getPozicija() + "th place!";

                tv_objasnjenje.setText(text);

            }).start();

        }

        if ((numberWeek > 24 && numberWeek < 30) || (numberWeek >= 1 && numberWeek < 4)) {

            tv_objasnjenje.setText("Player transfer!");

        }

        if (numberWeek == 32) {

            new Thread(() -> {

                String text = liga + "\n\n" + "The schedule for the new season has come out!";

                Mec mec = objasnjenjeViewModel.uzmiMecKlubaIzNarednogKola(kolo, klub);

                Klub domacin = objasnjenjeViewModel.uzmiKlub(mec.getDomacin());
                Klub gost = objasnjenjeViewModel.uzmiKlub(mec.getGost());

                if(domacin.getIme().matches(klub)) {
                    text = text + "\n\n\n" + klub + "\n\n"
                            + "plays in the first round at home against\n\n"
                            + gost.getIme();
                } else {
                    text = text + "\n\n\n" + klub + "\n\n"
                            + "plays in the first round away against\n\n"
                            + domacin.getIme();
                }

                tv_objasnjenje.setText(text);

            }).start();

        }

        if ((numberWeek >= 34 && numberWeek < 53) || (numberWeek >= 4 && numberWeek < 23)) {

            new Thread(() -> {

                Mec mec = objasnjenjeViewModel.uzmiMecKlubaIzNarednogKola(kolo, klub);

                Klub domacin = objasnjenjeViewModel.uzmiKlub(mec.getDomacin());
                Klub gost = objasnjenjeViewModel.uzmiKlub(mec.getGost());

                String text = liga + "\n\n"
                        + "Round: " + kolo + "\n\n\n"
                        + domacin.getIme() + "\n"
                        + domacin.getPozicija() + "th" + "\n\n"
                        + "VS" + "\n\n"
                        + gost.getIme() + "\n"
                        + gost.getPozicija() + "th";

                tv_objasnjenje.setText(text);

            }).start();

        }

    }

    @SuppressLint("CommitPrefEdits")
    private void init() {

        btn_dalje = findViewById(R.id.btn_dalje);
        tv_objasnjenje = findViewById(R.id.tv_objasnjenje);

        sharedPreferences = getSharedPreferences(Konstante.SHARED_PREFERENCES_PREFIX,0);
        editor = sharedPreferences.edit();
        editor.putString(Konstante.SHARED_PREFERENCES_KEY_AKTIVNOST, "ObjasnjenjeActivity");
        editor.apply();
        liga = sharedPreferences.getString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "");
        klub = sharedPreferences.getString(Konstante.SHARED_PREFERENCES_KEY_KLUB, "");
        numberWeek = sharedPreferences.getInt(Konstante.SHARED_PREFERENCES_KEY_SEDMICA, 0);
        godina = sharedPreferences.getInt(Konstante.SHARED_PREFERENCES_KEY_GODINA, 0);
        kolo = sharedPreferences.getInt(Konstante.SHARED_PREFERENCES_KEY_KOLO, 1);
        rejtingUcitan = sharedPreferences.getBoolean(Konstante.SHARED_PREFERENCES_KEY_REJTING_UCITAN, false);

        objasnjenjeViewModel = new ViewModelProvider(this).get(ObjasnjenjeViewModel.class);

    }
}