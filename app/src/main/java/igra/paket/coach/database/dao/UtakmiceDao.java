package igra.paket.coach.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import igra.paket.coach.database.entity.Utakmice;

@Dao
public interface UtakmiceDao {

    @Insert
    void insert(Utakmice utakmice);

    @Update
    void update(Utakmice utakmice);

    @Query("DELETE FROM utakmice_tabela")
    void obrisiSve();

    @Query("SELECT * FROM utakmice_tabela ORDER BY id DESC LIMIT 10")
    List<Utakmice> uzmiPoslednjihDesetUtakmica();
}
