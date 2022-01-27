package igra.paket.coach.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import igra.paket.coach.database.Baza;
import igra.paket.coach.database.entity.Klub;

public class TabelaViewModel extends AndroidViewModel {

    private final Baza baza;

    private final MutableLiveData<List<Klub>> klubList;

    public TabelaViewModel(@NonNull Application application) {
        super(application);
        klubList = new MutableLiveData<>();
        baza = Baza.getDatabase(getApplication().getApplicationContext());

    }

    public MutableLiveData<List<Klub>>uzmiKluboveIzJedneLigeObserver() {
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

}