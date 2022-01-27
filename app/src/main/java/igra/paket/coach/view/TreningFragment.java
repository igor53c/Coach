package igra.paket.coach.view;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import igra.paket.coach.R;
import igra.paket.coach.database.adapteri.RecyclerViewAdapterTrening;
import igra.paket.coach.database.entity.Igrac;
import igra.paket.coach.database.viewmodel.TreningViewModel;

public class TreningFragment extends Fragment {

    RecyclerViewAdapterTrening recyclerViewAdapterTrening;

    List<Igrac> igracList;
    TreningViewModel treningViewModel;

    public TreningFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);}

    @SuppressLint("CommitPrefEdits")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trening, container, false);

        treningViewModel = new ViewModelProvider(requireActivity()).get(TreningViewModel.class);

        ucitajListuIgraca();

        treningViewModel.uzmiSveIgraceObserver().observe(requireActivity(), igraci -> {

            initRecyclerView(view);
            recyclerViewAdapterTrening.setIgracList(igraci);
        });

        return view;
    }

    private void initRecyclerView(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.lv_prviTimLista);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapterTrening = new RecyclerViewAdapterTrening(getActivity());
        recyclerView.setAdapter(recyclerViewAdapterTrening);

    }

    private void ucitajListuIgraca() {

        new Thread(() -> igracList = treningViewModel.uzmiSveIgrace()).start();

    }

    private void sacuvajPodatkeTreninga() {

        new Thread(() -> {

            for (int i = 0; i < igracList.size(); i++) {

                treningViewModel.updateIgrac(igracList.get(i));

            }

        }).start();
    }

    @Override
    public void onStop() {
        super.onStop();
        sacuvajPodatkeTreninga();
    }
}