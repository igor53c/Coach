package igra.paket.coach.view;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import igra.paket.coach.R;
import igra.paket.coach.database.adapteri.RecyclerViewAdapterMecevi;
import igra.paket.coach.database.entity.Klub;
import igra.paket.coach.database.entity.Mec;
import igra.paket.coach.database.viewmodel.RasporedViewModel;
import igra.paket.coach.pomocne.Konstante;


public class RasporedFragment extends Fragment implements View.OnClickListener {

    List<Mec> mecList;

    List<Klub> klubList;

    Button btn_nazadKolo, btn_napredKolo;
    TextView tv_ligaRaspored, tv_brojKolaRaspored;
    int prikazanoKolo;

    RecyclerView recyclerView;
    RecyclerViewAdapterMecevi recyclerViewAdapterMecevi;
    RasporedViewModel rasporedViewModel;
    String klub, liga;
    int kolo, numberWeek;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public RasporedFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);}

    @SuppressLint("CommitPrefEdits")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_raspored, container, false);

        init(view);

        tv_ligaRaspored.setText(liga);

        rasporedViewModel = new ViewModelProvider(requireActivity()).get(RasporedViewModel.class);

        prikazanoKolo = kolo;

        if((kolo > 1 && kolo < 38) || (kolo == 38 && numberWeek < 22)) {
            prikazanoKolo = kolo - 1;
        }

        tv_brojKolaRaspored.setText("Kolo " + prikazanoKolo);

        loadMecList(prikazanoKolo);

        loadKlubList(liga);

        rasporedViewModel.uzmiMeceveIzJednogKolaObserver().observe(requireActivity(), mecevi -> {

            initRecyclerView();
            recyclerViewAdapterMecevi.setMecList(mecevi);

        });

        rasporedViewModel.uzmiKluboveIzJedneLigeObserver().observe(requireActivity(), klubovi -> new Thread(() -> {

            for (int i = 0; i < klubovi.size(); i++) {

                klubovi.get(i).setPozicija(i + 1);

                rasporedViewModel.updateKlub(klubovi.get(i));

            }

        }).start());

        return view;
    }

    @SuppressLint("CommitPrefEdits")
    private void init(View view) {

        sharedPreferences = requireActivity().getSharedPreferences(Konstante.SHARED_PREFERENCES_PREFIX,0);
        editor = sharedPreferences.edit();
        editor.putString(Konstante.SHARED_PREFERENCES_KEY_AKTIVNOST, "RasporedActivity");
        editor.apply();
        liga = sharedPreferences.getString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "");
        klub = sharedPreferences.getString(Konstante.SHARED_PREFERENCES_KEY_KLUB, "");
        kolo = sharedPreferences.getInt(Konstante.SHARED_PREFERENCES_KEY_KOLO, 1);
        numberWeek = sharedPreferences.getInt(Konstante.SHARED_PREFERENCES_KEY_SEDMICA, 0);

        btn_nazadKolo = view.findViewById(R.id.btn_nazadKolo);
        btn_napredKolo = view.findViewById(R.id.btn_napredKolo);
        tv_ligaRaspored = view.findViewById(R.id.tv_ligaRaspored);
        tv_brojKolaRaspored = view.findViewById(R.id.tv_brojKolaRaspored);
        recyclerView = view.findViewById(R.id.lv_koloRasporedLista);

        btn_nazadKolo.setOnClickListener(this);
        btn_napredKolo.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        btn_nazadKolo.setClickable(false);
        btn_napredKolo.setClickable(false);

        new Handler().postDelayed(() -> {

            btn_nazadKolo.setClickable(true);
            btn_napredKolo.setClickable(true);

        }, 500);

        switch (v.getId()) {
            case R.id.btn_nazadKolo:

                if(prikazanoKolo > 1) {
                    prikazanoKolo--;

                    new Thread(() -> {

                        tv_brojKolaRaspored.setText("Kolo " + prikazanoKolo);

                        rasporedViewModel.uzmiMeceveIzJednogKola(prikazanoKolo);

                    }).start();
                }
                break;

            case R.id.btn_napredKolo:

                if(prikazanoKolo < 38) {
                    prikazanoKolo++;

                    new Thread(() -> {

                        tv_brojKolaRaspored.setText("Kolo " + prikazanoKolo);

                        rasporedViewModel.uzmiMeceveIzJednogKola(prikazanoKolo);

                    }).start();
                }
                break;

            default:
                break;
        }

    }

    private void initRecyclerView() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapterMecevi = new RecyclerViewAdapterMecevi(getActivity(), klub);
        recyclerView.setAdapter(recyclerViewAdapterMecevi);

    }

    private void loadMecList(int prikazanoKolo) {

        new Thread(() -> mecList = rasporedViewModel.uzmiMeceveIzJednogKola(prikazanoKolo)).start();

    }

    private void loadKlubList(String liga) {

        new Thread(() -> klubList = rasporedViewModel.uzmiKluboveIzJedneLige(liga)).start();
    }

}