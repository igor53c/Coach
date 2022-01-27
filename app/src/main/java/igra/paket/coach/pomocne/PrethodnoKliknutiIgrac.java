package igra.paket.coach.pomocne;

import android.widget.TextView;

import androidx.constraintlayout.utils.widget.ImageFilterView;

public class PrethodnoKliknutiIgrac {

    boolean stanje;
    TextView igrac;
    int brojUListi;

    public PrethodnoKliknutiIgrac(boolean stanje, TextView igrac, int brojUListi) {
        this.stanje = stanje;
        this.igrac = igrac;
        this.brojUListi = brojUListi;
    }

    public boolean isStanje() {
        return stanje;
    }

    public void setStanje(boolean stanje) {
        this.stanje = stanje;
    }

    public TextView getIgrac() {
        return igrac;
    }

    public void setIgrac(TextView igrac) {
        this.igrac = igrac;
    }

    public int getBrojUListi() {
        return brojUListi;
    }

    public void setBrojUListi(int brojUListi) {
        this.brojUListi = brojUListi;
    }
}
