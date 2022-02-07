package igra.ipcoding.coach.database.podaci;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class PodaciIgraciTest {

    @Spy
    PodaciIgraci podaciIgraci;

    @Test
    public void brojIgraca() {

        assertTrue(podaciIgraci.brojIgraca() > -1);
        assertNotEquals(0, podaciIgraci.brojIgraca());

    }

    @Test
    public void uzmiIgraca() {

        assertNotSame(podaciIgraci.uzmiIgraca(0), podaciIgraci.uzmiIgraca(1));
        assertSame(podaciIgraci.uzmiIgraca(0), podaciIgraci.uzmiIgraca(0));

    }
}