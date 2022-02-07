package igra.ipcoding.coach.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import igra.ipcoding.coach.database.Baza;
import igra.ipcoding.coach.database.entity.Klub;
import igra.ipcoding.coach.database.entity.Mec;

public class RasporedViewModel extends AndroidViewModel {

    private final Baza baza;

    private final MutableLiveData<List<Mec>> mecList;

    private final MutableLiveData<List<Klub>> klubList;

    public RasporedViewModel(@NonNull Application application) {
        super(application);
        mecList = new MutableLiveData<>();
        klubList = new MutableLiveData<>();
        baza = Baza.getDatabase(getApplication().getApplicationContext());

    }

    public MutableLiveData<List<Mec>> uzmiMeceveIzJednogKolaObserver() {
        return mecList;
    }

    public List<Mec> uzmiMeceveIzJednogKola(int kolo) {
        List<Mec> mecList = baza.mecDao().uzmiMeceveIzJednogKola(kolo);
        if(mecList.size() > 0) {
            this.mecList.postValue(mecList);
        } else {
            this.mecList.postValue(null);
        }
        return mecList;
    }

    public MutableLiveData<List<Klub>> uzmiKluboveIzJedneLigeObserver() {
        return klubList;
    }

    public List<Klub> uzmiKluboveIzJedneLige(String liga) {
        List<Klub> klubList = baza.klubDao().uzmiKluboveIzJedneLige(liga);
        if(klubList.size() > 0) {
            this.klubList.postValue(klubList);
        } else {
            this.klubList.postValue(null);
        }
        return klubList;
    }

    public void updateKlub(Klub klub) { baza.klubDao().update(klub); }
}
