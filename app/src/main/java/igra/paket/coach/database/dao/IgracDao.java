package igra.paket.coach.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import igra.paket.coach.database.entity.Igrac;

@Dao
public interface IgracDao {

    @Insert
    void insert(Igrac igrac);

    @Update
    void update(Igrac igrac);

    @Query("DELETE FROM igraci_tabela")
    void obrisiSve();

    @Query("SELECT * FROM igraci_tabela WHERE id = :id")
    Igrac uzmiIgracaPoID(int id);

    @Query("SELECT * FROM igraci_tabela")
    List<Igrac> uzmiSveIgrace();

    @Query("SELECT * FROM igraci_tabela ORDER BY broj ASC")
    List<Igrac> uzmiSveIgracePoBroju();

    @Query("SELECT * FROM igraci_tabela ORDER BY rejting DESC")
    List<Igrac> uzmiSveIgracePoRejtingu();

}
