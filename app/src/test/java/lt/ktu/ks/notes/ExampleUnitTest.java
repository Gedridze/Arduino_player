package lt.ktu.ks.notes;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void song_length_is_correct() throws Exception {
        Melodija m = new Melodija(1, "3 A2 A4 A6 4 4 8,", "Melody");
        assertEquals(0.625, m.Trukme, 1e-15);
    }
}