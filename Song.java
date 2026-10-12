import java.util.Objects; 

/**
 * Represents a single song with a title, artist, and genre.
 * Acts as a simple data container with no playlist logic.
 *
 * @author  Matthew Abramson 
 * @version October 2026
 */
public class Song {

    private String title;
    private String artist;
    private String genre;

    /**
     * Creates a new Song with the given title, artist, and genre.
     *
     * @param title  the song's title
     * @param artist the performer or band
     * @param genre  the genre of the song
     */
    public Song(String title, String artist, String genre) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
    }

    /**
     * Gets the song's title.
     *
     * @return the title of the song
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the song's artist.
     *
     * @return the artist of the song
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Gets the song's genre.
     *
     * @return the genre of the song
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Checks whether this song is equal to another object.
     * Two songs are equal if their title, artist, and genre match.
     *
     * @param o the object to compare
     * @return true if the songs are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Song)) {
            return false;
        }

        Song other = (Song) o;

        // CHANGED: Objects.equals won't crash if a field is null
        return Objects.equals(title, other.title)
            && Objects.equals(artist, other.artist)
            && Objects.equals(genre, other.genre);
    }

    /**
     * Gets a readable description of the song.
     *
     * @return the song as "title by artist (genre)"
     */
    @Override
    public String toString() { // ADDED
        return title + " by " + artist + " (" + genre + ")";
    }
}