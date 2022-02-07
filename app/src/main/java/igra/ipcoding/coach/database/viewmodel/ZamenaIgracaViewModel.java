package igra.ipcoding.coach.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import igra.ipcoding.coach.database.Baza;
import igra.ipcoding.coach.database.entity.Igrac;

public class ZamenaIgracaViewModel extends AndroidViewModel {

    private final Baza baza;

    private final MutableLiveData<Igrac> igrac;

    public ZamenaIgracaViewModel(@NonNull Application application) {
        super(application);
        igrac = new MutableLiveData<>();
        baza = Baza.getDatabase(getApplication().getApplicationContext());

    }

    public MutableLiveData<Igrac> uzmiIgracaPoIDObserver() {
        return igrac;
    }

    public Igrac uzmiIgracaPoID(int id) {
        return baza.igracDao().uzmiIgracaPoID(id);
    }

    public void updateIgrac(Igrac igrac) {
        baza.igracDao().update(igrac);
    }

}