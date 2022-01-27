package igra.paket.coach.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import igra.paket.coach.database.Baza;
import igra.paket.coach.database.entity.Igrac;

public class IgraciViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Igrac>> igracList;

    private final Baza baza;

    public IgraciViewModel(@NonNull Application application) {
        super(application);
        igracList = new MutableLiveData<>();
        baza = Baza.getDatabase(getApplication().getApplicationContext());

    }

    public MutableLiveData<List<Igrac>>uzmiSveIgraceObserver() {
        return igracList;
    }

    public List<Igrac> uzmiSveIgrace() {
        List<Igrac> igracList = baza.igracDao().uzmiSveIgracePoRejtingu();
        if(igracList.size() > 0) {
            this.igracList.postValue(igracList);
        } else {
            this.igracList.postValue(null);
        }
        return igracList;
    }
}