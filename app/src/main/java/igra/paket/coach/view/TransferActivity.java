package igra.paket.coach.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import igra.paket.coach.R;
import igra.paket.coach.database.entity.Igrac;
import igra.paket.coach.database.podaci.PodaciTaktika;
import igra.paket.coach.database.viewmodel.TransferViewModel;
import igra.paket.coach.pomocne.IgracTextDres;
import igra.paket.coach.pomocne.Konstante;
import igra.paket.coach.pomocne.Taktika;

public class TransferActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences.Editor editor;
    int taktikaBroj, dres, boja, belaBoja, crnaBoja;

    ConstraintLayout ll_gk, ll_dr, ll_drc, ll_dc, ll_dlc, ll_dl, ll_mr, ll_mrc, ll_mc, ll_mlc, ll_ml, ll_frc, ll_fc, ll_flc;

    TextView tv_igrac1, tv_igrac2, tv_igrac3, tv_igrac4, tv_igrac5, tv_igrac6, tv_igrac7, tv_igrac8, tv_igrac9, tv_igrac10, tv_igrac11, tv_igracRez1,
            tv_igracRez2, tv_igracRez3, tv_igracRez4, tv_igracRez5, tv_igracRez6, tv_igracRez7;
    ImageFilterView ifv_igrac1, ifv_igrac2, ifv_igrac3,ifv_igrac4, ifv_igrac5, ifv_igrac6, ifv_igrac7, ifv_igrac8, ifv_igrac9, ifv_igrac10, ifv_igrac11,
            ifv_igracRez1, ifv_igracRez2, ifv_igracRez3, ifv_igracRez4, ifv_igracRez5, ifv_igracRez6, ifv_igracRez7;
    IgracTextDres sviIgraci;
    List<Igrac> igracList;
    PodaciTaktika podaciTaktika;
    TransferViewModel transferViewModel;

    Button btn_pozicijaNovogIgraca;

    String pozicija;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        init();

        ucitajListuIgraca();

        transferViewModel.uzmiSveIgracePoBrojuObserver().observe(this, igraci -> {

            for(int i=0; i<igraci.size(); i++) {
                String text = new DecimalFormat("0.0").format(igraci.get(i).getRejting())
                        + "\n" + igraci.get(i).getPozicija() + "\n" + igraci.get(i).getGodine();
                sviIgraci.getInformacije().get(i).setText(text);
            }

            Taktika taktika = podaciTaktika.uzmiTaktiku(taktikaBroj);

            promeniIzgledFormacije(taktika);
            proveriIgrace(taktika, igraci);

        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

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
        btn_pozicijaNovogIgraca.setClickable(false);

        new Handler().postDelayed(() -> {

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
            btn_pozicijaNovogIgraca.setClickable(true);
        }, 500);

        Intent intent = new Intent(TransferActivity.this, ZamenaIgracaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("pozicija", pozicija);

        switch (v.getId()) {
            case R.id.btn_pozicijaNovogIgraca:
                PopupMenu popup = new PopupMenu(TransferActivity.this, v);
                popup.getMenuInflater().inflate(R.menu.izaberi_poziciju, popup.getMenu());

                popup.setOnMenuItemClickListener(item -> {
                    pozicija = item.toString();
                    btn_pozicijaNovogIgraca.setText(pozicija);
                    return true;
                });

                popup.show();
                break;

            case R.id.tv_igrac1:
                izborIgraca(bundle, intent, igracList.get(0).getId());
                break;
            case R.id.tv_igrac2:
                izborIgraca(bundle, intent, igracList.get(1).getId());
                break;
            case R.id.tv_igrac3:
                izborIgraca(bundle, intent, igracList.get(2).getId());
                break;
            case R.id.tv_igrac4:
                izborIgraca(bundle, intent, igracList.get(3).getId());
                break;
            case R.id.tv_igrac5:
                izborIgraca(bundle, intent, igracList.get(4).getId());
                break;
            case R.id.tv_igrac6:
                izborIgraca(bundle, intent, igracList.get(5).getId());
                break;
            case R.id.tv_igrac7:
                izborIgraca(bundle, intent, igracList.get(6).getId());
                break;
            case R.id.tv_igrac8:
                izborIgraca(bundle, intent, igracList.get(7).getId());
                break;
            case R.id.tv_igrac9:
                izborIgraca(bundle, intent, igracList.get(8).getId());
                break;
            case R.id.tv_igrac10:
                izborIgraca(bundle, intent, igracList.get(9).getId());
                break;
            case R.id.tv_igrac11:
                izborIgraca(bundle, intent, igracList.get(10).getId());
                break;
            case R.id.tv_igracRez1:
                izborIgraca(bundle, intent, igracList.get(11).getId());
                break;
            case R.id.tv_igracRez2:
                izborIgraca(bundle, intent, igracList.get(12).getId());
                break;
            case R.id.tv_igracRez3:
                izborIgraca(bundle, intent, igracList.get(13).getId());
                break;
            case R.id.tv_igracRez4:
                izborIgraca(bundle, intent, igracList.get(14).getId());
                break;
            case R.id.tv_igracRez5:
                izborIgraca(bundle, intent, igracList.get(15).getId());
                break;
            case R.id.tv_igracRez6:
                izborIgraca(bundle, intent, igracList.get(16).getId());
                break;
            case R.id.tv_igracRez7:
                izborIgraca(bundle, intent, igracList.get(17).getId());
                break;
            default: break;
        }

    }

    private void izborIgraca(Bundle bundle, Intent intent, int igracId) {

        bundle.putInt("igracID", igracId);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();

    }

    private void ucitajListuIgraca() { new Thread(() -> igracList = transferViewModel.uzmiSveIgracePoBroju()).start(); }

    @SuppressLint("CommitPrefEdits")
    private void init() {

        sharedPreferences = getSharedPreferences(Konstante.SHARED_PREFERENCES_PREFIX,0);
        editor = sharedPreferences.edit();
        editor.putString(Konstante.SHARED_PREFERENCES_KEY_AKTIVNOST, "TransferActivity");
        editor.apply();
        taktikaBroj = sharedPreferences.getInt(Konstante.SHARED_PREFERENCES_KEY_TAKTIKA, 3);
        dres = sharedPreferences.getInt(Konstante.SHARED_PREFERENCES_KEY_DRES, R.color.crvena_dres);
        boja = getColor(dres);

        podaciTaktika = new PodaciTaktika();

        transferViewModel = new ViewModelProvider(this).get(TransferViewModel.class);

        btn_pozicijaNovogIgraca = findViewById(R.id.btn_pozicijaNovogIgraca);

        pozicija = "";

        belaBoja = Color.WHITE;
        crnaBoja = getColor(R.color.siva);

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
        btn_pozicijaNovogIgraca.setOnClickListener(this);

    }

    private void proveriIgrace(Taktika taktika, List<Igrac> igracList) {
        int i = 0;
        if (taktika.isGk()) {
            if (igracList.get(i).getPozicija().matches("GK")) {
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
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isDrc()) {
            if (igracList.get(i).getPozicija().matches("DRC") ||
                    igracList.get(i).getPozicija().matches("DC")) {
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
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isDlc()) {
            if (igracList.get(i).getPozicija().matches("DC") ||
                    igracList.get(i).getPozicija().matches("DLC")) {
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
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isMrc()) {
            if (igracList.get(i).getPozicija().matches("MRC") ||
                    igracList.get(i).getPozicija().matches("MC")) {
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
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
            i++;
        }
        if (taktika.isMlc()) {
            if (igracList.get(i).getPozicija().matches("MC") ||
                    igracList.get(i).getPozicija().matches("MLC")) {
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
                sviIgraci.ubaciBojuTeksta(i, belaBoja);
            } else {
                sviIgraci.ubaciBojuTeksta(i, crnaBoja);
            }
        }
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
        // connect start and end point of views, in this case top of child to top of parent.
        set.connect(imageFilterView.getId(), ConstraintSet.TOP, parentLayout.getId(), ConstraintSet.TOP, 0);
        // ... similarly add other constraints
        parentLayout.addView(textView, 1);

        set.clone(parentLayout);

        set.connect(textView.getId(), ConstraintSet.TOP, parentLayout.getId(), ConstraintSet.TOP, 4);

        set.applyTo(parentLayout);
    }
}