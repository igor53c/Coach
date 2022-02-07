package igra.ipcoding.coach.pomocne;

public class Taktika {

    String ime;
    boolean gk,
            dr, drc, dc, dlc, dl,
            mr, mrc, mc, mlc, ml,
            frc, fc, flc;

    public String getIme() {
        return ime;
    }

    public boolean isGk() {
        return gk;
    }

    public boolean isDr() {
        return dr;
    }

    public boolean isDrc() {
        return drc;
    }

    public boolean isDc() {
        return dc;
    }

    public boolean isDlc() {
        return dlc;
    }

    public boolean isDl() {
        return dl;
    }

    public boolean isMr() {
        return mr;
    }

    public boolean isMrc() {
        return mrc;
    }

    public boolean isMc() {
        return mc;
    }

    public boolean isMlc() {
        return mlc;
    }

    public boolean isMl() {
        return ml;
    }

    public boolean isFrc() {
        return frc;
    }

    public boolean isFc() {
        return fc;
    }

    public boolean isFlc() {
        return flc;
    }

    public Taktika(String ime, boolean gk, boolean dr, boolean drc, boolean dc, boolean dlc, boolean dl,
                   boolean mr, boolean mrc, boolean mc, boolean mlc, boolean ml,
                   boolean frc, boolean fc, boolean flc) {
        this.ime = ime;
        this.gk = gk;
        this.dr = dr;
        this.drc = drc;
        this.dc = dc;
        this.dlc = dlc;
        this.dl = dl;
        this.mr = mr;
        this.mrc = mrc;
        this.mc = mc;
        this.mlc = mlc;
        this.ml = ml;
        this.frc = frc;
        this.fc = fc;
        this.flc = flc;
    }
}
