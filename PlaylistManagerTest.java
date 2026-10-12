import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for PlaylistManager: one normal case and one bad-input case for
 * each public method.
 *
 * @author Archer
 * @version October 2026
 */
public class PlaylistManagerTest
{
    private PlaylistManager manager;

    /**p
     * Gives every test a fresh, empty playlist.
     */
    @Before
    public void setUp()
    {
        manager = new PlaylistManager();
    }

    // ---------------------------------------------------------- addSong

    /**
     * Normal: a valid song is added.
     */
    @Test
    public void testAddSong()
    {
        assertTrue(manager.addSong("Hey Jude", "Beatles", "Rock"));
        assertEquals(1, manager.size());
    }

    /**
     * Bad input: null, empty, and whitespace-only titles are rejected.
     */
    @Test
    public void testAddSongBadTitle()
    {
        assertFalse(manager.addSong(null, "Beatles", "Rock"));
        assertFalse(manager.addSong("", "Beatles", "Rock"));
        assertFalse(manager.addSong("   ", "Beatles", "Rock"));
        assertEquals(0, manager.size());
    }

    /**
     * Bad input: a null artist or genre is rejected.
     */
    @Test
    public void testAddSongNullArtistOrGenre()
    {
        assertFalse(manager.addSong("Hey Jude", null, "Rock"));
        assertFalse(manager.addSong("Hey Jude", "Beatles", null));
        assertEquals(0, manager.size());
    }

    /**
     * Bad input: a 1000-character title is accepted but trimmed.
     */
    @Test
    public void testAddSongLongTitle()
    {
        StringBuilder longTitle = new StringBuilder();
        for (int i = 0; i < 1000; i++)
        {
            longTitle.append('a');
        }
        assertTrue(manager.addSong(longTitle.toString(), "X", "Rock"));
        String stored = manager.viewPlaylist().get(0).getTitle();
        assertEquals(PlaylistManager.MAX_TITLE_LENGTH, stored.length());
    }

    // ----------------------------------------------------- viewPlaylist

    /**
     * Normal: songs come back in the order they were added.
     */
    @Test
    public void testViewPlaylist()
    {
        manager.addSong("One", "A", "Rock");
        manager.addSong("Two", "B", "Pop");
        manager.addSong("Three", "C", "Country");
        List<Song> songs = manager.viewPlaylist();
        assertEquals(3, songs.size());
        assertEquals("One", songs.get(0).getTitle());
        assertEquals("Two", songs.get(1).getTitle());
        assertEquals("Three", songs.get(2).getTitle());
    }

    /**
     * Bad input / empty collection: an empty playlist gives an empty list,
     * not null.
     */
    @Test
    public void testViewPlaylistEmpty()
    {
        List<Song> songs = manager.viewPlaylist();
        assertNotNull(songs);
        assertTrue(songs.isEmpty());
    }

    /**
     * Changing the returned list must not change the playlist.
     */
    @Test
    public void testViewPlaylistIsCopy()
    {
        manager.addSong("One", "A", "Rock");
        manager.viewPlaylist().clear();
        assertEquals(1, manager.size());
    }

    // ------------------------------------------------ removeSongByTitle

    /**
     * Normal: a matching song is removed (case does not matter).
     */
    @Test
    public void testRemoveSongByTitle()
    {
        manager.addSong("Hey Jude", "Beatles", "Rock");
        assertTrue(manager.removeSongByTitle("hey jude"));
        assertEquals(0, manager.size());
    }

    /**
     * Bad input: removing a title that is not there leaves the playlist
     * unchanged.
     */
    @Test
    public void testRemoveSongNotThere()
    {
        manager.addSong("Hey Jude", "Beatles", "Rock");
        assertFalse(manager.removeSongByTitle("Nope"));
        assertEquals(1, manager.size());
    }

    /**
     * Bad input / empty collection: removing from an empty playlist, or
     * with a null or blank title, returns false.
     */
    @Test
    public void testRemoveSongEmptyOrBlank()
    {
        assertFalse(manager.removeSongByTitle("Anything"));
        manager.addSong("Hey Jude", "Beatles", "Rock");
        assertFalse(manager.removeSongByTitle(null));
        assertFalse(manager.removeSongByTitle("  "));
        assertEquals(1, manager.size());
    }

    /**
     * Removing from the middle keeps the other songs in order.
     */
    @Test
    public void testRemoveSongMiddle()
    {
        manager.addSong("One", "A", "Rock");
        manager.addSong("Two", "B", "Pop");
        manager.addSong("Three", "C", "Country");
        assertTrue(manager.removeSongByTitle("Two"));
        List<Song> songs = manager.viewPlaylist();
        assertEquals(2, songs.size());
        assertEquals("One", songs.get(0).getTitle());
        assertEquals("Three", songs.get(1).getTitle());
    }

    // -------------------------------------------------------- shuffleSong

    /**
     * Normal: the result is a song in the playlist, and nothing is removed.
     */
    @Test
    public void testShuffleSong()
    {
        manager.addSong("One", "A", "Rock");
        manager.addSong("Two", "B", "Pop");
        manager.addSong("Three", "C", "Country");
        for (int i = 0; i < 20; i++)
        {
            Song picked = manager.shuffleSong();
            assertNotNull(picked);
            assertTrue(manager.viewPlaylist().contains(picked));
        }
        assertEquals(3, manager.size());
    }

    /**
     * Bad input / empty collection: shuffling an empty playlist gives null.
     */
    @Test
    public void testShuffleSongEmpty()
    {
        assertNull(manager.shuffleSong());
    }

    // ------------------------------------------------------ getSongByGenre

    /**
     * Normal: returns a song of the requested genre (case does not matter).
     */
    @Test
    public void testGetSongByGenre()
    {
        manager.addSong("Rocker", "A", "Rock");
        manager.addSong("Popper", "B", "Pop");
        assertEquals("Rocker", manager.getSongByGenre("rock").getTitle());
        assertEquals("Popper", manager.getSongByGenre("Pop").getTitle());
    }

    /**
     * Bad input: a valid genre with no songs gives null.
     */
    @Test
    public void testGetSongByGenreNoMatch()
    {
        manager.addSong("Rocker", "A", "Rock");
        assertNull(manager.getSongByGenre("Jazz"));
    }

    /**
     * Bad input / empty collection: null or blank genre, or an empty
     * playlist, gives null.
     */
    @Test
    public void testGetSongByGenreEmptyOrBlank()
    {
        assertNull(manager.getSongByGenre("Rock"));
        manager.addSong("Rocker", "A", "Rock");
        assertNull(manager.getSongByGenre(null));
        assertNull(manager.getSongByGenre("  "));
    }

    /**
     * With several songs in one genre, the pick is always one of them.
     */
    @Test
    public void testGetSongByGenreSeveral()
    {
        manager.addSong("R1", "A", "Rock");
        manager.addSong("P1", "B", "Pop");
        manager.addSong("R2", "C", "Rock");
        for (int i = 0; i < 20; i++)
        {
            Song picked = manager.getSongByGenre("Rock");
            assertEquals("Rock", picked.getGenre());
        }
    }

    // --------------------------------------------------------------- size

    /**
     * Normal: size tracks adds and removes.
     */
    @Test
    public void testSize()
    {
        assertEquals(0, manager.size());
        manager.addSong("One", "A", "Rock");
        assertEquals(1, manager.size());
        manager.removeSongByTitle("One");
        assertEquals(0, manager.size());
    }

    /**
     * Bad input: a rejected add does not change the size.
     */
    @Test
    public void testSizeAfterRejectedAdd()
    {
        manager.addSong("", "A", "Rock");
        assertEquals(0, manager.size());
    }
}