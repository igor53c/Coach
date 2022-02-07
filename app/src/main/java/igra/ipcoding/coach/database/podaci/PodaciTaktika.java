package igra.ipcoding.coach.database.podaci;

import java.util.ArrayList;
import java.util.List;

import igra.ipcoding.coach.pomocne.Taktika;

public class PodaciTaktika {

    List<Taktika> taktikaList;

    public PodaciTaktika() {
        taktikaList = new ArrayList<>();
        ubaciSveTaktike();
    }

    public int brojTaktika () {
        return taktikaList.size();
    }

    public Taktika uzmiTaktiku(int broj) {
        return taktikaList.get(broj);
    }

    private void ubaciSveTaktike() {
        taktikaList.add(new Taktika("5-4-1", true, true, true, true, true, true,
                true, true, false, true, true,
                false,true,false));
        taktikaList.add(new Taktika("5-3-2", true, true, true, true, true, true,
                false, true, true, true, false,
                true,false,true));
        taktikaList.add(new Taktika("4-5-1", true, true, true, false, true, true,
                true, true, true, true, true,
                false,true,false));
        taktikaList.add(new Taktika("4-4-2", true, true, true, false, true, true,
                true, true, false, true, true,
                true,false,true));
        taktikaList.add(new Taktika("4-3-3", true, true, true, false, true, true,
                false, true, true, true, false,
                true,true,true));
        taktikaList.add(new Taktika("3-5-2", true, false, true, true, true, false,
                true, true, true, true, true,
                true,false,true));
        taktikaList.add(new Taktika("3-4-3", true, false, true, true, true, false,
                true, true, false, true, true,
                true,true,true));
    }
}
