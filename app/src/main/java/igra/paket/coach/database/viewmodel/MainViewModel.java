package igra.paket.coach.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import igra.paket.coach.database.Baza;
import igra.paket.coach.database.entity.Igrac;
import igra.paket.coach.database.entity.Istorija;
import igra.paket.coach.database.entity.Klub;
import igra.paket.coach.database.entity.Mec;
import igra.paket.coach.database.entity.Utakmice;

public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Utakmice>> utakmiceList;

    private final Baza baza;

    public MainViewModel(@NonNull Application application) {
        super(application);
        utakmiceList = new MutableLiveData<>();
        baza = Baza.getDatabase(getApplication().getApplicationContext());

    }

    public void updateKlub(Klub klub) { baza.klubDao().update(klub); }

    public void obrisiSveMeceve() { baza.mecDao().obrisiSve(); }

    public void insertMec(Mec mec) { baza.mecDao().insert(mec); }

    public void updateIgrac(Igrac igrac) {  baza.igracDao().update(igrac); }

    public void insertIstorija(Istorija istorija) { baza.istorijaDao().insert(istorija); }

    public Klub uzmiKlub(String klub) { return  baza.klubDao().uzmiKlub(klub); }

    public List<Igrac> uzmiSveIgrace() { return baza.igracDao().uzmiSveIgrace(); }

    public List<Klub> uzmiKluboveIzJedneLige(String liga) { return baza.klubDao().uzmiKluboveIzJedneLige(liga); }

    public List<Klub> uzmiKluboveIzLigePoRejtingu(String liga) { return baza.klubDao().uzmiKluboveIzLigePoRejtingu(liga); }

    public List<Klub> uzmiKluboveIzLigePoPoziciji(String liga) { return baza.klubDao().uzmiKluboveIzLigePoPoziciji(liga); }

    public MutableLiveData<List<Utakmice>> uzmiPoslednjihDesetUtakmicaObserver() { return utakmiceList; }

    public List<Utakmice> uzmiPoslednjihDesetUtakmica() {
        List<Utakmice> utakmiceList = baza.utakmiceDao().uzmiPoslednjihDesetUtakmica();
        if(utakmiceList.size() > 0) {
            this.utakmiceList.postValue(utakmiceList);
        } else {
            this.utakmiceList.postValue(null);
        }
        return utakmiceList;
    }
}
