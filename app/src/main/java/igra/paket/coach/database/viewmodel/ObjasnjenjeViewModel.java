package igra.paket.coach.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import igra.paket.coach.database.Baza;
import igra.paket.coach.database.entity.Klub;
import igra.paket.coach.database.entity.Mec;

public class ObjasnjenjeViewModel extends AndroidViewModel {

    private final Baza baza;

    public ObjasnjenjeViewModel(@NonNull Application application) {
        super(application);
        baza = Baza.getDatabase(getApplication().getApplicationContext());

    }

    public Mec uzmiMecKlubaIzNarednogKola(int kolo, String klub) { return baza.mecDao().uzmiMecKlubaIzNarednogKola(kolo, klub); }

    public Klub uzmiKlub(String klub) { return baza.klubDao().uzmiKlub(klub); }
}