package igra.ipcoding.coach.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Random;

import igra.ipcoding.coach.R;
import igra.ipcoding.coach.database.entity.Igrac;
import igra.ipcoding.coach.database.podaci.PodaciIgraci;
import igra.ipcoding.coach.database.viewmodel.ZamenaIgracaViewModel;
import igra.ipcoding.coach.pomocne.Konstante;

public class ZamenaIgracaActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_stariIgrac, tv_noviIgrac;
    Button btn_odustani, btn_zameni, btn_levo, btn_desno;
    ZamenaIgracaViewModel zamenaIgracaViewModel;

    Igrac stariIgrac;
    Igrac noviIgrac, noviIgrac1, noviIgrac2, noviIgrac3;

    PodaciIgraci podaciIgraci;

    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zamena_igraca);

        init();

        Bundle bundle = getIntent().getExtras();

        int igracID = bundle.getInt("igracID", 0);
        String pozicija = bundle.getString("pozicija", "");

        ispisStarogIgraca(igracID,pozicija);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        btn_odustani.setClickable(false);
        btn_zameni.setClickable(false);
        btn_levo.setClickable(false);
        btn_desno.setClickable(false);

        new Handler().postDelayed(() -> {

            btn_odustani.setClickable(true);
            btn_zameni.setClickable(true);
            btn_levo.setClickable(true);
            btn_desno.setClickable(true);
        }, 500);

        Intent intent = new Intent(ZamenaIgracaActivity.this, GlavniActivity.class);

        switch (v.getId()) {
            case R.id.btn_levo:
                if(noviIgrac == noviIgrac1) {
                    noviIgrac = noviIgrac3;
                } else {
                    if(noviIgrac == noviIgrac2) {
                        noviIgrac = noviIgrac1;
                    } else {
                        noviIgrac = noviIgrac2;
                    }
                }
                tv_noviIgrac.setText(igracText(noviIgrac));
                break;

            case R.id.btn_desno:
                if(noviIgrac == noviIgrac1) {
                    noviIgrac = noviIgrac2;
                } else {
                    if(noviIgrac == noviIgrac2) {
                        noviIgrac = noviIgrac3;
                    } else {
                        noviIgrac = noviIgrac1;
                    }
                }
                tv_noviIgrac.setText(igracText(noviIgrac));
                break;

            case R.id.btn_odustani:
                startActivity(intent);
                finish();
                break;

            case R.id.btn_zameni:
                new Thread(() -> {

                    zamenaIgracaViewModel.updateIgrac(noviIgrac);
                    startActivity(intent);

                    finish();

                }).start();
                break;

            default: break;
        }

    }

    private Igrac izborNovogIgraca(String pozicija, Igrac igrac) {

        String [] poz = new String[]{"GK", "DR", "DC", "DL", "DRC", "DRL", "DLC", "MR", "MC", "ML", "MRC", "MRL", "MLC",
                "FR", "FC", "FL", "FRC", "FRL", "FLC"};

        double rejting;

        switch (pozicija.length()) {

            case 2:
                rejting = igrac.getRejting() - 2.0;
                break;
            case 3:
                rejting = igrac.getRejting() - 4.0;
                break;
            default:
                rejting = igrac.getRejting() + 2.0;
                pozicija = poz[uzmiBrojIzOpsega(0,poz.length - 1)];
                break;
        }

        int rej = uzmiBrojIzOpsega((int) Math.round(rejting - 2), (int) Math.round(rejting + 2));
        int godine = uzmiBrojIzOpsega(18, 36);
        rej = rej + ((godine - igrac.getGodine()) / 2);

        return new Igrac(igrac.getId(), uzmiSlovo() + " " + podaciIgraci.uzmiIgraca(uzmiBrojIzOpsega(0, podaciIgraci.brojIgraca() - 1)),
                pozicija, ograniciRejting(rej), godine, igrac.getBroj(), 100, 100, 0);
    }

    private String igracText(Igrac igrac) {
        return igrac.getIme() + "\n" + new DecimalFormat("0.0").format(igrac.getRejting())
                + "\n" + igrac.getPozicija() + "\n" + igrac.getGodine();
    }

    private void ispisStarogIgraca(int id, String pozicija) {

        new Thread(() -> {
            stariIgrac = zamenaIgracaViewModel.uzmiIgracaPoID(id);

            tv_stariIgrac.setText(igracText(stariIgrac));

            noviIgrac1 = izborNovogIgraca(pozicija, stariIgrac);
            noviIgrac2 = izborNovogIgraca(pozicija, stariIgrac);
            noviIgrac3 = izborNovogIgraca(pozicija, stariIgrac);

            noviIgrac = noviIgrac1;
            tv_noviIgrac.setText(igracText(noviIgrac));

        }).start();

    }

    @SuppressLint("CommitPrefEdits")
    private void init() {

        sharedPreferences = getSharedPreferences(Konstante.SHARED_PREFERENCES_PREFIX,0);
        editor = sharedPreferences.edit();
        editor.putString(Konstante.SHARED_PREFERENCES_KEY_AKTIVNOST, "ZamenaIgracaActivity");
        editor.apply();

        tv_stariIgrac = findViewById(R.id.tv_stariIgrac);
        tv_noviIgrac = findViewById(R.id.tv_noviIgrac);
        btn_odustani = findViewById(R.id.btn_odustani);
        btn_zameni = findViewById(R.id.btn_zameni);
        btn_levo = findViewById(R.id.btn_levo);
        btn_desno = findViewById(R.id.btn_desno);

        btn_odustani.setOnClickListener(this);
        btn_zameni.setOnClickListener(this);
        btn_levo.setOnClickListener(this);
        btn_desno.setOnClickListener(this);

        zamenaIgracaViewModel = new ViewModelProvider(this).get(ZamenaIgracaViewModel.class);

        podaciIgraci = new PodaciIgraci();
    }

    private static int uzmiBrojIzOpsega(int min, int max) {

        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    private static char uzmiSlovo() {
        Random rnd = new Random();
        return (char) ('A' + rnd.nextInt(26));
    }

    private static int ograniciRejting(int rejting) {
        if(rejting > 99) {
            rejting = 99;
        } else {
            if (rejting < 1) {
                rejting = 1;
            }
        }

        return rejting;
    }
}