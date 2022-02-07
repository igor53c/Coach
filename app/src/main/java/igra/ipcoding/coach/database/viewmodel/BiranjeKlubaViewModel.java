package igra.ipcoding.coach.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import igra.ipcoding.coach.database.Baza;
import igra.ipcoding.coach.database.entity.Igrac;
import igra.ipcoding.coach.database.entity.Klub;

public class BiranjeKlubaViewModel extends AndroidViewModel {

    private final Baza baza;

    public BiranjeKlubaViewModel(@NonNull Application application) {
        super(application);
        baza = Baza.getDatabase(getApplication().getApplicationContext());

    }

    public void insertKlub(Klub klub) {
        baza.klubDao().insert(klub);
    }

    public void insertIgrac(Igrac igrac) {
        baza.igracDao().insert(igrac);
    }

    public double uzmiRejtingKluba(String imeKluba) {
        return baza.klubDao().uzmiRejtingKluba(imeKluba);
    }

    public void obrisiSveKlubove() {
        baza.klubDao().obrisiSve();
    }

    public void obrisiSveIgrace() {
        baza.igracDao().obrisiSve();
    }

    public void obrisiSveMeceve() {
        baza.mecDao().obrisiSve();
    }

    public void obrisiCeluIstoriju() {
        baza.istorijaDao().obrisiSve();
    }

    public void obrisiSveUtakmice() { baza.utakmiceDao().obrisiSve(); }
}
