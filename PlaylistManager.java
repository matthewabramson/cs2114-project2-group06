import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * // -------------------------------------------------------------------------
/**
 *  The songs are all stored in a LinkedChain. All of the playlist methods 
 *  live in this class and not in the LinkedChain class
 * 
 *  @author matthewabramson
 *  @version Oct 12, 2026
 */
public class PlaylistManager
{
    /**
     * The longest title that's allowed to be stored
     */
    public static final int MAX_TITLE_LENGTH = 100;

    private LinkedChain<Song> playlist; 
    private Random random;           

    /**
     * Creates an empty playlist.
     */
    public PlaylistManager()
    {
        playlist = new LinkedChain<Song>();
        random = new Random();
    }

    /**
     * Adds a song to the end of the playlist and will reject the 
     * null titles. It's O(n), because addToEnd walks the chain.
     *
     * @param title the song title
     * @param artist the singer
     * @param genre the genre name
     * @return true if the song was added
     */
    public boolean addSong(String title, String artist, String genre)
    {
        if (isBlank(title) || artist == null || genre == null)
        {
            return false;
        }
        String goodTitle = title.trim();
        if (goodTitle.length() > MAX_TITLE_LENGTH)
        {
            goodTitle = goodTitle.substring(0, MAX_TITLE_LENGTH);
        }
        return playlist.addToEnd(new Song(goodTitle, artist, genre));
    }

    /**
     * Returns every song in the playlist in the order that it's in 
     * the playlist - O(n).
     *
     * @return a list of the songs
     */
    public List<Song> viewPlaylist()
    {
        List<Song> result = new ArrayList<Song>();
        Song[] songs = toSongArray();
        for (int i = 0; i < songs.length; i++)
        {
            result.add(songs[i]);
        }
        return result;
    }

    /**
     * Removes the song that matches the title inputed by user - O(n).
     *
     * @param title to remove
     * @return true if a song was removed, false if not 
     */
    public boolean removeSongByTitle(String title)
    {
        if (isBlank(title))
        {
            return false;
        }
        String wantedSong = title.trim();
        Song[] songs = toSongArray();
        for (int i = 0; i < songs.length; i++)
        {
            if (songs[i].getTitle().equalsIgnoreCase(wantedSong))
            {
                return playlist.remove(songs[i]);
            }
        }
        return false;
    }

    /**
     * Picks a random song from the playlist - O(n).
     *
     * @return a random song, or null if the playlist is empty
     */
    public Song shuffleSong()
    {
        if (playlist.isEmpty())
        {
            return null;
        }
        return playlist.get(random.nextInt(playlist.size()));
    }

    /**
     * Picks a random song based on the genre - O(n).
     *
     * @param genre to look for
     * @return a song in that genre, or null if cases don't pass 
     */
    public Song getSongByGenre(String genre)
    {
        if (isBlank(genre))
        {
            return null;
        }
        
        String wantedGenre = genre.trim();
        List<Song> matches = new ArrayList<Song>();
        Song[] songs = toSongArray();
        
        for (int i = 0; i < songs.length; i++)
        {
            if (songs[i].getGenre().equalsIgnoreCase(wantedGenre))
            {
                matches.add(songs[i]);
            }
        }
        if (matches.isEmpty())
        {
            return null;
        }
        return matches.get(random.nextInt(matches.size()));
    }

    /**
     * Gets the number of songs in the playlist - O(1).
     *
     * @return the number of songs
     */
    public int size()
    {
        return playlist.size();
    }

    /**
     * Copies the playlist into an array
     *
     * @return an array holding every song
     */
    private Song[] toSongArray()
    {
        return playlist.toArray(new Song[playlist.size()]);
    }

    /**
     * Tests for null, empty, or whitespace-only text.
     *
     * @param text to check
     * @return true if the text has no visible characters
     */
    private boolean isBlank(String text)
    {
        return text == null || text.trim().isEmpty();
    }
}