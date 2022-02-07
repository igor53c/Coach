package igra.ipcoding.coach.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "klubovi_tabela")
public class Klub {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "ime")
    private String ime;
    @ColumnInfo(name = "liga")
    private String liga;
    @ColumnInfo(name = "pozicija")
    private int pozicija;
    @ColumnInfo(name = "rejting")
    private double rejting;
    @ColumnInfo(name = "pobede")
    private int pobede;
    @ColumnInfo(name = "neresene")
    private int neresene;
    @ColumnInfo(name = "porazi")
    private int porazi;
    @ColumnInfo(name = "dati_golovi")
    private int datiGolovi;
    @ColumnInfo(name = "primljeni_golovi")
    private int primljeniGolovi;
    @ColumnInfo(name = "gol_razlika")
    private int golRazlika;
    @ColumnInfo(name = "bodovi")
    private int bodovi;

    public int getPozicija() {
        return pozicija;
    }

    public void setPozicija(int pozicija) {
        this.pozicija = pozicija;
    }

    public Klub(int id, String ime, String liga, int pozicija, double rejting, int pobede, int neresene, int porazi, int datiGolovi,
                int primljeniGolovi, int golRazlika, int bodovi) {
        this.id = id;
        this.ime = ime;
        this.liga = liga;
        this.pozicija = pozicija;
        this.rejting = rejting;
        this.pobede = pobede;
        this.neresene = neresene;
        this.porazi = porazi;
        this.datiGolovi = datiGolovi;
        this.primljeniGolovi = primljeniGolovi;
        this.golRazlika = golRazlika;
        this.bodovi = bodovi;
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

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }

    public double getRejting() {
        return rejting;
    }

    public void setRejting(double rejting) {
        this.rejting = rejting;
    }

    public int getPobede() {
        return pobede;
    }

    public void setPobede(int pobede) {
        this.pobede = pobede;
    }

    public int getNeresene() {
        return neresene;
    }

    public void setNeresene(int neresene) {
        this.neresene = neresene;
    }

    public int getPorazi() {
        return porazi;
    }

    public void setPorazi(int porazi) {
        this.porazi = porazi;
    }

    public int getDatiGolovi() {
        return datiGolovi;
    }

    public void setDatiGolovi(int datiGolovi) {
        this.datiGolovi = datiGolovi;
    }

    public int getPrimljeniGolovi() {
        return primljeniGolovi;
    }

    public void setPrimljeniGolovi(int primljeniGolovi) {
        this.primljeniGolovi = primljeniGolovi;
    }

    public int getGolRazlika() {
        return golRazlika;
    }

    public void setGolRazlika(int golRazlika) {
        this.golRazlika = golRazlika;
    }

    public int getBodovi() {
        return bodovi;
    }

    public void setBodovi(int bodovi) {
        this.bodovi = bodovi;
    }
}
