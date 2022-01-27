package igra.paket.coach.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "raspored_tabela")
public class Mec {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "kolo")
    private int kolo;
    @ColumnInfo(name = "domacin")
    private String domacin;
    @ColumnInfo(name = "gost")
    private String gost;
    @ColumnInfo(name = "golovi_domacin")
    private int golDomacin;
    @ColumnInfo(name = "golovi_gost")
    private int golGost;

    public Mec(int id, int kolo, String domacin, String gost, int golDomacin, int golGost) {
        this.id = id;
        this.kolo = kolo;
        this.domacin = domacin;
        this.gost = gost;
        this.golDomacin = golDomacin;
        this.golGost = golGost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKolo() {
        return kolo;
    }

    public void setKolo(int kolo) {
        this.kolo = kolo;
    }

    public String getDomacin() {
        return domacin;
    }

    public void setDomacin(String domacin) {
        this.domacin = domacin;
    }

    public String getGost() {
        return gost;
    }

    public void setGost(String gost) {
        this.gost = gost;
    }

    public int getGolDomacin() {
        return golDomacin;
    }

    public void setGolDomacin(int golDomacin) {
        this.golDomacin = golDomacin;
    }

    public int getGolGost() {
        return golGost;
    }

    public void setGolGost(int golGost) {
        this.golGost = golGost;
    }
}
