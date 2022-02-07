package igra.ipcoding.coach.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "istorija_tabela")
public class Istorija {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "godina")
    private int godina;
    @ColumnInfo(name = "liga")
    private String liga;
    @ColumnInfo(name = "pozicija")
    private int pozicija;

    public Istorija(int id, int godina, String liga, int pozicija) {
        this.id = id;
        this.godina = godina;
        this.liga = liga;
        this.pozicija = pozicija;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGodina() {
        return godina;
    }

    public void setGodina(int godina) {
        this.godina = godina;
    }

    public String getLiga() {
        return liga;
    }

    public void setLiga(String liga) {
        this.liga = liga;
    }

    public int getPozicija() {
        return pozicija;
    }

    public void setPozicija(int pozicija) {
        this.pozicija = pozicija;
    }
}
