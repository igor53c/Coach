package igra.ipcoding.coach.database.dao;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import igra.ipcoding.coach.database.Baza;
import igra.ipcoding.coach.database.entity.Klub;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class KlubDaoTest {

    private KlubDao klubDao;

    private Baza baza;

    @Before
    public void setUp() {

        Context context = ApplicationProvider.getApplicationContext();
        baza = Room.inMemoryDatabaseBuilder(context, Baza.class).build();
        klubDao = baza.klubDao();
    }

    @After
    public void tearDown() {

        baza.close();

    }

    @Test
    public void insertAndUzmiKlub() {
        Klub klub = new Klub(1,"klub", "liga", 1,50.0,0,0,0,
                0,0,0,0);
        klubDao.insert(klub);

        assertTrue(klubDao.uzmiKlub("klub").getIme().matches(klub.getIme()));

    }

}