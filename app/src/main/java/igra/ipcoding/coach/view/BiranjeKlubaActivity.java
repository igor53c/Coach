package igra.ipcoding.coach.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

import igra.ipcoding.coach.R;
import igra.ipcoding.coach.database.entity.Igrac;
import igra.ipcoding.coach.database.entity.Klub;
import igra.ipcoding.coach.database.podaci.PodaciIgraci;
import igra.ipcoding.coach.database.podaci.PodaciKlubovi;
import igra.ipcoding.coach.database.viewmodel.BiranjeKlubaViewModel;
import igra.ipcoding.coach.pomocne.Konstante;


public class BiranjeKlubaActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton btn_izaberi;

    ImageFilterView ifv_dres;

    Button btn_izaberiLigu, btn_izaberiKlub;
    TextView tv_izabranaLiga, tv_izabraniKlub, tv_crvena, tv_bordo, tv_ljubicasta, tv_narandzasta, tv_zuta, tv_plava,
            tv_svetloPlava, tv_zelena, tv_svetloSiva, tv_tamnoSiva, tv_dres;

    PodaciKlubovi podaciKlubovi;
    PodaciIgraci podaciIgraci;
    int rejtingMin, rejtingMax, dres;

    BiranjeKlubaViewModel biranjeKlubaViewModel;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biranje_kluba);

        sharedPreferences = getSharedPreferences(Konstante.SHARED_PREFERENCES_PREFIX, 0);
        editor = sharedPreferences.edit();

        String activity = sharedPreferences.getString(Konstante.SHARED_PREFERENCES_KEY_AKTIVNOST, "");

        Intent intent;

        switch (activity) {

            case "PredMecActivity":
                intent = new Intent(BiranjeKlubaActivity.this, PredMecActivity.class);
                startActivity(intent);
                finish();
                break;

            case "ObjasnjenjeActivity":
                intent = new Intent(BiranjeKlubaActivity.this, ObjasnjenjeActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                String klub = sharedPreferences.getString(Konstante.SHARED_PREFERENCES_KEY_KLUB, "");

                if(!klub.matches("")) {

                    intent = new Intent(BiranjeKlubaActivity.this, GlavniActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    init();

                    dres = R.color.crvena_dres;
                    ifv_dres.setColorFilter(getColor(dres));

                }
                break;
        }

    }

    @SuppressLint("CommitPrefEdits")
    private void init() {

        btn_izaberi = findViewById(R.id.btn_izaberi);
        btn_izaberiLigu = findViewById(R.id.btn_izaberiLigu);
        btn_izaberiKlub = findViewById(R.id.btn_izaberiKlub);
        tv_izabranaLiga = findViewById(R.id.tv_izabranaLiga);
        tv_izabraniKlub = findViewById(R.id.tv_izabraniKlub);
        tv_crvena = findViewById(R.id.tv_crvena);
        tv_bordo = findViewById(R.id.tv_bordo);
        tv_ljubicasta = findViewById(R.id.tv_ljubicasta);
        tv_narandzasta = findViewById(R.id.tv_narandzasta);
        tv_zuta = findViewById(R.id.tv_zuta);
        tv_plava = findViewById(R.id.tv_plava);
        tv_svetloPlava = findViewById(R.id.tv_svetloPlava);
        tv_zelena = findViewById(R.id.tv_zelena);
        tv_svetloSiva = findViewById(R.id.tv_svetloSiva);
        tv_tamnoSiva = findViewById(R.id.tv_tamnoSiva);
        tv_dres = findViewById(R.id.tv_dres);
        ifv_dres = findViewById(R.id.ifv_dres);

        podaciIgraci = new PodaciIgraci();
        podaciKlubovi = new PodaciKlubovi();

        editor.putString(Konstante.SHARED_PREFERENCES_KEY_AKTIVNOST, "BiranjeKlubaActivity");
        editor.apply();

        biranjeKlubaViewModel = new ViewModelProvider(this).get(BiranjeKlubaViewModel.class);

        btn_izaberi.setOnClickListener(this);
        btn_izaberiLigu.setOnClickListener(this);
        btn_izaberiKlub.setOnClickListener(this);
        tv_crvena.setOnClickListener(this);
        tv_bordo.setOnClickListener(this);
        tv_ljubicasta.setOnClickListener(this);
        tv_narandzasta.setOnClickListener(this);
        tv_zuta.setOnClickListener(this);
        tv_plava.setOnClickListener(this);
        tv_svetloPlava.setOnClickListener(this);
        tv_zelena.setOnClickListener(this);
        tv_svetloSiva.setOnClickListener(this);
        tv_tamnoSiva.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        btn_izaberi.setClickable(false);
        btn_izaberiLigu.setClickable(false);
        btn_izaberiKlub.setClickable(false);
        tv_crvena.setClickable(false);
        tv_bordo.setClickable(false);
        tv_ljubicasta.setClickable(false);
        tv_narandzasta.setClickable(false);
        tv_zuta.setClickable(false);
        tv_plava.setClickable(false);
        tv_svetloPlava.setClickable(false);
        tv_zelena.setClickable(false);
        tv_svetloSiva.setClickable(false);
        tv_tamnoSiva.setClickable(false);

        new Handler().postDelayed(() -> {

            btn_izaberi.setClickable(true);
            btn_izaberiLigu.setClickable(true);
            btn_izaberiKlub.setClickable(true);
            tv_crvena.setClickable(true);
            tv_bordo.setClickable(true);
            tv_ljubicasta.setClickable(true);
            tv_narandzasta.setClickable(true);
            tv_zuta.setClickable(true);
            tv_plava.setClickable(true);
            tv_svetloPlava.setClickable(true);
            tv_zelena.setClickable(true);
            tv_svetloSiva.setClickable(true);
            tv_tamnoSiva.setClickable(true);

        }, 500);

        PopupMenu popup = new PopupMenu(BiranjeKlubaActivity.this, v);

        switch (v.getId()) {
            case R.id.tv_crvena:

                dres = R.color.crvena_dres;
                ifv_dres.setColorFilter(getColor(dres));

                break;
            case R.id.tv_bordo:

                dres = R.color.bordo_dres;
                ifv_dres.setColorFilter(getColor(dres));

                break;
            case R.id.tv_ljubicasta:

                dres = R.color.ljubicasta_dres;
                ifv_dres.setColorFilter(getColor(dres));

                break;
            case R.id.tv_narandzasta:

                dres = R.color.narandzasta_dres;
                ifv_dres.setColorFilter(getColor(dres));

                break;
            case R.id.tv_zuta:

                dres = R.color.zuta_dres;
                ifv_dres.setColorFilter(getColor(dres));

                break;
            case R.id.tv_plava:

                dres = R.color.plava_dres;
                ifv_dres.setColorFilter(getColor(dres));

                break;
            case R.id.tv_svetloPlava:

                dres = R.color.svetlo_plava_dres;
                ifv_dres.setColorFilter(getColor(dres));

                break;
            case R.id.tv_zelena:

                dres = R.color.zelena_dres;
                ifv_dres.setColorFilter(getColor(dres));

                break;
            case R.id.tv_svetloSiva:

                dres = R.color.svetlo_siva_dres;
                ifv_dres.setColorFilter(getColor(dres));

                break;
            case R.id.tv_tamnoSiva:

                dres = R.color.tamno_siva_dres;
                ifv_dres.setColorFilter(getColor(dres));

                break;
            case R.id.btn_izaberi:
                if(String.valueOf(tv_izabranaLiga.getText()).equals("") || String.valueOf(tv_izabraniKlub.getText()).equals("")) {
                    Toast.makeText(BiranjeKlubaActivity.this, "Choose a league and a club!", Toast.LENGTH_SHORT).show();
                } else {

                    popuniKlubove(tv_izabraniKlub.getText().toString());

                    editor.putInt(Konstante.SHARED_PREFERENCES_KEY_SEDMICA, 24);
                    editor.putInt(Konstante.SHARED_PREFERENCES_KEY_GODINA, 2021);
                    editor.putInt(Konstante.SHARED_PREFERENCES_KEY_KOLO, 1);
                    editor.putInt(Konstante.SHARED_PREFERENCES_KEY_TAKTIKA, 3);
                    editor.putBoolean(Konstante.SHARED_PREFERENCES_KEY_REJTING_UCITAN, false);
                    editor.putInt(Konstante.SHARED_PREFERENCES_KEY_DRES, dres);
                    editor.apply();
                    Intent intent = new Intent(BiranjeKlubaActivity.this, GlavniActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.btn_izaberiLigu:
                popup.getMenuInflater().inflate(R.menu.izaberi_ligu, popup.getMenu());

                popup.setOnMenuItemClickListener(item -> {
                    String liga = item.toString();
                    tv_izabranaLiga.setText(liga);
                    editor.putString(Konstante.SHARED_PREFERENCES_KEY_LIGA, liga);
                    tv_izabraniKlub.setText("");
                    editor.putString(Konstante.SHARED_PREFERENCES_KEY_KLUB, "");
                    editor.apply();
                    return true;
                });

                popup.show();
                break;
            case R.id.btn_izaberiKlub:
                PodaciKlubovi podaciKlubovi = new PodaciKlubovi();

                rejtingMin = 0;
                rejtingMax = 0;

                switch (tv_izabranaLiga.getText().toString()){
                    case "League 1":
                        rejtingMin = 85;
                        rejtingMax = 95;
                        for(int i=0; i<20; i++) {
                            popup.getMenu().add(podaciKlubovi.uzmiKlub(i));
                        }
                        break;
                    case "League 2":
                        rejtingMin = 75;
                        rejtingMax = 85;
                        for(int i=20; i<40; i++) {
                            popup.getMenu().add(podaciKlubovi.uzmiKlub(i));
                        }
                        break;
                    case "League 3":
                        rejtingMin = 65;
                        rejtingMax = 75;
                        for(int i=40; i<60; i++) {
                            popup.getMenu().add(podaciKlubovi.uzmiKlub(i));
                        }
                        break;
                    case "League 4":
                        rejtingMin = 55;
                        rejtingMax = 65;
                        for(int i=60; i<80; i++) {
                            popup.getMenu().add(podaciKlubovi.uzmiKlub(i));
                        }
                        break;
                    case "League 5":
                        rejtingMin = 45;
                        rejtingMax = 55;
                        for(int i=80; i<100; i++) {
                            popup.getMenu().add(podaciKlubovi.uzmiKlub(i));
                        }
                        break;
                    case "League 6":
                        rejtingMin = 35;
                        rejtingMax = 45;
                        for(int i=100; i<120; i++) {
                            popup.getMenu().add(podaciKlubovi.uzmiKlub(i));
                        }
                        break;
                    case "League 7":
                        rejtingMin = 25;
                        rejtingMax = 35;
                        for(int i=120; i<140; i++) {
                            popup.getMenu().add(podaciKlubovi.uzmiKlub(i));
                        }
                        break;
                    default:
                        break;
                }

                popup.getMenuInflater().inflate(R.menu.liga, popup.getMenu());

                popup.setOnMenuItemClickListener(item -> {
                    String klub = item.toString();
                    tv_izabraniKlub.setText(klub);
                    editor.putString(Konstante.SHARED_PREFERENCES_KEY_KLUB, klub);
                    editor.apply();
                    return true;
                });

                popup.show();
                break;
            default: break;
        }

    }

    private void popuniKlubove(String imeKluba) {

       new Thread(() -> {

           biranjeKlubaViewModel.obrisiSveKlubove();
           biranjeKlubaViewModel.obrisiSveMeceve();
           biranjeKlubaViewModel.obrisiCeluIstoriju();
           biranjeKlubaViewModel.obrisiSveUtakmice();

           for (int i = 0; i < 140; i++) {

               String liga = "";
               double rej = 0;

               if (i < 20) {
                   liga = "League 1";
                   rej = 85 + 10 - (i % 20.0) / 2;
               }

               if (i >= 20 && i < 40) {
                   liga = "League 2";
                   rej = 75 + 10 - (i % 20.0) / 2;
               }

               if (i >= 40 && i < 60) {
                   liga = "League 3";
                   rej = 65 + 10 - (i % 20.0) / 2;
               }

               if (i >= 60 && i < 80) {
                   liga = "League 4";
                   rej = 55 + 10 - (i % 20.0) / 2;
               }

               if (i >= 80 && i < 100) {
                   liga = "League 5";
                   rej = 45 + 10 - (i % 20.0) / 2;
               }

               if (i >= 100 && i < 120) {
                   liga = "League 6";
                   rej = 35 + 10 - (i % 20.0) / 2;
               }

               if (i >= 120) {
                   liga = "League 7";
                   rej = 25 + 10 - (i % 20.0) / 2;
               }

               String ime = podaciKlubovi.uzmiKlub(i);

               Klub klub = new Klub(0, ime, liga, (i % 20) + 1, rej, 0, 0, 0,
                       0, 0, 0, 0);

               biranjeKlubaViewModel.insertKlub(klub);


               if(klub.getIme().matches(imeKluba)) {
                   popuniIgrace(biranjeKlubaViewModel.uzmiRejtingKluba(imeKluba));
               }
           }
       }).start();

    }

    private void popuniIgrace(double rejtingKluba) {

        biranjeKlubaViewModel.obrisiSveIgrace();

        for (int i = 0; i < 18; i++) {

            String pozicija;

            if (i < 2) {
                pozicija = "GK";
            } else {
                String[] poz;
                if (i < 8) {
                    poz = new String[]{"DR", "DC", "DL", "DRC", "DRL", "DLC"};
                } else {
                    if (i < 14) {
                        poz = new String[]{"MR", "MC", "ML", "MRC", "MRL", "MLC"};
                    } else {
                        poz = new String[]{"FR", "FC", "FL", "FRC", "FRL", "FLC"};
                    }
                }
                pozicija = poz[uzmiBrojIzOpsega(0,5)];
            }

            int rej = uzmiBrojIzOpsega((int) Math.round(rejtingKluba - 8), (int) Math.round(rejtingKluba + 4));
            int godine;
            if(rejtingKluba - rej < 4) {
                godine = uzmiBrojIzOpsega(31, 36);
            } else {
                if(rejtingKluba - rej < 0) {
                    godine = uzmiBrojIzOpsega(24, 30);
                } else {
                    godine = uzmiBrojIzOpsega(18, 23);
                }
            }
            Igrac igrac = new Igrac(0, uzmiSlovo() + " " +
                    podaciIgraci.uzmiIgraca(uzmiBrojIzOpsega(0, podaciIgraci.brojIgraca() - 1)),
                    pozicija, rej, godine, i,100,100,0);

            biranjeKlubaViewModel.insertIgrac(igrac);

        }

    }

    private static int uzmiBrojIzOpsega(int min, int max) {

        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

    private static char uzmiSlovo() {
        Random rnd = new Random();
        return (char) ('A' + rnd.nextInt(26));
    }
}