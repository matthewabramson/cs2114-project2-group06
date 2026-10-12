import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * PlaylistManager is the "brains" of MusicMate.
 * It stores songs in a LinkedChain<Song> and enforces all playlist validation.
 * The UI (Main) never touches the LinkedChain directly.
 *
 * REQUIREMENT: LinkedChain<T> must implement Iterable<T> so this class can
 * walk the chain (needed for viewPlaylist, removeSongByTitle, getSongByGenre).
 *
 * @author Archer Kim
 * @version 1.1
 */
public class PlaylistManager {

    /** Titles longer than this are trimmed (bad-input case: 1000+ chars). */
    public static final int MAX_TITLE_LENGTH = 100;

    /** How many times shuffle may re-roll to avoid repeating the last song. */
    public static final int MAX_REROLLS = 5;

    private static final String UNKNOWN_ARTIST = "Unknown Artist";

    private final LinkedChain<Song> playlist;
    private final Random random;
    private Song lastPlayed; // so shuffle doesn't repeat the same song back-to-back

    /**
     * Creates an empty playlist.
     */
    public PlaylistManager() {
        this.playlist = new LinkedChain<>();
        this.random = new Random();
        this.lastPlayed = null;
    }

    /**
     * Adds a song to the playlist.
     * Validation: rejects null/empty/blank title or genre; trims long titles.
     * Duplicates are allowed (a user may add the same favorite twice).
     * Big-O: O(1)
     *
     * @param title  the song title
     * @param artist the artist (null/blank becomes "Unknown Artist")
     * @param genre  the genre
     * @return true if added, false if rejected (UI shows "Please enter a valid title.")
     */
    public boolean addSong(String title, String artist, String genre) {
        if (isBlank(title) || isBlank(genre)) {
            return false;
        }

        String cleanTitle = title.trim();
        if (cleanTitle.length() > MAX_TITLE_LENGTH) {
            cleanTitle = cleanTitle.substring(0, MAX_TITLE_LENGTH);
        }

        String cleanArtist = isBlank(artist) ? UNKNOWN_ARTIST : artist.trim();

        playlist.add(new Song(cleanTitle, cleanArtist, genre.trim()));
        return true;
    }

    /**
     * Returns every song as a List, in the order they were added.
     * Empty playlist returns an empty list (never null).
     * Big-O: O(n)
     *
     * @return a new list of the songs in the playlist
     */
    public List<Song> viewPlaylist() {
        List<Song> songs = new ArrayList<>();
        for (Song song : playlist) {
            songs.add(song);
        }
        return songs;
    }

    /**
     * Removes the first song whose title matches (case-insensitive).
     * Big-O: O(n)
     *
     * @param title the title to remove
     * @return true if removed; false if the title is null/blank, the playlist
     *         is empty, or nothing matches (playlist stays unchanged)
     */
    public boolean removeSongByTitle(String title) {
        if (isBlank(title) || playlist.isEmpty()) {
            return false;
        }

        String target = title.trim();
        Song match = null;
        for (Song song : playlist) {
            if (song.getTitle().equalsIgnoreCase(target)) {
                match = song;
                break;
            }
        }

        if (match == null) {
            return false;
        }

        boolean removed = playlist.remove(match);
        if (removed && match.equals(lastPlayed) && !playlist.contains(match)) {
            lastPlayed = null;
        }
        return removed;
    }

    /**
     * Picks a random song from the playlist. If there is more than one song,
     * it re-rolls up to MAX_REROLLS times to avoid repeating the last song.
     * Big-O: O(n) per roll (LinkedChain.getRandom walks to a random node)
     *
     * @return a random Song, or null if the playlist is empty
     *         (UI shows "Playlist is empty.")
     */
    public Song shuffleSong() {
        if (playlist.isEmpty()) {
            return null;
        }

        Song pick = playlist.getRandom();
        int rerolls = 0;
        while (playlist.getSize() > 1 && pick != null
                && pick.equals(lastPlayed) && rerolls < MAX_REROLLS) {
            pick = playlist.getRandom();
            rerolls++;
        }

        lastPlayed = pick;
        return pick;
    }

    /**
     * Returns a random song from the playlist that matches the genre
     * (case-insensitive). The UI validates that the genre is a real genre
     * BEFORE calling this method.
     * Big-O: O(n)
     *
     * @param genre the genre to look for
     * @return a matching Song, or null if none match
     *         (UI shows "No songs in that genre yet.")
     */
    public Song getSongByGenre(String genre) {
        if (isBlank(genre) || playlist.isEmpty()) {
            return null;
        }

        String target = genre.trim();
        List<Song> matches = new ArrayList<>();
        for (Song song : playlist) {
            if (song.getGenre().equalsIgnoreCase(target)) {
                matches.add(song);
            }
        }

        if (matches.isEmpty()) {
            return null;
        }
        return matches.get(random.nextInt(matches.size()));
    }

    /**
     * STRETCH GOAL: finds every song whose title or artist contains the
     * search text (case-insensitive). For example, "be" would match
     * "Believer" and an artist named "Beyonce".
     * Big-O: O(n)
     *
     * @param query the partial title or artist text to look for
     * @return a new list of matching songs in playlist order; empty if the
     *         query is null/blank or nothing matches (never null)
     */
    public List<Song> searchSongs(String query) {
        List<Song> results = new ArrayList<>();
        if (isBlank(query)) {
            return results;
        }

        String target = query.trim().toLowerCase();
        for (Song song : playlist) {
            if (song.getTitle().toLowerCase().contains(target)
                    || song.getArtist().toLowerCase().contains(target)) {
                results.add(song);
            }
        }
        return results;
    }

    /**
     * Checks whether a string is null, empty, or only whitespace.
     *
     * @param text the string to check
     * @return true if the string has no usable characters
     */
    private static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}
