import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for PlaylistManager: normal cases and the bad-input cases from the
 * MusicMate test plan (empty/null input, missing songs, empty playlist).
 *
 * @author Archer Kim
 * @version 1.1
 */
public class PlaylistManagerTest {

    private PlaylistManager manager;

    /**
     * Creates a fresh, empty PlaylistManager before each test.
     */
    @BeforeEach
    public void setUp() {
        manager = new PlaylistManager();
    }

    // ---------- addSong ----------

    /**
     * Tests that a valid song is accepted and the playlist grows.
     */
    @Test
    public void testAddSongValid() {
        assertTrue(manager.addSong("Believer", "Imagine Dragons", "Rock"));
        assertEquals(1, manager.viewPlaylist().size());
    }

    /**
     * Tests that an empty title is rejected and the playlist is unchanged.
     */
    @Test
    public void testAddSongEmptyTitle() {
        assertFalse(manager.addSong("", "Artist", "Rock"));
        assertEquals(0, manager.viewPlaylist().size());
    }

    /**
     * Tests that a null title is rejected and the playlist is unchanged.
     */
    @Test
    public void testAddSongNullTitle() {
        assertFalse(manager.addSong(null, "Artist", "Rock"));
        assertEquals(0, manager.viewPlaylist().size());
    }

    /**
     * Tests that a whitespace-only title is rejected and the playlist is unchanged.
     */
    @Test
    public void testAddSongBlankTitle() {
        assertFalse(manager.addSong("   ", "Artist", "Rock"));
        assertEquals(0, manager.viewPlaylist().size());
    }

    /**
     * Tests that a null genre is rejected and the playlist is unchanged.
     */
    @Test
    public void testAddSongNullGenre() {
        assertFalse(manager.addSong("Song", "Artist", null));
        assertEquals(0, manager.viewPlaylist().size());
    }

    /**
     * Tests that a whitespace-only genre is rejected and the playlist is unchanged.
     */
    @Test
    public void testAddSongBlankGenre() {
        assertFalse(manager.addSong("Song", "Artist", "  "));
        assertEquals(0, manager.viewPlaylist().size());
    }

    /**
     * Tests that a null artist is accepted and replaced with "Unknown Artist".
     */
    @Test
    public void testAddSongNullArtistUsesDefault() {
        assertTrue(manager.addSong("Song", null, "Pop"));
        assertEquals("Unknown Artist", manager.viewPlaylist().get(0).getArtist());
    }

    /**
     * Tests that a 1500-character title is trimmed to MAX_TITLE_LENGTH.
     */
    @Test
    public void testAddSongLongTitleIsTrimmed() {
        String longTitle = "a".repeat(1500);
        assertTrue(manager.addSong(longTitle, "Artist", "Pop"));
        assertEquals(PlaylistManager.MAX_TITLE_LENGTH,
                manager.viewPlaylist().get(0).getTitle().length());
    }

    /**
     * Tests that adding the same song twice is allowed (duplicates are kept).
     */
    @Test
    public void testAddSongDuplicatesAllowed() {
        assertTrue(manager.addSong("Believer", "Imagine Dragons", "Rock"));
        assertTrue(manager.addSong("Believer", "Imagine Dragons", "Rock"));
        assertEquals(2, manager.viewPlaylist().size());
    }

    // ---------- viewPlaylist ----------

    /**
     * Tests that viewing an empty playlist returns an empty list, not null.
     */
    @Test
    public void testViewPlaylistEmpty() {
        List<Song> songs = manager.viewPlaylist();
        assertNotNull(songs);
        assertTrue(songs.isEmpty());
    }

    /**
     * Tests that viewing a non-empty playlist returns songs in the order they were added.
     */
    @Test
    public void testViewPlaylistNonEmptyKeepsOrder() {
        manager.addSong("First", "A", "Rock");
        manager.addSong("Second", "B", "Pop");
        manager.addSong("Third", "C", "Country");

        List<Song> songs = manager.viewPlaylist();
        assertEquals(3, songs.size());
        assertEquals("First", songs.get(0).getTitle());
        assertEquals("Second", songs.get(1).getTitle());
        assertEquals("Third", songs.get(2).getTitle());
    }

    // ---------- removeSongByTitle ----------

    /**
     * Tests that removing a song that exists returns true and removes it.
     */
    @Test
    public void testRemoveSongByTitleExists() {
        manager.addSong("Believer", "Imagine Dragons", "Rock");
        assertTrue(manager.removeSongByTitle("Believer"));
        assertEquals(0, manager.viewPlaylist().size());
    }

    /**
     * Tests that title matching ignores upper/lower case when removing.
     */
    @Test
    public void testRemoveSongByTitleIgnoresCase() {
        manager.addSong("Believer", "Imagine Dragons", "Rock");
        assertTrue(manager.removeSongByTitle("believer"));
        assertEquals(0, manager.viewPlaylist().size());
    }

    /**
     * Tests that removing a title that is not in the playlist returns false and
     * leaves the playlist unchanged.
     */
    @Test
    public void testRemoveSongByTitleNotFound() {
        manager.addSong("Believer", "Imagine Dragons", "Rock");
        assertFalse(manager.removeSongByTitle("Missing Song"));
        assertEquals(1, manager.viewPlaylist().size());
    }

    /**
     * Tests that removing from an empty playlist returns false.
     */
    @Test
    public void testRemoveSongByTitleEmptyPlaylist() {
        assertFalse(manager.removeSongByTitle("Anything"));
    }

    /**
     * Tests that removing a null or blank title returns false and leaves the
     * playlist unchanged.
     */
    @Test
    public void testRemoveSongByTitleNullOrBlank() {
        manager.addSong("Believer", "Imagine Dragons", "Rock");
        assertFalse(manager.removeSongByTitle(null));
        assertFalse(manager.removeSongByTitle("   "));
        assertEquals(1, manager.viewPlaylist().size());
    }

    /**
     * Tests that removing a title with two duplicates removes only one copy.
     */
    @Test
    public void testRemoveSongByTitleOnlyRemovesOneDuplicate() {
        manager.addSong("Believer", "Imagine Dragons", "Rock");
        manager.addSong("Believer", "Imagine Dragons", "Rock");
        assertTrue(manager.removeSongByTitle("Believer"));
        assertEquals(1, manager.viewPlaylist().size());
    }

    // ---------- shuffleSong ----------

    /**
     * Tests that shuffling an empty playlist returns null.
     */
    @Test
    public void testShuffleSongEmpty() {
        assertNull(manager.shuffleSong());
    }

    /**
     * Tests that with one song, every shuffle returns that song.
     */
    @Test
    public void testShuffleSongSingleSong() {
        manager.addSong("Only Song", "Artist", "Pop");
        Song first = manager.shuffleSong();
        Song second = manager.shuffleSong();
        assertNotNull(first);
        assertEquals("Only Song", first.getTitle());
        assertEquals("Only Song", second.getTitle());
    }

    /**
     * Tests that shuffling a non-empty playlist always returns a song from the
     * playlist.
     */
    @Test
    public void testShuffleSongNonEmptyReturnsSongInPlaylist() {
        manager.addSong("One", "A", "Rock");
        manager.addSong("Two", "B", "Pop");
        manager.addSong("Three", "C", "Country");

        for (int i = 0; i < 20; i++) {
            Song picked = manager.shuffleSong();
            assertNotNull(picked);
            assertTrue(manager.viewPlaylist().contains(picked));
        }
    }

    /**
     * Tests that shuffling after the last song is removed returns null.
     */
    @Test
    public void testShuffleAfterRemovingAllSongs() {
        manager.addSong("One", "A", "Rock");
        manager.shuffleSong();
        manager.removeSongByTitle("One");
        assertNull(manager.shuffleSong());
    }

    // ---------- searchSongs (stretch goal) ----------

    /**
     * Tests that a partial query matches both titles and artists, in playlist
     * order.
     */
    @Test
    public void testSearchSongsMatchesTitleAndArtist() {
        manager.addSong("Believer", "Imagine Dragons", "Rock");
        manager.addSong("Halo", "Beyonce", "Pop");
        manager.addSong("Shake It Off", "Taylor Swift", "Pop");

        List<Song> results = manager.searchSongs("Be");
        assertEquals(2, results.size());
        assertEquals("Believer", results.get(0).getTitle());
        assertEquals("Halo", results.get(1).getTitle());
    }

    /**
     * Tests that search matching ignores upper/lower case.
     */
    @Test
    public void testSearchSongsIgnoresCase() {
        manager.addSong("Believer", "Imagine Dragons", "Rock");
        assertEquals(1, manager.searchSongs("bELIEV").size());
    }

    /**
     * Tests that a query with no matches returns an empty list.
     */
    @Test
    public void testSearchSongsNoMatch() {
        manager.addSong("Believer", "Imagine Dragons", "Rock");
        assertTrue(manager.searchSongs("zzz").isEmpty());
    }

    /**
     * Tests that searching an empty playlist returns an empty list.
     */
    @Test
    public void testSearchSongsEmptyPlaylist() {
        assertTrue(manager.searchSongs("be").isEmpty());
    }

    /**
     * Tests that a null or blank search query returns an empty list.
     */
    @Test
    public void testSearchSongsNullOrBlankQuery() {
        manager.addSong("Believer", "Imagine Dragons", "Rock");
        assertTrue(manager.searchSongs(null).isEmpty());
        assertTrue(manager.searchSongs("   ").isEmpty());
    }

    // ---------- getSongByGenre ----------

    /**
     * Tests that a genre with a matching song returns that song.
     */
    @Test
    public void testGetSongByGenreExists() {
        manager.addSong("Believer", "Imagine Dragons", "Rock");
        manager.addSong("Shake It Off", "Taylor Swift", "Pop");

        Song result = manager.getSongByGenre("Rock");
        assertNotNull(result);
        assertEquals("Believer", result.getTitle());
    }

    /**
     * Tests that genre matching ignores upper/lower case.
     */
    @Test
    public void testGetSongByGenreIgnoresCase() {
        manager.addSong("Believer", "Imagine Dragons", "Rock");
        Song result = manager.getSongByGenre("rOcK");
        assertNotNull(result);
        assertEquals("Rock", result.getGenre());
    }

    /**
     * Tests that with several songs in the genre, every pick has the requested
     * genre.
     */
    @Test
    public void testGetSongByGenreMultipleMatchesAllCorrectGenre() {
        manager.addSong("Song 1", "A", "Rock");
        manager.addSong("Song 2", "B", "Pop");
        manager.addSong("Song 3", "C", "Rock");

        for (int i = 0; i < 20; i++) {
            Song result = manager.getSongByGenre("Rock");
            assertNotNull(result);
            assertEquals("Rock", result.getGenre());
        }
    }

    /**
     * Tests that a genre with no matching songs returns null.
     */
    @Test
    public void testGetSongByGenreNoneFound() {
        manager.addSong("Believer", "Imagine Dragons", "Rock");
        assertNull(manager.getSongByGenre("Country"));
    }

    /**
     * Tests that looking up a genre in an empty playlist returns null.
     */
    @Test
    public void testGetSongByGenreEmptyPlaylist() {
        assertNull(manager.getSongByGenre("Rock"));
    }

    /**
     * Tests that a null or blank genre returns null.
     */
    @Test
    public void testGetSongByGenreNullOrBlank() {
        manager.addSong("Believer", "Imagine Dragons", "Rock");
        assertNull(manager.getSongByGenre(null));
        assertNull(manager.getSongByGenre("  "));
    }
}
