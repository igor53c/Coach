package igra.ipcoding.coach.database.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import igra.ipcoding.coach.database.Baza;
import igra.ipcoding.coach.database.entity.Istorija;

public class IstorijaViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Istorija>> istorijaList;

    private final Baza baza;

    public IstorijaViewModel(@NonNull Application application) {
        super(application);
        istorijaList = new MutableLiveData<>();
        baza = Baza.getDatabase(getApplication().getApplicationContext());

    }

    public MutableLiveData<List<Istorija>> uzmiCeluIstorijuObserver() {
        return istorijaList;
    }

    public List<Istorija> uzmiCeluIstoriju() {
        List<Istorija> istorijaList = baza.istorijaDao().uzmiCeluIstoriju();
        if (istorijaList.size() > 0) {
            this.istorijaList.postValue(istorijaList);
        } else {
            this.istorijaList.postValue(null);
        }
        return istorijaList;
    }
}
