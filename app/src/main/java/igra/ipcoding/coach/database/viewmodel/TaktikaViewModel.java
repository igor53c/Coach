package igra.ipcoding.coach.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import igra.ipcoding.coach.database.Baza;
import igra.ipcoding.coach.database.entity.Igrac;
import igra.ipcoding.coach.database.entity.Klub;

public class TaktikaViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Igrac>> igracList;

    private final Baza baza;

    public TaktikaViewModel(@NonNull Application application) {
        super(application);
        igracList = new MutableLiveData<>();
        baza = Baza.getDatabase(getApplication().getApplicationContext());

    }

    public MutableLiveData<List<Igrac>> uzmiSveIgracePoBrojuObserver() {
        return igracList;
    }

    public List<Igrac> uzmiSveIgracePoBroju() {
        List<Igrac> igracList = baza.igracDao().uzmiSveIgracePoBroju();
        if (igracList.size() > 0) {
            this.igracList.postValue(igracList);
        } else {
            this.igracList.postValue(null);
        }
        return igracList;
    }

    public void updateIgrac(Igrac igrac) {
        baza.igracDao().update(igrac);
    }

    public Klub uzmiKlub(String klub) { return  baza.klubDao().uzmiKlub(klub); }

    public void updateKlub(Klub klub) {
        baza.klubDao().update(klub);
    }
}
