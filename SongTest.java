/**
 * Test class for the Song class.
 * Uses student.TestCase for unit testing.
 *
 * @author Matthew Abramson 
 * @version October 2026
 */
public class SongTest extends student.TestCase {

    /**
     * Tests that the getters return the correct values.
     */
    public void testGetters() {
        Song s = new Song("Halo", "Beyonce", "Pop");

        assertEquals("Halo", s.getTitle());
        assertEquals("Beyonce", s.getArtist());
        assertEquals("Pop", s.getGenre());
    }

    /**
     * Tests that two identical Song objects are considered equal.
     */
    public void testEqualsSameSong() {
        Song a = new Song("Halo", "Beyonce", "Pop");
        Song b = new Song("Halo", "Beyonce", "Pop");

        assertTrue(a.equals(b));
    }

    /**
     * Tests that two different Song objects are not equal.
     */
    public void testEqualsDifferentSong() {
        Song a = new Song("Halo", "Beyonce", "Pop");
        Song b = new Song("Numb", "Linkin Park", "Rock");

        assertFalse(a.equals(b));
    }

    /**
     * Tests that equals() returns false when comparing to null.
     */
    public void testEqualsNull() {
        Song a = new Song("Halo", "Beyonce", "Pop");

        assertFalse(a.equals(null));
    }

    /**
     * Tests that equals() returns false when comparing to a non-Song object.
     */
    public void testEqualsDifferentType() {
        Song a = new Song("Halo", "Beyonce", "Pop");

        assertFalse(a.equals("not a song"));
    }


    /**
     * Tests that a song is equal to itself.
     */
    public void testEqualsSameObject() {
        Song a = new Song("Halo", "Beyonce", "Pop");

        assertTrue(a.equals(a));
    }

    /**
     * Tests that songs differing only in artist are not equal.
     */
    public void testEqualsDifferentArtistOnly() {
        Song a = new Song("Halo", "Beyonce", "Pop");
        Song b = new Song("Halo", "Someone Else", "Pop");

        assertFalse(a.equals(b));
    }

    /**
     * Tests that songs differing only in genre are not equal.
     */
    public void testEqualsDifferentGenreOnly() {
        Song a = new Song("Halo", "Beyonce", "Pop");
        Song b = new Song("Halo", "Beyonce", "R&B");

        assertFalse(a.equals(b));
    }

    /**
     * Tests that equals() handles null fields without crashing.
     */
    public void testEqualsWithNullFields() {
        Song a = new Song(null, "Beyonce", "Pop");
        Song b = new Song(null, "Beyonce", "Pop");
        Song c = new Song("Halo", "Beyonce", "Pop");

        assertTrue(a.equals(b));
        assertFalse(a.equals(c));
    }


    /**
     * Tests the readable string form of a song.
     */
    public void testToString() {
        Song a = new Song("Halo", "Beyonce", "Pop");

        assertEquals("Halo by Beyonce (Pop)", a.toString());
    }
}