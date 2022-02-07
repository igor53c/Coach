package igra.ipcoding.coach.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import igra.ipcoding.coach.database.dao.IgracDao;
import igra.ipcoding.coach.database.dao.IstorijaDao;
import igra.ipcoding.coach.database.dao.KlubDao;
import igra.ipcoding.coach.database.dao.MecDao;
import igra.ipcoding.coach.database.dao.UtakmiceDao;
import igra.ipcoding.coach.database.entity.Igrac;
import igra.ipcoding.coach.database.entity.Istorija;
import igra.ipcoding.coach.database.entity.Klub;
import igra.ipcoding.coach.database.entity.Mec;
import igra.ipcoding.coach.database.entity.Utakmice;


@Database( version = 1,
        entities = {
                Igrac.class,
                Klub.class,
                Mec.class,
                Istorija.class,
                Utakmice.class
        },
        exportSchema = false
)
public abstract class Baza extends RoomDatabase {

    private static volatile Baza INSTANCE;

    static final ExecutorService databaseWriteExecutor = Executors.newCachedThreadPool();

    public abstract IgracDao igracDao();

    public abstract KlubDao klubDao();

    public abstract MecDao mecDao();

    public abstract IstorijaDao istorijaDao();

    public abstract UtakmiceDao utakmiceDao();

    public static Baza getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Baza.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Baza.class, "baza")
                            .fallbackToDestructiveMigration()
                            .addCallback(callback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {

            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {

            });
        }
    };

}
