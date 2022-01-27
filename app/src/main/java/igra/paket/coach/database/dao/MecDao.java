package igra.paket.coach.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import igra.paket.coach.database.entity.Mec;

@Dao
public interface MecDao {

    @Insert
    void insert(Mec mec);

    @Update
    void update(Mec mec);

    @Query("DELETE FROM raspored_tabela")
    void obrisiSve();

    @Query("SELECT * FROM raspored_tabela WHERE kolo = :kolo ORDER BY domacin ASC")
    List<Mec> uzmiMeceveIzJednogKola(int kolo);

    @Query("SELECT * FROM raspored_tabela WHERE (kolo = :kolo AND (domacin = :klub OR gost = :klub))")
    Mec uzmiMecKlubaIzNarednogKola(int kolo, String klub);
}
