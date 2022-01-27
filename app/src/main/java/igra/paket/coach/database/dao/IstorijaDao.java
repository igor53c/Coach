package igra.paket.coach.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import igra.paket.coach.database.entity.Istorija;

@Dao
public interface IstorijaDao {

    @Insert
    void insert(Istorija istorija);

    @Update
    void update(Istorija istorija);

    @Query("DELETE FROM istorija_tabela")
    void obrisiSve();

    @Query("SELECT * FROM istorija_tabela ORDER BY godina DESC")
    List<Istorija> uzmiCeluIstoriju();

}
