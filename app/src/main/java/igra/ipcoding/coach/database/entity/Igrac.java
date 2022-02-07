package igra.ipcoding.coach.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "igraci_tabela")
public class Igrac {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "ime")
    private String ime;
    @ColumnInfo(name = "pozicija")
    private String pozicija;
    @ColumnInfo(name = "rejting")
    private double rejting;
    @ColumnInfo(name = "godine")
    private int godine;
    @ColumnInfo(name = "broj")
    private int broj;
    @ColumnInfo(name = "motivacija")
    private int motivacija;
    @ColumnInfo(name = "spremnost")
    private int spremnost;
    @ColumnInfo(name = "trening")
    private int trening;

    public Igrac(int id, String ime, String pozicija, double rejting, int godine, int broj, int motivacija, int spremnost, int trening) {
        this.id = id;
        this.ime = ime;
        this.pozicija = pozicija;
        this.rejting = rejting;
        this.godine = godine;
        this.broj = broj;
        this.motivacija = motivacija;
        this.spremnost = spremnost;
        this.trening = trening;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPozicija() {
        return pozicija;
    }

    public void setPozicija(String pozicija) {
        this.pozicija = pozicija;
    }

    public double getRejting() {
        return rejting;
    }

    public void setRejting(double rejting) {
        this.rejting = rejting;
    }

    public int getGodine() {
        return godine;
    }

    public void setGodine(int godine) {
        this.godine = godine;
    }

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    public int getMotivacija() {
        return motivacija;
    }

    public void setMotivacija(int motivacija) {
        this.motivacija = motivacija;
    }

    public int getSpremnost() {
        return spremnost;
    }

    public void setSpremnost(int spremnost) {
        this.spremnost = spremnost;
    }

    public int getTrening() {
        return trening;
    }

    public void setTrening(int trening) {
        this.trening = trening;
    }
}
