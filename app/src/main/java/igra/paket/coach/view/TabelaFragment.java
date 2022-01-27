package igra.paket.coach.view;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import igra.paket.coach.R;
import igra.paket.coach.database.adapteri.RecyclerViewAdapterKlubovi;
import igra.paket.coach.database.entity.Klub;
import igra.paket.coach.database.viewmodel.TabelaViewModel;
import igra.paket.coach.pomocne.Konstante;


public class TabelaFragment extends Fragment {

    private RecyclerViewAdapterKlubovi recyclerViewAdapterKlubovi;
    RecyclerView recyclerView;

    TextView tv_tabelaLiga;
    List<Klub> klubList;

    String liga, klub;
    TabelaViewModel tabelaViewModel;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public TabelaFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);}

    @SuppressLint("CommitPrefEdits")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tabela, container, false);

        init(view);

        loadKlubList();

        tabelaViewModel.uzmiKluboveIzJedneLigeObserver().observe(requireActivity(), klubovi -> {

            initRecyclerView(view);
            recyclerViewAdapterKlubovi.setKlubList(klubovi);
        });

        return view;
    }

    @SuppressLint("CommitPrefEdits")
    private void init(View view) {

        sharedPreferences = requireActivity().getSharedPreferences(Konstante.SHARED_PREFERENCES_PREFIX,0);
        editor = sharedPreferences.edit();
        editor.putString(Konstante.SHARED_PREFERENCES_KEY_AKTIVNOST, "TabelaActivity");
        editor.apply();
        liga = sharedPreferences.getString(Konstante.SHARED_PREFERENCES_KEY_LIGA, "");
        klub = sharedPreferences.getString(Konstante.SHARED_PREFERENCES_KEY_KLUB, "");

        tv_tabelaLiga = view.findViewById(R.id.tv_tabelaLiga);
        tv_tabelaLiga.setText(liga);

        tabelaViewModel = new ViewModelProvider(requireActivity()).get(TabelaViewModel.class);

    }

    private void initRecyclerView(View view) {

        recyclerView = view.findViewById(R.id.lv_kluboviLista);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapterKlubovi = new RecyclerViewAdapterKlubovi(getActivity(), klub);
        recyclerView.setAdapter(recyclerViewAdapterKlubovi);

    }

    private void loadKlubList() {

        new Thread(() -> {

            klubList = tabelaViewModel.uzmiKluboveIzJedneLige(liga);

            for(int i = 0; i < 20; i++) {
                klubList.get(i).setPozicija(i + 1);
            }
        }).start();
    }

}