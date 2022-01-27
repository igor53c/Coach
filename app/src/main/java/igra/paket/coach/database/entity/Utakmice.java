package igra.paket.coach.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "utakmice_tabela")
public class Utakmice {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "protivnik")
    private String protivnik;
    @ColumnInfo(name = "mesto")
    private String mesto;
    @ColumnInfo(name = "dati_golovi")
    private int datiGolovi;
    @ColumnInfo(name = "primljeni_golovi")
    private int primljeniGolovi;

    public Utakmice(int id, String protivnik, String mesto, int datiGolovi, int primljeniGolovi) {
        this.id = id;
        this.protivnik = protivnik;
        this.mesto = mesto;
        this.datiGolovi = datiGolovi;
        this.primljeniGolovi = primljeniGolovi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProtivnik() {
        return protivnik;
    }

    public void setProtivnik(String protivnik) {
        this.protivnik = protivnik;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
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
}
