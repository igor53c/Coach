package igra.ipcoding.coach.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import igra.ipcoding.coach.R;
import igra.ipcoding.coach.database.entity.Igrac;
import igra.ipcoding.coach.database.entity.Istorija;
import igra.ipcoding.coach.database.entity.Klub;
import igra.ipcoding.coach.database.entity.Mec;
import igra.ipcoding.coach.database.entity.Utakmice;
import igra.ipcoding.coach.database.viewmodel.MainViewModel;
import igra.ipcoding.coach.pomocne.Konstante;

public class PocetnaFragment extends Fragment implements View.OnClickListener {

    Button btn_ne, btn_da;
    FloatingActionButton btn_dalje;
    ImageView iv_utakmica1, iv_utakmica2, iv_utakmica3, iv_utakmica4, iv_utakmica5,
            iv_utakmica6, iv_utakmica7, iv_utakmica8, iv_utakmica9, iv_utakmica10;
    TextView tv_datum, tv_sedmica, tv_liga, tv_imeKluba, tv_godina;
    int numberWeek, godina, kolo, dres;
    boolean rejtingUcitan;
    String[] liga;
    String klub;

    ImageFilterView ifv_grb;

    List<Utakmice> utakmiceList;

    List<ImageView> imageViewList;

    SharedPreferences sharedPreferences;

    MainViewModel mainViewModel;

    SharedPreferences.Editor editor;

    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    public PocetnaFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pocetna, container, false);

        init(v);

        ucitajListuUtakmica();

        mainViewModel.uzmiPoslednjihDesetUtakmicaObserver().observe(requireActivity(), utakmice -> {

            if(!(utakmice == null || utakmice.size() == 0)) {

                int j = 0;
                for (int i = utakmice.size() - 1; i > -1; i--) {
                    if(utakmice.get(i).getDatiGolovi() - utakmice.get(i).getPrimljeniGolovi() > 0) {
                        imageViewList.get(j).setImageResource(R.drawable.slovo_w);
                    } else {
                        if(utakmice.get(i).getDatiGolovi() - utakmice.get(i).getPrimljeniGolovi() == 0) {
                            imageViewList.get(j).setImageResource(R.drawable.slovo_d);
                        } else {
                            imageViewList.get(j).setImageResource(R.drawable.slovo_l);
                        }
                    }
                    imageViewList.get(j).setVisibility(View.VISIBLE);
                    j++;
                }
            }
        });

        upisiPodatkeNaStranici();

        return v;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        btn_dalje.setClickable(false);
        tv_imeKluba.setClickable(false);

        new Handler().postDelayed(() -> {

            btn_dalje.setClickable(true);
            tv_imeKluba.setClickable(true);

        }, 500);


        AtomicReference<Intent> intent = new AtomicReference<>();

        switch (v.getId()) {

            case R.id.btn_da:
                alertDialog.dismiss();
                editor.putString(Konstante.SHARED_PREFERENCES_KEY_KLUB, "");
                editor.apply();
                intent.set(new Intent(getActivity(), BiranjeKlubaActivity.class));
                startActivity(intent.get());
                requireActivity().finish();
                break;

            case R.id.btn_ne:
                alertDialog.dismiss();
                break;

            case R.id.tv_imeKluba:
                otvoriPopup();
                break;

            case R.id.tv_godina:
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout, new IstorijaFragment())
                        .commit();
                break;

            case R.id.btn_dalje:

                numberWeek++;

                if (numberWeek == 53) {
                    numberWeek = 1;
                    godina++;
                }

                if(numberWeek == 23) {

                    promeniIstoriju();

                    promeniIgracimaGodine();

                }

                if (numberWeek == 30) {
                    if (kolo == 38) {

                        pripremaZaNovuSezonu(klub, liga[0]);

                    }
                }

                if (numberWeek == 31) {

                    pripremaKlubovaIPravljenjeRasporeda();

                    kolo = 1;
                }

                boolean imaMec = false;

                if((numberWeek >= 34 && numberWeek < 53) || (numberWeek >= 4 && numberWeek < 23)) {

                    imaMec = true;

                }

                racunanjeTreninga(imaMec);

                editor.putInt(Konstante.SHARED_PREFERENCES_KEY_SEDMICA, numberWeek);
                editor.putInt(Konstante.SHARED_PREFERENCES_KEY_GODINA, godina);
                editor.putInt(Konstante.SHARED_PREFERENCES_KEY_KOLO, kolo);
                editor.apply();

                intent.set(new Intent(getActivity(), ObjasnjenjeActivity.class));
                startActivity(intent.get());
                requireActivity().finish();

                break;

            default: break;
        }
    }

    private void otvoriPopup() {

        builder = new AlertDialog.Builder(requireActivity());
        final View view = getLayoutInflater().inflate(R.layout.popup_main, null);
        btn_da = view.findViewById(R.id.btn_da);
        btn_ne = view.findViewById(R.id.btn_ne);

        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        btn_da.setOnClickListener(this);
        btn_ne.setOnClickListener(this);

    }

    private void ucitajListuUtakmica() {
        new Thread(() -> utakmiceList = mainViewModel.uzmiPoslednjihDesetUtakmica()).start();
    }

    private void pripremaKlubovaIPravljenjeRasporeda() {

        new Thread(() -> {

            liga[0] = sharedPreferences.getString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "");
            double rejting = 95.0;
            for (int i = 1; i < 8; i++) {
                String trenutnaLiga = "League " + i;
                if (!liga[0].matches(trenutnaLiga)) {

                    List<Klub> klubList = mainViewModel.uzmiKluboveIzLigePoRejtingu(trenutnaLiga);

                    int pozicija = 0;

                    for (int j = 0; j < 20; j++) {

                        Klub klub1 = klubList.get(j);

                        pozicija++;

                        klub1.setPozicija(pozicija);
                        klub1.setRejting(rejting);
                        mainViewModel.updateKlub(klub1);

                        rejting = rejting - 0.5;

                    }
                } else {

                    rejting = rejting - 10.0;

                }
            }
            mainViewModel.obrisiSveMeceve();
            List<Klub> klubList = mainViewModel.uzmiKluboveIzJedneLige(liga[0]);
            for (int i = 0; i < klubList.size(); i++) {

                klubList.get(i).setPobede(0);
                klubList.get(i).setNeresene(0);
                klubList.get(i).setPorazi(0);
                klubList.get(i).setDatiGolovi(0);
                klubList.get(i).setPrimljeniGolovi(0);
                klubList.get(i).setGolRazlika(0);
                klubList.get(i).setBodovi(0);

                mainViewModel.updateKlub(klubList.get(i));

            }
            napraviRaspored(liga[0]);

        }).start();

    }

    private void racunanjeTreninga(boolean imaMec) {

        new Thread(() -> {

            List<Igrac> igracList = mainViewModel.uzmiSveIgrace();

            for (int i = 0; i < igracList.size(); i++) {

                if(!imaMec) {
                    igracList.get(i).setMotivacija(igracList.get(i).getMotivacija() + 20);
                    if(igracList.get(i).getMotivacija() > 100) {
                        igracList.get(i).setMotivacija(100);
                    }
                }

                igracList.get(i).setSpremnost(igracList.get(i).getSpremnost() + (4 - igracList.get(i).getTrening()) * 20);
                if(igracList.get(i).getSpremnost() > 100) {
                    igracList.get(i).setSpremnost(100);
                } else {
                    if (igracList.get(i).getSpremnost() < 0) {
                        igracList.get(i).setSpremnost(0);
                    }
                }

                igracList.get(i).setRejting(igracList.get(i).getRejting() +
                        (igracList.get(i).getTrening() * ((36 - igracList.get(i).getGodine()) / 800.0)) *
                                (igracList.get(i).getSpremnost() / 100.0) * (igracList.get(i).getMotivacija() / 100.0));
                if(igracList.get(i).getRejting() > 99) {
                    igracList.get(i).setRejting(99.0);
                } else {
                    if (igracList.get(i).getRejting() < 1) {
                        igracList.get(i).setRejting(1.0);
                    }
                }

                mainViewModel.updateIgrac(igracList.get(i));
            }

        }).start();
    }

    @SuppressLint("CommitPrefEdits")
    private void init(View v) {

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        btn_dalje = v.findViewById(R.id.btn_dalje);

        ifv_grb = v.findViewById(R.id.ifv_grb);

        sharedPreferences = requireActivity().getSharedPreferences(Konstante.SHARED_PREFERENCES_PREFIX,0);
        editor = sharedPreferences.edit();
        editor.putString(Konstante.SHARED_PREFERENCES_KEY_AKTIVNOST, "GlavniActivity");
        editor.apply();
        liga = new String[]{sharedPreferences.getString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "")};
        klub = sharedPreferences.getString(Konstante.SHARED_PREFERENCES_KEY_KLUB, "");
        numberWeek = sharedPreferences.getInt(Konstante.SHARED_PREFERENCES_KEY_SEDMICA, 0);
        godina = sharedPreferences.getInt(Konstante.SHARED_PREFERENCES_KEY_GODINA, 0);
        kolo = sharedPreferences.getInt(Konstante.SHARED_PREFERENCES_KEY_KOLO, 1);
        rejtingUcitan = sharedPreferences.getBoolean(Konstante.SHARED_PREFERENCES_KEY_REJTING_UCITAN, false);
        dres = sharedPreferences.getInt(Konstante.SHARED_PREFERENCES_KEY_DRES, R.color.crvena_dres);

        ifv_grb.setColorFilter(requireActivity().getColor(dres));

        tv_godina = v.findViewById(R.id.tv_godina);
        tv_godina.setText(String.valueOf(godina));
        tv_datum = v.findViewById(R.id.tv_datum);
        tv_sedmica = v.findViewById(R.id.tv_sedmica);
        tv_liga = v.findViewById(R.id.tv_liga);
        tv_liga.setText(liga[0]);
        tv_imeKluba = v.findViewById(R.id.tv_imeKluba);
        tv_imeKluba.setText(klub);

        imageViewList = new ArrayList<>();
        iv_utakmica1 = v.findViewById(R.id.iv_utakmica1);
        imageViewList.add(iv_utakmica1);
        iv_utakmica2 = v.findViewById(R.id.iv_utakmica2);
        imageViewList.add(iv_utakmica2);
        iv_utakmica3 = v.findViewById(R.id.iv_utakmica3);
        imageViewList.add(iv_utakmica3);
        iv_utakmica4 = v.findViewById(R.id.iv_utakmica4);
        imageViewList.add(iv_utakmica4);
        iv_utakmica5 = v.findViewById(R.id.iv_utakmica5);
        imageViewList.add(iv_utakmica5);
        iv_utakmica6 = v.findViewById(R.id.iv_utakmica6);
        imageViewList.add(iv_utakmica6);
        iv_utakmica7 = v.findViewById(R.id.iv_utakmica7);
        imageViewList.add(iv_utakmica7);
        iv_utakmica8 = v.findViewById(R.id.iv_utakmica8);
        imageViewList.add(iv_utakmica8);
        iv_utakmica9 = v.findViewById(R.id.iv_utakmica9);
        imageViewList.add(iv_utakmica9);
        iv_utakmica10 = v.findViewById(R.id.iv_utakmica10);
        imageViewList.add(iv_utakmica10);

        btn_dalje.setOnClickListener(this);
        tv_imeKluba.setOnClickListener(this);
        tv_godina.setOnClickListener(this);
    }

    private void promeniIgracimaGodine() {

        new Thread(() -> {

            List<Igrac> igracList = mainViewModel.uzmiSveIgrace();
            for(int i = 0; i < igracList.size(); i++) {
                int god = igracList.get(i).getGodine() + 1;
                igracList.get(i).setGodine(god);
                mainViewModel.updateIgrac(igracList.get(i));
            }

        }).start();

    }

    private void promeniIstoriju() {

        new Thread(() -> {

            Istorija istorija = new Istorija(0, godina, liga[0], mainViewModel.uzmiKlub(klub).getPozicija());

            mainViewModel.insertIstorija(istorija);

        }).start();

    }

    public void napraviRaspored(String liga) {

        List<Klub> tempKlubList = mainViewModel.uzmiKluboveIzJedneLige(liga);

        int numTeams = tempKlubList.size();

        int numDays = (numTeams - 1); // Days needed to complete tournament
        int halfSize = numTeams / 2;

        List<Klub> teams = new ArrayList<>(tempKlubList); // Add teams to List and remove the first team
        teams.remove(0);

        int teamsSize = teams.size();

        for (int day = 0; day < numDays; day++) {

            int kolo1;
            int kolo2;

            int teamIdx = day % teamsSize;

            if (day % 2 == 0) {
                kolo1 = day + 1;
                kolo2 = day + 1 + 19;
            } else {
                kolo2 = day + 1;
                kolo1 = day + 1 + 19;
            }

            mainViewModel.insertMec(new Mec(0, kolo1, teams.get(teamIdx).getIme(),
                    tempKlubList.get(0).getIme(), 0, 0));

            mainViewModel.insertMec(new Mec(0, kolo2, tempKlubList.get(0).getIme(),
                    teams.get(teamIdx).getIme(), 0, 0));

            for (int idx = 1; idx < halfSize; idx++) {
                int firstTeam = (day + idx) % teamsSize;
                int secondTeam = (day + teamsSize - idx) % teamsSize;

                mainViewModel.insertMec(new Mec(0, kolo1, teams.get(firstTeam).getIme(),
                        teams.get(secondTeam).getIme(), 0, 0));

                mainViewModel.insertMec(new Mec(0, kolo2, teams.get(secondTeam).getIme(),
                        teams.get(firstTeam).getIme(), 0, 0));

            }
        }
    }

    private void upisiPodatkeNaStranici() {

        tv_sedmica.setText(numberWeek + " week");

        if(numberWeek<5) {
            tv_datum.setText("January");
        }
        if(numberWeek>=5 && numberWeek<9) {
            tv_datum.setText("February");
        }
        if(numberWeek>=9 && numberWeek<14) {
            tv_datum.setText("March");
        }
        if(numberWeek>=14 && numberWeek<18) {
            tv_datum.setText("April");
        }
        if(numberWeek>=18 && numberWeek<23) {
            tv_datum.setText("May");
        }
        if(numberWeek>=24 && numberWeek<28) {
            tv_datum.setText("June");
        }
        if(numberWeek>=28 && numberWeek<32) {
            tv_datum.setText("July");
        }
        if(numberWeek>=32 && numberWeek<36) {
            tv_datum.setText("August");
        }
        if(numberWeek>=36 && numberWeek<40) {
            tv_datum.setText("September");
        }
        if(numberWeek>=40 && numberWeek<45) {
            tv_datum.setText("October");
        }
        if(numberWeek>=45 && numberWeek<49) {
            tv_datum.setText("November");
        }
        if(numberWeek>=49 && numberWeek<53) {
            tv_datum.setText("December");
        }

    }

    private void pripremaZaNovuSezonu(String klub, String liga) {

        new Thread(() -> {

            List<Klub> klubList = mainViewModel.uzmiKluboveIzJedneLige(liga);
            switch (liga) {
                case "League 1":
                    for(int i=0; i<20; i++) {
                        if(i > 15) {
                            List<Klub> nizaLiga = mainViewModel.uzmiKluboveIzLigePoPoziciji("League 2");
                            Klub trenutniKlub = klubList.get(i);
                            Klub buduciKlub = nizaLiga.get(i - 16);
                            if(klub.matches(klubList.get(i).getIme())) {
                                editor.putString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "League 2");
                                editor.apply();
                            }
                            trenutniKlub.setLiga("League 2");
                            trenutniKlub.setPozicija(i-15);
                            trenutniKlub.setPobede(0);
                            trenutniKlub.setNeresene(0);
                            trenutniKlub.setPorazi(0);
                            trenutniKlub.setDatiGolovi(0);
                            trenutniKlub.setPrimljeniGolovi(0);
                            trenutniKlub.setGolRazlika(0);
                            trenutniKlub.setBodovi(0);
                            mainViewModel.updateKlub(trenutniKlub);
                            buduciKlub.setLiga("League 1");
                            buduciKlub.setPozicija(i - 16 + 1);
                            mainViewModel.updateKlub(buduciKlub);
                        }
                    }
                    break;
                case "League 2":
                    for(int i=0; i<20; i++) {
                        if(i > 15) {
                            List<Klub> nizaLiga = mainViewModel.uzmiKluboveIzLigePoPoziciji("League 3");
                            Klub trenutniKlub = klubList.get(i);
                            Klub buduciKlub = nizaLiga.get(i - 16);
                            if(klub.matches(klubList.get(i).getIme())) {
                                editor.putString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "League 3");
                                editor.apply();
                            }
                            trenutniKlub.setLiga("League 3");
                            trenutniKlub.setPozicija(i-15);
                            trenutniKlub.setPobede(0);
                            trenutniKlub.setNeresene(0);
                            trenutniKlub.setPorazi(0);
                            trenutniKlub.setDatiGolovi(0);
                            trenutniKlub.setPrimljeniGolovi(0);
                            trenutniKlub.setGolRazlika(0);
                            trenutniKlub.setBodovi(0);
                            mainViewModel.updateKlub(trenutniKlub);
                            buduciKlub.setLiga("League 2");
                            buduciKlub.setPozicija(i - 16 + 1);
                            mainViewModel.updateKlub(buduciKlub);
                        }
                        if(i < 4) {
                            List<Klub> visaLiga = mainViewModel.uzmiKluboveIzLigePoPoziciji("League 1");
                            Klub trenutniKlub = klubList.get(i);
                            Klub buduciKlub = visaLiga.get(16 + i);
                            if(klub.matches(klubList.get(i).getIme())) {
                                editor.putString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "League 1");
                                editor.apply();
                            }
                            trenutniKlub.setLiga("League 1");
                            trenutniKlub.setPozicija(17 + i);
                            trenutniKlub.setPobede(0);
                            trenutniKlub.setNeresene(0);
                            trenutniKlub.setPorazi(0);
                            trenutniKlub.setDatiGolovi(0);
                            trenutniKlub.setPrimljeniGolovi(0);
                            trenutniKlub.setGolRazlika(0);
                            trenutniKlub.setBodovi(0);
                            mainViewModel.updateKlub(trenutniKlub);
                            buduciKlub.setLiga("League 2");
                            buduciKlub.setPozicija(i + 1);
                            mainViewModel.updateKlub(buduciKlub);
                        }
                    }
                    break;
                case "League 3":
                    for(int i=0; i<20; i++) {
                        if(i > 15) {
                            List<Klub> nizaLiga = mainViewModel.uzmiKluboveIzLigePoPoziciji("League 4");
                            Klub trenutniKlub = klubList.get(i);
                            Klub buduciKlub = nizaLiga.get(i - 16);
                            if(klub.matches(klubList.get(i).getIme())) {
                                editor.putString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "League 4");
                                editor.apply();
                            }
                            trenutniKlub.setLiga("League 4");
                            trenutniKlub.setPozicija(i-15);
                            trenutniKlub.setPobede(0);
                            trenutniKlub.setNeresene(0);
                            trenutniKlub.setPorazi(0);
                            trenutniKlub.setDatiGolovi(0);
                            trenutniKlub.setPrimljeniGolovi(0);
                            trenutniKlub.setGolRazlika(0);
                            trenutniKlub.setBodovi(0);
                            mainViewModel.updateKlub(trenutniKlub);
                            buduciKlub.setLiga("League 3");
                            buduciKlub.setPozicija(i - 16 + 1);
                            mainViewModel.updateKlub(buduciKlub);
                        }
                        if(i < 4) {
                            List<Klub> visaLiga = mainViewModel.uzmiKluboveIzLigePoPoziciji("League 2");
                            Klub trenutniKlub = klubList.get(i);
                            Klub buduciKlub = visaLiga.get(16 + i);
                            if(klub.matches(klubList.get(i).getIme())) {
                                editor.putString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "League 2");
                                editor.apply();
                            }
                            trenutniKlub.setLiga("League 2");
                            trenutniKlub.setPozicija(17 + i);
                            trenutniKlub.setPobede(0);
                            trenutniKlub.setNeresene(0);
                            trenutniKlub.setPorazi(0);
                            trenutniKlub.setDatiGolovi(0);
                            trenutniKlub.setPrimljeniGolovi(0);
                            trenutniKlub.setGolRazlika(0);
                            trenutniKlub.setBodovi(0);
                            mainViewModel.updateKlub(trenutniKlub);
                            buduciKlub.setLiga("League 3");
                            buduciKlub.setPozicija(i + 1);
                            mainViewModel.updateKlub(buduciKlub);
                        }
                    }
                    break;
                case "League 4":
                    for(int i=0; i<20; i++) {
                        if(i > 15) {
                            List<Klub> nizaLiga = mainViewModel.uzmiKluboveIzLigePoPoziciji("League 5");
                            Klub trenutniKlub = klubList.get(i);
                            Klub buduciKlub = nizaLiga.get(i - 16);
                            if(klub.matches(klubList.get(i).getIme())) {
                                editor.putString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "League 5");
                                editor.apply();
                            }
                            trenutniKlub.setLiga("League 5");
                            trenutniKlub.setPozicija(i-15);
                            trenutniKlub.setPobede(0);
                            trenutniKlub.setNeresene(0);
                            trenutniKlub.setPorazi(0);
                            trenutniKlub.setDatiGolovi(0);
                            trenutniKlub.setPrimljeniGolovi(0);
                            trenutniKlub.setGolRazlika(0);
                            trenutniKlub.setBodovi(0);
                            mainViewModel.updateKlub(trenutniKlub);
                            buduciKlub.setLiga("League 4");
                            buduciKlub.setPozicija(i - 16 + 1);
                            mainViewModel.updateKlub(buduciKlub);
                        }
                        if(i < 4) {
                            List<Klub> visaLiga = mainViewModel.uzmiKluboveIzLigePoPoziciji("League 3");
                            Klub trenutniKlub = klubList.get(i);
                            Klub buduciKlub = visaLiga.get(16 + i);
                            if(klub.matches(klubList.get(i).getIme())) {
                                editor.putString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "League 3");
                                editor.apply();
                            }
                            trenutniKlub.setLiga("League 3");
                            trenutniKlub.setPozicija(17 + i);
                            trenutniKlub.setPobede(0);
                            trenutniKlub.setNeresene(0);
                            trenutniKlub.setPorazi(0);
                            trenutniKlub.setDatiGolovi(0);
                            trenutniKlub.setPrimljeniGolovi(0);
                            trenutniKlub.setGolRazlika(0);
                            trenutniKlub.setBodovi(0);
                            mainViewModel.updateKlub(trenutniKlub);
                            buduciKlub.setLiga("League 4");
                            buduciKlub.setPozicija(i + 1);
                            mainViewModel.updateKlub(buduciKlub);
                        }
                    }
                    break;
                case "League 5":
                    for(int i=0; i<20; i++) {
                        if(i > 15) {
                            List<Klub> nizaLiga = mainViewModel.uzmiKluboveIzLigePoPoziciji("League 6");
                            Klub trenutniKlub = klubList.get(i);
                            Klub buduciKlub = nizaLiga.get(i - 16);
                            if(klub.matches(klubList.get(i).getIme())) {
                                editor.putString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "League 6");
                                editor.apply();
                            }
                            trenutniKlub.setLiga("League 6");
                            trenutniKlub.setPozicija(i-15);
                            trenutniKlub.setPobede(0);
                            trenutniKlub.setNeresene(0);
                            trenutniKlub.setPorazi(0);
                            trenutniKlub.setDatiGolovi(0);
                            trenutniKlub.setPrimljeniGolovi(0);
                            trenutniKlub.setGolRazlika(0);
                            trenutniKlub.setBodovi(0);
                            mainViewModel.updateKlub(trenutniKlub);
                            buduciKlub.setLiga("League 5");
                            buduciKlub.setPozicija(i - 16 + 1);
                            mainViewModel.updateKlub(buduciKlub);
                        }
                        if(i < 4) {
                            List<Klub> visaLiga = mainViewModel.uzmiKluboveIzLigePoPoziciji("League 4");
                            Klub trenutniKlub = klubList.get(i);
                            Klub buduciKlub = visaLiga.get(16 + i);
                            if(klub.matches(klubList.get(i).getIme())) {
                                editor.putString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "League 4");
                                editor.apply();
                            }
                            trenutniKlub.setLiga("League 4");
                            trenutniKlub.setPozicija(17 + i);
                            trenutniKlub.setPobede(0);
                            trenutniKlub.setNeresene(0);
                            trenutniKlub.setPorazi(0);
                            trenutniKlub.setDatiGolovi(0);
                            trenutniKlub.setPrimljeniGolovi(0);
                            trenutniKlub.setGolRazlika(0);
                            trenutniKlub.setBodovi(0);
                            mainViewModel.updateKlub(trenutniKlub);
                            buduciKlub.setLiga("League 5");
                            buduciKlub.setPozicija(i + 1);
                            mainViewModel.updateKlub(buduciKlub);
                        }
                    }
                    break;
                case "League 6":
                    for(int i=0; i<20; i++) {
                        if(i > 15) {
                            List<Klub> nizaLiga = mainViewModel.uzmiKluboveIzLigePoPoziciji("League 7");
                            Klub trenutniKlub = klubList.get(i);
                            Klub buduciKlub = nizaLiga.get(i - 16);
                            if(klub.matches(klubList.get(i).getIme())) {
                                editor.putString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "League 7");
                                editor.apply();
                            }
                            trenutniKlub.setLiga("League 7");
                            trenutniKlub.setPozicija(i-15);
                            trenutniKlub.setPobede(0);
                            trenutniKlub.setNeresene(0);
                            trenutniKlub.setPorazi(0);
                            trenutniKlub.setDatiGolovi(0);
                            trenutniKlub.setPrimljeniGolovi(0);
                            trenutniKlub.setGolRazlika(0);
                            trenutniKlub.setBodovi(0);
                            mainViewModel.updateKlub(trenutniKlub);
                            buduciKlub.setLiga("League 6");
                            buduciKlub.setPozicija(i - 16 + 1);
                            mainViewModel.updateKlub(buduciKlub);
                        }
                        if(i < 4) {
                            List<Klub> visaLiga = mainViewModel.uzmiKluboveIzLigePoPoziciji("League 5");
                            Klub trenutniKlub = klubList.get(i);
                            Klub buduciKlub = visaLiga.get(16 + i);
                            if(klub.matches(klubList.get(i).getIme())) {
                                editor.putString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "League 5");
                                editor.apply();
                            }
                            trenutniKlub.setLiga("League 5");
                            trenutniKlub.setPozicija(17 + i);
                            trenutniKlub.setPobede(0);
                            trenutniKlub.setNeresene(0);
                            trenutniKlub.setPorazi(0);
                            trenutniKlub.setDatiGolovi(0);
                            trenutniKlub.setPrimljeniGolovi(0);
                            trenutniKlub.setGolRazlika(0);
                            trenutniKlub.setBodovi(0);
                            mainViewModel.updateKlub(trenutniKlub);
                            buduciKlub.setLiga("League 6");
                            buduciKlub.setPozicija(i + 1);
                            mainViewModel.updateKlub(buduciKlub);
                        }
                    }
                    break;
                case "League 7":
                    for(int i=0; i<20; i++) {
                        if(i < 4) {
                            List<Klub> visaLiga = mainViewModel.uzmiKluboveIzLigePoPoziciji("League 6");
                            Klub trenutniKlub = klubList.get(i);
                            Klub buduciKlub = visaLiga.get(16 + i);
                            if(klub.matches(klubList.get(i).getIme())) {
                                editor.putString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "League 6");
                                editor.apply();
                            }
                            trenutniKlub.setLiga("League 6");
                            trenutniKlub.setPozicija(17 + i);
                            trenutniKlub.setPobede(0);
                            trenutniKlub.setNeresene(0);
                            trenutniKlub.setPorazi(0);
                            trenutniKlub.setDatiGolovi(0);
                            trenutniKlub.setPrimljeniGolovi(0);
                            trenutniKlub.setGolRazlika(0);
                            trenutniKlub.setBodovi(0);
                            mainViewModel.updateKlub(trenutniKlub);
                            buduciKlub.setLiga("League 7");
                            buduciKlub.setPozicija(i + 1);
                            mainViewModel.updateKlub(buduciKlub);
                        }
                    }
                    break;
                default:
                    break;
            }

        }).start();

    }
}