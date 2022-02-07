package igra.ipcoding.coach.view;

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

import igra.ipcoding.coach.R;
import igra.ipcoding.coach.database.adapteri.RecyclerViewAdapterIstorija;
import igra.ipcoding.coach.database.entity.Istorija;
import igra.ipcoding.coach.database.viewmodel.IstorijaViewModel;

public class IstorijaFragment extends Fragment {

    RecyclerViewAdapterIstorija recyclerViewAdapterIstorija;

    List<Istorija> istorijaList;
    IstorijaViewModel istorijaViewModel;

    public IstorijaFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);}

    @SuppressLint("CommitPrefEdits")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_istorija, container, false);

        istorijaViewModel = new ViewModelProvider(requireActivity()).get(IstorijaViewModel.class);

        ucitajIstoriju();

        istorijaViewModel.uzmiCeluIstorijuObserver().observe(requireActivity(), istorija -> {

            initRecyclerView(view);
            recyclerViewAdapterIstorija.setIstorijaList(istorija);
        });

        return view;
    }

    private void initRecyclerView(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.lv_istorijaLista);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapterIstorija = new RecyclerViewAdapterIstorija(getActivity());
        recyclerView.setAdapter(recyclerViewAdapterIstorija);

    }

    private void ucitajIstoriju() {

        new Thread(() -> istorijaList = istorijaViewModel.uzmiCeluIstoriju()).start();

    }
}