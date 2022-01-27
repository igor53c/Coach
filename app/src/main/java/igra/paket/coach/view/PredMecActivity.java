package igra.paket.coach.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import igra.paket.coach.R;
import igra.paket.coach.database.entity.Igrac;
import igra.paket.coach.database.entity.Klub;
import igra.paket.coach.database.entity.Mec;
import igra.paket.coach.database.entity.Utakmice;
import igra.paket.coach.database.podaci.PodaciRezultat;
import igra.paket.coach.database.podaci.PodaciTaktika;
import igra.paket.coach.database.viewmodel.PredMecViewModel;
import igra.paket.coach.pomocne.IgracTextDres;
import igra.paket.coach.pomocne.Konstante;
import igra.paket.coach.pomocne.PrethodnoKliknutiIgrac;
import igra.paket.coach.pomocne.Taktika;

public class PredMecActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences.Editor editor;
    int taktikaBroj, dres, boja, belaBoja, crnaBoja;

    FloatingActionButton btn_odigrajMec;

    Drawable drawable;

    Button btn_prethodnaTaktika, btn_sledecaTaktika;
    ConstraintLayout ll_gk, ll_dr, ll_drc, ll_dc, ll_dlc, ll_dl, ll_mr, ll_mrc, ll_mc, ll_mlc, ll_ml, ll_frc, ll_fc, ll_flc;
    TextView tv_igrac1, tv_igrac2, tv_igrac3, tv_igrac4, tv_igrac5, tv_igrac6, tv_igrac7, tv_igrac8, tv_igrac9, tv_igrac10, tv_igrac11,
            tv_igracRez1, tv_igracRez2, tv_igracRez3, tv_igracRez4, tv_igracRez5, tv_igracRez6, tv_igracRez7,
            tv_ime, tv_pozicija, tv_rejting, tv_godine, tv_imeText, tv_pozicijaText, tv_rejtingText, tv_godineText, pb_motivacijaText, pb_fitnesText;
    ImageFilterView ifv_igrac1, ifv_igrac2, ifv_igrac3,ifv_igrac4, ifv_igrac5, ifv_igrac6, ifv_igrac7, ifv_igrac8, ifv_igrac9, ifv_igrac10, ifv_igrac11,
            ifv_igracRez1, ifv_igracRez2, ifv_igracRez3, ifv_igracRez4, ifv_igracRez5, ifv_igracRez6, ifv_igracRez7;
    ProgressBar pb_motivacija, pb_fitnes;
    IgracTextDres sviIgraci;
    List<Igrac> igracList;
    PodaciTaktika podaciTaktika;
    TextView tv_trenutnaTaktika, tv_rejtingPostave;
    PredMecViewModel predMecViewModel;
    PrethodnoKliknutiIgrac prethodnoKliknutiIgrac;
    String klub;
    int kolo;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pred_mec);

        init();

        tv_trenutnaTaktika.setText(podaciTaktika.uzmiTaktiku(taktikaBroj).getIme());

        ucitajListuIgraca();

        predMecViewModel.uzmiSveIgracePoBrojuObserver().observe(this, igraci -> {

            for(int i=0; i<igraci.size(); i++) {
                String text = new DecimalFormat("0.0").format(igraci.get(i).getRejting())
                        + "\n" + igraci.get(i).getPozicija() + "\n" + igraci.get(i).getGodine();
                sviIgraci.getInformacije().get(i).setText(text);
            }

            Taktika taktika = podaciTaktika.uzmiTaktiku(taktikaBroj);

            promeniIzgledFormacije(taktika);
            double rejting = proveriIgrace(taktika, igraci);
            zameniRejtingKluba(rejting);
            tv_rejtingPostave.setText(new DecimalFormat("0.0").format(rejting));

        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        btn_odigrajMec.setClickable(false);
        btn_sledecaTaktika.setClickable(false);
        btn_prethodnaTaktika.setClickable(false);
        tv_igrac1.setClickable(false);
        tv_igrac2.setClickable(false);
        tv_igrac3.setClickable(false);
        tv_igrac4.setClickable(false);
        tv_igrac5.setClickable(false);
        tv_igrac6.setClickable(false);
        tv_igrac7.setClickable(false);
        tv_igrac8.setClickable(false);
        tv_igrac9.setClickable(false);
        tv_igrac10.setClickable(false);
        tv_igrac11.setClickable(false);
        tv_igracRez1.setClickable(false);
        tv_igracRez2.setClickable(false);
        tv_igracRez3.setClickable(false);
        tv_igracRez4.setClickable(false);
        tv_igracRez5.setClickable(false);
        tv_igracRez6.setClickable(false);
        tv_igracRez7.setClickable(false);

        new Handler().postDelayed(() -> {

            btn_odigrajMec.setClickable(true);
            btn_sledecaTaktika.setClickable(true);
            btn_prethodnaTaktika.setClickable(true);
            tv_igrac1.setClickable(true);
            tv_igrac2.setClickable(true);
            tv_igrac3.setClickable(true);
            tv_igrac4.setClickable(true);
            tv_igrac5.setClickable(true);
            tv_igrac6.setClickable(true);
            tv_igrac7.setClickable(true);
            tv_igrac8.setClickable(true);
            tv_igrac9.setClickable(true);
            tv_igrac10.setClickable(true);
            tv_igrac11.setClickable(true);
            tv_igracRez1.setClickable(true);
            tv_igracRez2.setClickable(true);
            tv_igracRez3.setClickable(true);
            tv_igracRez4.setClickable(true);
            tv_igracRez5.setClickable(true);
            tv_igracRez6.setClickable(true);
            tv_igracRez7.setClickable(true);

        }, 500);

        switch (v.getId()) {

            case R.id.btn_odigrajMec:

                kolo = sharedPreferences.getInt(Konstante.SHARED_PREFERENCES_KEY_KOLO, 1);

                new Thread(() -> {

                    for(int i=0; i < igracList.size(); i++) {
                        igracList.get(i).setBroj(i);
                        predMecViewModel.updateIgrac(igracList.get(i));
                    }

                    zameniRejtingKluba(proveriIgrace(podaciTaktika.uzmiTaktiku(taktikaBroj), igracList));
                    editor.putInt(Konstante.SHARED_PREFERENCES_KEY_TAKTIKA, taktikaBroj);
                    editor.apply();

                    String rezultatKluba = odigrajKolo(kolo, klub);
                    promeniRejtingIgracaNaOsnovuRezultata(rezultatKluba);

                    if (kolo > 0 && kolo < 38) {
                        kolo++;
                    }

                    editor.putInt(Konstante.SHARED_PREFERENCES_KEY_KOLO, kolo);
                    editor.apply();

                    Bundle b = new Bundle();
                    b.putInt("broj", 5);
                    Intent intent2 = new Intent(PredMecActivity.this, GlavniActivity.class);
                    intent2.putExtras(b);
                    startActivity(intent2);
                    finish();
                }).start();

                break;
            case R.id.btn_sledecaTaktika:
                taktikaBroj++;
                if(taktikaBroj == podaciTaktika.brojTaktika()) {
                    taktikaBroj = 0;
                }
                tv_trenutnaTaktika.setText(podaciTaktika.uzmiTaktiku(taktikaBroj).getIme());
                promeniIzgledFormacije(podaciTaktika.uzmiTaktiku(taktikaBroj));
                tv_rejtingPostave.setText(
                        new DecimalFormat("0.0").format(proveriIgrace(podaciTaktika.uzmiTaktiku(taktikaBroj), igracList)));
                break;
            case R.id.btn_prethodnaTaktika:
                taktikaBroj--;
                if(taktikaBroj < 0) {
                    taktikaBroj = podaciTaktika.brojTaktika() - 1;
                }
                tv_trenutnaTaktika.setText(podaciTaktika.uzmiTaktiku(taktikaBroj).getIme());
                promeniIzgledFormacije(podaciTaktika.uzmiTaktiku(taktikaBroj));
                tv_rejtingPostave.setText(
                        new DecimalFormat("0.0").format(proveriIgrace(podaciTaktika.uzmiTaktiku(taktikaBroj), igracList)));
                break;
            case R.id.tv_igrac1:
                promeniPozicijeIgracima(v, 0);
                break;
            case R.id.tv_igrac2:
                promeniPozicijeIgracima(v, 1);
                break;
            case R.id.tv_igrac3:
                promeniPozicijeIgracima(v, 2);
                break;
            case R.id.tv_igrac4:
                promeniPozicijeIgracima(v,3);
                break;
            case R.id.tv_igrac5:
                promeniPozicijeIgracima(v,4);
                break;
            case R.id.tv_igrac6:
                promeniPozicijeIgracima(v,5);
                break;
            case R.id.tv_igrac7:
                promeniPozicijeIgracima(v,6);
                break;
            case R.id.tv_igrac8:
                promeniPozicijeIgracima(v,7);
                break;
            case R.id.tv_igrac9:
                promeniPozicijeIgracima(v,8);
                break;
            case R.id.tv_igrac10:
                promeniPozicijeIgracima(v,9);
                break;
            case R.id.tv_igrac11:
                promeniPozicijeIgracima(v,10);
                break;
            case R.id.tv_igracRez1:
                promeniPozicijeIgracima(v,11);
                break;
            case R.id.tv_igracRez2:
                promeniPozicijeIgracima(v,12);
                break;
            case R.id.tv_igracRez3:
                promeniPozicijeIgracima(v,13);
                break;
            case R.id.tv_igracRez4:
                promeniPozicijeIgracima(v,14);
                break;
            case R.id.tv_igracRez5:
                promeniPozicijeIgracima(v,15);
                break;
            case R.id.tv_igracRez6:
                promeniPozicijeIgracima(v,16);
                break;
            case R.id.tv_igracRez7:
                promeniPozicijeIgracima(v,17);
                break;
            default: break;
        }
    }

    public void promeniRejtingIgracaNaOsnovuRezultata(String rezultat) {

        List<Igrac> igracList = predMecViewModel.uzmiSveIgrace();
        int datiGolovi = Integer.parseInt(String.valueOf(rezultat.charAt(0)));
        int primljeniGolovi = Integer.parseInt(String.valueOf(rezultat.charAt(2)));
        for (int i = 0; i < 11; i++) {
            if(datiGolovi > primljeniGolovi) {
                igracList.get(i).setRejting(igracList.get(i).getRejting() + (36 - igracList.get(i).getGodine()) * 0.005);
                if(igracList.get(i).getRejting() > 99) {
                    igracList.get(i).setRejting(99.0);
                }
                igracList.get(i).setMotivacija(igracList.get(i).getMotivacija() + 20);
                if(igracList.get(i).getMotivacija() > 100) {
                    igracList.get(i).setMotivacija(100);
                }
            } else {
                if (datiGolovi < primljeniGolovi) {
                    igracList.get(i).setRejting(igracList.get(i).getRejting() - (igracList.get(i).getGodine() - 16) * 0.005);
                    if(igracList.get(i).getRejting() < 1) {
                        igracList.get(i).setRejting(1.0);
                    }
                    igracList.get(i).setMotivacija(igracList.get(i).getMotivacija() - 20);
                    if(igracList.get(i).getMotivacija() < 0) {
                        igracList.get(i).setMotivacija(0);
                    }
                }
            }
            igracList.get(i).setSpremnost(igracList.get(i).getSpremnost() - 20);
            if(igracList.get(i).getSpremnost() < 0) {
                igracList.get(i).setSpremnost(0);
            }
            predMecViewModel.updateIgrac(igracList.get(i));
        }

        for(int i = 11; i < 18; i++) {
            igracList.get(i).setMotivacija(igracList.get(i).getMotivacija() + 20);
            if(igracList.get(i).getMotivacija() > 100) {
                igracList.get(i).setMotivacija(100);
            }
            predMecViewModel.updateIgrac(igracList.get(i));
        }
    }

    public String odigrajKolo(int kolo, String klub) {

        String vratiRezultatKluba = "";

        List<Mec> mecList = predMecViewModel.uzmiMeceveIzJednogKola(kolo);
        int razlikaKlubova;
        int golDomacin;
        int golGost;
        String rezultat;
        PodaciRezultat podaciRezultat = new PodaciRezultat();

        for(int i=0; i<10; i++) {
            Mec mec = mecList.get(i);
            String domacin = mec.getDomacin();
            String gost = mec.getGost();
            razlikaKlubova = (int) ((predMecViewModel.uzmiRejtingKluba(domacin) - predMecViewModel.uzmiRejtingKluba(gost)) * 6 + 10);
            int maxOpsega1 = podaciRezultat.brojRezultata() - razlikaKlubova - 1;
            int maxOpsega2 = podaciRezultat.brojRezultata() + razlikaKlubova - 1;
            if(maxOpsega1 < 50) {
                maxOpsega1 = 50;
            }
            if(maxOpsega2 < 50) {
                maxOpsega2 = 50;
            }
            if(razlikaKlubova >= 0) {
                rezultat = podaciRezultat.uzmiRezultat(uzmiBrojIzOpsega(0, maxOpsega1));
                golDomacin = Integer.parseInt(String.valueOf(rezultat.charAt(0)));
                golGost = Integer.parseInt(String.valueOf(rezultat.charAt(2)));
                if(klub.matches(domacin)) {
                    vratiRezultatKluba = rezultat;
                    Utakmice utakmice = new Utakmice(0, gost, "domacin", golDomacin, golGost);
                    predMecViewModel.insertUtakmice(utakmice);
                }
                if(klub.matches(gost)) {
                    vratiRezultatKluba = rezultat.charAt(2) + "-" + rezultat.charAt(0);
                    Utakmice utakmice = new Utakmice(0, domacin, "gost", golGost, golDomacin);
                    predMecViewModel.insertUtakmice(utakmice);
                }
            } else {
                rezultat = podaciRezultat.uzmiRezultat(uzmiBrojIzOpsega(0, maxOpsega2));
                golGost = Integer.parseInt(String.valueOf(rezultat.charAt(0)));
                golDomacin = Integer.parseInt(String.valueOf(rezultat.charAt(2)));
                if(klub.matches(domacin)) {
                    vratiRezultatKluba = rezultat.charAt(2) + "-" + rezultat.charAt(0);
                    Utakmice utakmice = new Utakmice(0, gost, "domacin", golDomacin, golGost);
                    predMecViewModel.insertUtakmice(utakmice);
                }
                if(klub.matches(gost)) {
                    vratiRezultatKluba = rezultat;
                    Utakmice utakmice = new Utakmice(0, domacin, "gost", golGost, golDomacin);
                    predMecViewModel.insertUtakmice(utakmice);
                }
            }
            mec.setGolDomacin(golDomacin);
            mec.setGolGost(golGost);
            predMecViewModel.updateMec(mec);
            promeniPodatkeKluba(domacin, golDomacin, golGost);
            promeniPodatkeKluba(gost, golGost, golDomacin);
        }

        return  vratiRezultatKluba;
    }

    public void promeniPodatkeKluba(String imeKluba, int datiGolovi, int primljeniGolovi) {
        Klub klub = predMecViewModel.uzmiKlub(imeKluba);
        if(datiGolovi > primljeniGolovi) {

            klub.setPobede(klub.getPobede() + 1);
            klub.setDatiGolovi(klub.getDatiGolovi() + datiGolovi);
            klub.setPrimljeniGolovi(klub.getPrimljeniGolovi() + primljeniGolovi);
            klub.setGolRazlika(klub.getGolRazlika() + datiGolovi - primljeniGolovi);
            klub.setBodovi(klub.getBodovi() + 3);
        } else {
            if (datiGolovi < primljeniGolovi) {

                klub.setPorazi(klub.getPorazi() + 1);
                klub.setDatiGolovi(klub.getDatiGolovi() + datiGolovi);
                klub.setPrimljeniGolovi(klub.getPrimljeniGolovi() + primljeniGolovi);
                klub.setGolRazlika(klub.getGolRazlika() + datiGolovi - primljeniGolovi);
            } else {
                klub.setNeresene(klub.getNeresene() + 1);
                klub.setDatiGolovi(klub.getDatiGolovi() + datiGolovi);
                klub.setPrimljeniGolovi(klub.getPrimljeniGolovi() + primljeniGolovi);
                klub.setGolRazlika(klub.getGolRazlika() + datiGolovi - primljeniGolovi);
                klub.setBodovi(klub.getBodovi() + 1);
            }
        }

        predMecViewModel.updateKlub(klub);
    }


    private void promeniPozicijeIgracima(View v, int brojUListiSada) {
        if(prethodnoKliknutiIgrac.isStanje()) {
            TextView prethodniIgrac = prethodnoKliknutiIgrac.getIgrac();
            TextView sadasnjiIgrac = findViewById(v.getId());
            String sacuvatiPodatke = prethodniIgrac.getText().toString();
            int brojUListiPreth = prethodnoKliknutiIgrac.getBrojUListi();
            Igrac preIgrac = igracList.get(brojUListiPreth);
            Igrac sadIgrac = igracList.get(brojUListiSada);
            igracList.set(brojUListiPreth, sadIgrac);
            igracList.set(brojUListiSada, preIgrac);
            prethodniIgrac.setText(sadasnjiIgrac.getText());
            sadasnjiIgrac.setText(sacuvatiPodatke);
            prethodniIgrac.setTextSize(14);
            prethodnoKliknutiIgrac.setStanje(false);
            prethodnoKliknutiIgrac.setIgrac(null);
            tv_rejtingPostave.setText(
                    new DecimalFormat("0.0").format(proveriIgrace(podaciTaktika.uzmiTaktiku(taktikaBroj), igracList)));
            tv_ime.setVisibility(View.INVISIBLE);
            tv_pozicija.setVisibility(View.INVISIBLE);
            tv_rejting.setVisibility(View.INVISIBLE);
            tv_godine.setVisibility(View.INVISIBLE);
            pb_motivacija.setVisibility(View.INVISIBLE);
            pb_fitnes.setVisibility(View.INVISIBLE);
            tv_imeText.setVisibility(View.INVISIBLE);
            tv_pozicijaText.setVisibility(View.INVISIBLE);
            tv_rejtingText.setVisibility(View.INVISIBLE);
            tv_godineText.setVisibility(View.INVISIBLE);
            pb_motivacijaText.setVisibility(View.INVISIBLE);
            pb_fitnesText.setVisibility(View.INVISIBLE);
        } else {
            TextView sadasnjiIgrac = findViewById(v.getId());
            sadasnjiIgrac.setTextSize(16);
            prethodnoKliknutiIgrac.setStanje(true);
            prethodnoKliknutiIgrac.setIgrac(sadasnjiIgrac);
            prethodnoKliknutiIgrac.setBrojUListi(brojUListiSada);
            tv_ime.setText(igracList.get(brojUListiSada).getIme());
            tv_pozicija.setText(igracList.get(brojUListiSada).getPozicija());
            tv_rejting.setText(new DecimalFormat("0.0").format(igracList.get(brojUListiSada).getRejting()));
            tv_godine.setText(String.valueOf(igracList.get(brojUListiSada).getGodine()));

            int motivacija = igracList.get(brojUListiSada).getMotivacija();
            if(motivacija > 66) {
                pb_motivacija.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
            } else {
                if(motivacija > 33) {
                    pb_motivacija.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
                } else {
                    pb_motivacija.setProgressTintList(ColorStateList.valueOf(Color.RED));
                }
            }
            pb_motivacija.setProgress(motivacija);

            int fitnes = igracList.get(brojUListiSada).getSpremnost();
            if(fitnes > 66) {
                pb_fitnes.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
            } else {
                if(fitnes > 33) {
                    pb_fitnes.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
                } else {
                    pb_fitnes.setProgressTintList(ColorStateList.valueOf(Color.RED));
                }
            }
            pb_fitnes.setProgress(fitnes);

            tv_ime.setVisibility(View.VISIBLE);
            tv_pozicija.setVisibility(View.VISIBLE);
            tv_rejting.setVisibility(View.VISIBLE);
            tv_godine.setVisibility(View.VISIBLE);
            pb_motivacija.setVisibility(View.VISIBLE);
            pb_fitnes.setVisibility(View.VISIBLE);
            tv_imeText.setVisibility(View.VISIBLE);
            tv_pozicijaText.setVisibility(View.VISIBLE);
            tv_rejtingText.setVisibility(View.VISIBLE);
            tv_godineText.setVisibility(View.VISIBLE);
            pb_motivacijaText.setVisibility(View.VISIBLE);
            pb_fitnesText.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("CommitPrefEdits")
    private void init() {

        sharedPreferences = getSharedPreferences(Konstante.SHARED_PREFERENCES_PREFIX,0);
        editor = sharedPreferences.edit();
        editor.putString(Konstante.SHARED_PREFERENCES_KEY_AKTIVNOST, "PredMecActivity");
        editor.apply();
        taktikaBroj = sharedPreferences.getInt(Konstante.SHARED_PREFERENCES_KEY_TAKTIKA, 3);
        klub = sharedPreferences.getString(Konstante.SHARED_PREFERENCES_KEY_KLUB, "");
        dres = sharedPreferences.getInt(Konstante.SHARED_PREFERENCES_KEY_DRES, R.color.crvena_dres);
        boja = getColor(dres);

        drawable = ContextCompat.getDrawable(this, dres);

        podaciTaktika = new PodaciTaktika();

        belaBoja = Color.WHITE;
        crnaBoja = getColor(R.color.siva);

        predMecViewModel = new ViewModelProvider(this).get(PredMecViewModel.class);

        prethodnoKliknutiIgrac = new PrethodnoKliknutiIgrac(false, null, 0);

        btn_odigrajMec = findViewById(R.id.btn_odigrajMec);
        btn_prethodnaTaktika = findViewById(R.id.btn_prethodnaTaktika);
        btn_sledecaTaktika = findViewById(R.id.btn_sledecaTaktika);
        tv_trenutnaTaktika = findViewById(R.id.tv_trenutnaTaktika);
        tv_rejtingPostave = findViewById(R.id.tv_rejtingPostave);

        sviIgraci = new IgracTextDres(new ArrayList<>(), new ArrayList<>());
        ll_gk = findViewById(R.id.ll_gk);
        ll_dr = findViewById(R.id.ll_dr);
        ll_drc = findViewById(R.id.ll_drc);
        ll_dc = findViewById(R.id.ll_dc);
        ll_dlc = findViewById(R.id.ll_dlc);
        ll_dl = findViewById(R.id.ll_dl);
        ll_mr = findViewById(R.id.ll_mr);
        ll_mrc = findViewById(R.id.ll_mrc);
        ll_mc = findViewById(R.id.ll_mc);
        ll_mlc = findViewById(R.id.ll_mlc);
        ll_ml = findViewById(R.id.ll_ml);
        ll_frc = findViewById(R.id.ll_frc);
        ll_fc = findViewById(R.id.ll_fc);
        ll_flc = findViewById(R.id.ll_flc);
        tv_igrac1 = findViewById(R.id.tv_igrac1);
        ifv_igrac1 = findViewById(R.id.ifv_igrac1);
        tv_igrac1 = findViewById(R.id.tv_igrac1);
        ifv_igrac1 = findViewById(R.id.ifv_igrac1);
        sviIgraci.add(tv_igrac1, ifv_igrac1, boja);
        tv_igrac2 = findViewById(R.id.tv_igrac2);
        ifv_igrac2 = findViewById(R.id.ifv_igrac2);
        sviIgraci.add(tv_igrac2, ifv_igrac2, boja);
        tv_igrac3 = findViewById(R.id.tv_igrac3);
        ifv_igrac3 = findViewById(R.id.ifv_igrac3);
        sviIgraci.add(tv_igrac3, ifv_igrac3, boja);
        tv_igrac4 = findViewById(R.id.tv_igrac4);
        ifv_igrac4 = findViewById(R.id.ifv_igrac4);
        sviIgraci.add(tv_igrac4, ifv_igrac4, boja);
        tv_igrac5 = findViewById(R.id.tv_igrac5);
        ifv_igrac5 = findViewById(R.id.ifv_igrac5);
        sviIgraci.add(tv_igrac5, ifv_igrac5, boja);
        tv_igrac6 = findViewById(R.id.tv_igrac6);
        ifv_igrac6 = findViewById(R.id.ifv_igrac6);
        sviIgraci.add(tv_igrac6, ifv_igrac6, boja);
        tv_igrac7 = findViewById(R.id.tv_igrac7);
        ifv_igrac7 = findViewById(R.id.ifv_igrac7);
        sviIgraci.add(tv_igrac7, ifv_igrac7, boja);
        tv_igrac8 = findViewById(R.id.tv_igrac8);
        ifv_igrac8 = findViewById(R.id.ifv_igrac8);
        sviIgraci.add(tv_igrac8, ifv_igrac8, boja);
        tv_igrac9 = findViewById(R.id.tv_igrac9);
        ifv_igrac9 = findViewById(R.id.ifv_igrac9);
        sviIgraci.add(tv_igrac9, ifv_igrac9, boja);
        tv_igrac10 = findViewById(R.id.tv_igrac10);
        ifv_igrac10 = findViewById(R.id.ifv_igrac10);
        sviIgraci.add(tv_igrac10, ifv_igrac10, boja);
        tv_igrac11 = findViewById(R.id.tv_igrac11);
        ifv_igrac11 = findViewById(R.id.ifv_igrac11);
        sviIgraci.add(tv_igrac11, ifv_igrac11, boja);
        tv_igracRez1 = findViewById(R.id.tv_igracRez1);
        ifv_igracRez1 = findViewById(R.id.ifv_igracRez1);
        sviIgraci.add(tv_igracRez1, ifv_igracRez1, boja);
        tv_igracRez2 = findViewById(R.id.tv_igracRez2);
        ifv_igracRez2 = findViewById(R.id.ifv_igracRez2);
        sviIgraci.add(tv_igracRez2, ifv_igracRez2, boja);
        tv_igracRez3 = findViewById(R.id.tv_igracRez3);
        ifv_igracRez3 = findViewById(R.id.ifv_igracRez3);
        sviIgraci.add(tv_igracRez3, ifv_igracRez3, boja);
        tv_igracRez4 = findViewById(R.id.tv_igracRez4);
        ifv_igracRez4 = findViewById(R.id.ifv_igracRez4);
        sviIgraci.add(tv_igracRez4, ifv_igracRez4, boja);
        tv_igracRez5 = findViewById(R.id.tv_igracRez5);
        ifv_igracRez5 = findViewById(R.id.ifv_igracRez5);
        sviIgraci.add(tv_igracRez5, ifv_igracRez5, boja);
        tv_igracRez6 = findViewById(R.id.tv_igracRez6);
        ifv_igracRez6 = findViewById(R.id.ifv_igracRez6);
        sviIgraci.add(tv_igracRez6, ifv_igracRez6, boja);
        tv_igracRez7 = findViewById(R.id.tv_igracRez7);
        ifv_igracRez7 = findViewById(R.id.ifv_igracRez7);
        sviIgraci.add(tv_igracRez7, ifv_igracRez7, boja);

        tv_ime = findViewById(R.id.tv_ime);
        tv_pozicija = findViewById(R.id.tv_pozicija);
        tv_rejting = findViewById(R.id.tv_rejting);
        tv_godine = findViewById(R.id.tv_godine);
        pb_motivacija = findViewById(R.id.pb_motivacija);
        pb_fitnes = findViewById(R.id.pb_fitnes);
        tv_imeText = findViewById(R.id.tv_imeText);
        tv_pozicijaText = findViewById(R.id.tv_pozicijaText);
        tv_rejtingText = findViewById(R.id.tv_rejtingText);
        tv_godineText = findViewById(R.id.tv_godineText);
        pb_motivacijaText = findViewById(R.id.pb_motivacijaText);
        pb_fitnesText = findViewById(R.id.pb_fitnesText);

        btn_odigrajMec.setOnClickListener(this);
        btn_sledecaTaktika.setOnClickListener(this);
        btn_prethodnaTaktika.setOnClickListener(this);
        tv_igrac1.setOnClickListener(this);
        tv_igrac2.setOnClickListener(this);
        tv_igrac3.setOnClickListener(this);
        tv_igrac4.setOnClickListener(this);
        tv_igrac5.setOnClickListener(this);
        tv_igrac6.setOnClickListener(this);
        tv_igrac7.setOnClickListener(this);
        tv_igrac8.setOnClickListener(this);
        tv_igrac9.setOnClickListener(this);
        tv_igrac10.setOnClickListener(this);
        tv_igrac11.setOnClickListener(this);
        tv_igracRez1.setOnClickListener(this);
        tv_igracRez2.setOnClickListener(this);
        tv_igracRez3.setOnClickListener(this);
        tv_igracRez4.setOnClickListener(this);
        tv_igracRez5.setOnClickListener(this);
        tv_igracRez6.setOnClickListener(this);
        tv_igracRez7.setOnClickListener(this);

    }

    private void zameniRejtingKluba(double rejting) {

        new Thread(() -> {

            Klub klubTemp = predMecViewModel.uzmiKlub(klub);
            klubTemp.setRejting(rejting);
            predMecViewModel.updateKlub(klubTemp);

        }).start();

    }

    private void ucitajListuIgraca() { new Thread(() -> igracList = predMecViewModel.uzmiSveIgracePoBroju()).start(); }

    private double proveriIgrace(Taktika taktika, List<Igrac> igracList) {
        int i = 0;
        double ukupanRejting = 0.0;
        if (taktika.isGk()) {
            if (igracList.get(i).getPozicija().matches("GK")) {
                ukupanRejting = ukupanRejting + igracList.get(i).getRejting() - (100.0 - igracList.get(i).getMotivacija()) / 50.0;
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isDr()) {
            if (igracList.get(i).getPozicija().matches("DR") ||
                    igracList.get(i).getPozicija().matches("DRC") ||
                    igracList.get(i).getPozicija().matches("DRL")) {
                ukupanRejting = ukupanRejting + igracList.get(i).getRejting() - (100.0 - igracList.get(i).getMotivacija()) / 50.0;
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isDrc()) {
            if (igracList.get(i).getPozicija().matches("DRC") ||
                    igracList.get(i).getPozicija().matches("DC")) {
                ukupanRejting = ukupanRejting + igracList.get(i).getRejting() - (100.0 - igracList.get(i).getMotivacija()) / 50.0;
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isDc()) {
            if (igracList.get(i).getPozicija().matches("DRC") ||
                    igracList.get(i).getPozicija().matches("DC") ||
                    igracList.get(i).getPozicija().matches("DLC")) {
                ukupanRejting = ukupanRejting + igracList.get(i).getRejting() - (100.0 - igracList.get(i).getMotivacija()) / 50.0;
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isDlc()) {
            if (igracList.get(i).getPozicija().matches("DC") ||
                    igracList.get(i).getPozicija().matches("DLC")) {
                ukupanRejting = ukupanRejting + igracList.get(i).getRejting() - (100.0 - igracList.get(i).getMotivacija()) / 50.0;
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isDl()) {
            if (igracList.get(i).getPozicija().matches("DLC") ||
                    igracList.get(i).getPozicija().matches("DL") ||
                    igracList.get(i).getPozicija().matches("DRL")) {
                ukupanRejting = ukupanRejting + igracList.get(i).getRejting() - (100.0 - igracList.get(i).getMotivacija()) / 50.0;
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isMr()) {
            if (igracList.get(i).getPozicija().matches("MR") ||
                    igracList.get(i).getPozicija().matches("MRC") ||
                    igracList.get(i).getPozicija().matches("MRL")) {
                ukupanRejting = ukupanRejting + igracList.get(i).getRejting() - (100.0 - igracList.get(i).getMotivacija()) / 50.0;
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isMrc()) {
            if (igracList.get(i).getPozicija().matches("MRC") ||
                    igracList.get(i).getPozicija().matches("MC")) {
                ukupanRejting = ukupanRejting + igracList.get(i).getRejting() - (100.0 - igracList.get(i).getMotivacija()) / 50.0;
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isMc()) {
            if (igracList.get(i).getPozicija().matches("MRC") ||
                    igracList.get(i).getPozicija().matches("MC") ||
                    igracList.get(i).getPozicija().matches("MLC")) {
                ukupanRejting = ukupanRejting + igracList.get(i).getRejting() - (100.0 - igracList.get(i).getMotivacija()) / 50.0;
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isMlc()) {
            if (igracList.get(i).getPozicija().matches("MC") ||
                    igracList.get(i).getPozicija().matches("MLC")) {
                ukupanRejting = ukupanRejting + igracList.get(i).getRejting() - (100.0 - igracList.get(i).getMotivacija()) / 50.0;
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isMl()) {
            if (igracList.get(i).getPozicija().matches("ML") ||
                    igracList.get(i).getPozicija().matches("MLC") ||
                    igracList.get(i).getPozicija().matches("MRL")) {
                ukupanRejting = ukupanRejting + igracList.get(i).getRejting() - (100.0 - igracList.get(i).getMotivacija()) / 50.0;
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isFrc()) {
            if (igracList.get(i).getPozicija().matches("FR") ||
                    igracList.get(i).getPozicija().matches("FRC") ||
                    igracList.get(i).getPozicija().matches("FRL")) {
                ukupanRejting = ukupanRejting + igracList.get(i).getRejting() - (100.0 - igracList.get(i).getMotivacija()) / 50.0;
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isFc()) {
            if (igracList.get(i).getPozicija().matches("FRC") ||
                    igracList.get(i).getPozicija().matches("FC") ||
                    igracList.get(i).getPozicija().matches("FLC")) {
                ukupanRejting = ukupanRejting + igracList.get(i).getRejting() - (100.0 - igracList.get(i).getMotivacija()) / 50.0;
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isFlc()) {
            if (igracList.get(i).getPozicija().matches("FLC") ||
                    igracList.get(i).getPozicija().matches("FL") ||
                    igracList.get(i).getPozicija().matches("FRL")) {
                ukupanRejting = ukupanRejting + igracList.get(i).getRejting() - (100.0 - igracList.get(i).getMotivacija()) / 50.0;
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
        }
        ukupanRejting = ukupanRejting / 11.0;
        return ukupanRejting;
    }

    private void promeniIzgledFormacije(Taktika taktika) {
        ll_gk.removeAllViews();
        ll_dr.removeAllViews();
        ll_drc.removeAllViews();
        ll_dc.removeAllViews();
        ll_dlc.removeAllViews();
        ll_dl.removeAllViews();
        ll_mr.removeAllViews();
        ll_mrc.removeAllViews();
        ll_mc.removeAllViews();
        ll_mlc.removeAllViews();
        ll_ml.removeAllViews();
        ll_frc.removeAllViews();
        ll_fc.removeAllViews();
        ll_flc.removeAllViews();

        int i = 0;
        if(taktika.isGk()) {
            dodajView(ll_gk, sviIgraci.getInformacije().get(i), sviIgraci.getDres().get(i));
            i++;
        }
        if(taktika.isDr()) {
            dodajView(ll_dr, sviIgraci.getInformacije().get(i), sviIgraci.getDres().get(i));
            i++;
        }
        if(taktika.isDrc()) {
            dodajView(ll_drc, sviIgraci.getInformacije().get(i), sviIgraci.getDres().get(i));
            i++;
        }
        if(taktika.isDc()) {
            dodajView(ll_dc, sviIgraci.getInformacije().get(i), sviIgraci.getDres().get(i));
            i++;
        }
        if(taktika.isDlc()) {
            dodajView(ll_dlc, sviIgraci.getInformacije().get(i), sviIgraci.getDres().get(i));
            i++;
        }
        if(taktika.isDl()) {
            dodajView(ll_dl, sviIgraci.getInformacije().get(i), sviIgraci.getDres().get(i));
            i++;
        }
        if(taktika.isMr()) {
            dodajView(ll_mr, sviIgraci.getInformacije().get(i), sviIgraci.getDres().get(i));
            i++;
        }
        if(taktika.isMrc()) {
            dodajView(ll_mrc, sviIgraci.getInformacije().get(i), sviIgraci.getDres().get(i));
            i++;
        }
        if(taktika.isMc()) {
            dodajView(ll_mc, sviIgraci.getInformacije().get(i), sviIgraci.getDres().get(i));
            i++;
        }
        if(taktika.isMlc()) {
            dodajView(ll_mlc, sviIgraci.getInformacije().get(i), sviIgraci.getDres().get(i));
            i++;
        }
        if(taktika.isMl()) {
            dodajView(ll_ml, sviIgraci.getInformacije().get(i), sviIgraci.getDres().get(i));
            i++;
        }
        if(taktika.isFrc()) {
            dodajView(ll_frc, sviIgraci.getInformacije().get(i), sviIgraci.getDres().get(i));
            i++;
        }
        if(taktika.isFc()) {
            dodajView(ll_fc, sviIgraci.getInformacije().get(i), sviIgraci.getDres().get(i));
            i++;
        }
        if(taktika.isFlc()) {
            dodajView(ll_flc, sviIgraci.getInformacije().get(i), sviIgraci.getDres().get(i));
        }

    }

    private void dodajView(ConstraintLayout parentLayout, TextView textView, ImageFilterView imageFilterView) {

        ConstraintSet set = new ConstraintSet();

        parentLayout.addView(imageFilterView, 0);

        set.clone(parentLayout);

        set.connect(imageFilterView.getId(), ConstraintSet.TOP, parentLayout.getId(), ConstraintSet.TOP, 0);

        parentLayout.addView(textView, 1);

        set.clone(parentLayout);

        set.connect(textView.getId(), ConstraintSet.TOP, parentLayout.getId(), ConstraintSet.TOP, 4);

        set.applyTo(parentLayout);
    }

    private static int uzmiBrojIzOpsega(int min, int max) {

        return (int)(Math.random() * ((max - min) + 1)) + min;
    }
}