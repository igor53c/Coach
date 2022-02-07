package igra.ipcoding.coach.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import igra.ipcoding.coach.database.Baza;
import igra.ipcoding.coach.database.entity.Igrac;
import igra.ipcoding.coach.database.entity.Klub;
import igra.ipcoding.coach.database.entity.Mec;
import igra.ipcoding.coach.database.entity.Utakmice;

public class PredMecViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Igrac>> igracList;

    private final Baza baza;

    public PredMecViewModel (@NonNull Application application) {
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

    public List<Igrac> uzmiSveIgrace() {

        return baza.igracDao().uzmiSveIgracePoBroju();
    }

    public List<Mec> uzmiMeceveIzJednogKola(int kolo) {

        return baza.mecDao().uzmiMeceveIzJednogKola(kolo);

    }

    public double uzmiRejtingKluba(String klub) {
        return baza.klubDao().uzmiRejtingKluba(klub);
    }

    public void updateIgrac(Igrac igrac) {
        baza.igracDao().update(igrac);
    }

    public Klub uzmiKlub(String klub) { return  baza.klubDao().uzmiKlub(klub); }

    public void updateKlub(Klub klub) {
        baza.klubDao().update(klub);
    }

    public void updateMec(Mec mec) { baza.mecDao().update(mec); }

    public void insertUtakmice(Utakmice utakmice) { baza.utakmiceDao().insert(utakmice); }
}

