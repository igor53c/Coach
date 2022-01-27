package igra.paket.coach.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import igra.paket.coach.database.entity.Klub;


@Dao
public interface KlubDao {

    @Insert
    void insert(Klub klub);

    @Update
    void update(Klub klub);

    @Query("DELETE FROM klubovi_tabela")
    void obrisiSve();

    @Query("SELECT rejting FROM klubovi_tabela WHERE ime = :klubIme")
    double uzmiRejtingKluba (String klubIme);

    @Query("SELECT * FROM klubovi_tabela WHERE liga = :liga ORDER BY bodovi DESC, gol_razlika DESC")
    List<Klub> uzmiKluboveIzJedneLige(String liga);

    @Query("SELECT * FROM klubovi_tabela WHERE liga = :izLige ORDER BY pozicija ASC")
    List<Klub> uzmiKluboveIzLigePoPoziciji(String izLige);

    @Query("SELECT * FROM klubovi_tabela WHERE liga = :izLige ORDER BY rejting DESC")
    List<Klub> uzmiKluboveIzLigePoRejtingu(String izLige);

    @Query("SELECT * FROM klubovi_tabela WHERE ime = :klub")
    Klub uzmiKlub(String klub);

}
