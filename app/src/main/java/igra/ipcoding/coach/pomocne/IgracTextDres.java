package igra.ipcoding.coach.pomocne;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.utils.widget.ImageFilterView;

import java.util.List;

public class IgracTextDres {

    List<TextView> informacije;
    List<ImageFilterView> dres;

    public IgracTextDres(List<TextView> informacije, List<ImageFilterView> dres) {
        this.informacije = informacije;
        this.dres = dres;
    }

    public void add(TextView textView, ImageFilterView imageFilterView, int boja) {
        imageFilterView.setColorFilter(boja);
        imageFilterView.setVisibility(View.VISIBLE);
        this.informacije.add(textView);
        this.dres.add(imageFilterView);
    }

    public void ubaciBojuTeksta(int i, int bojaTeksta) {
        this.informacije.get(i).setTextColor(bojaTeksta);
    }

    public List<TextView> getInformacije() {
        return informacije;
    }

    public void setInformacije(List<TextView> informacije) {
        this.informacije = informacije;
    }

    public List<ImageFilterView> getDres() {
        return dres;
    }

    public void setDres(List<ImageFilterView> dres) {
        this.dres = dres;
    }
}
