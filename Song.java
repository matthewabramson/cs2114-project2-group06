public class Song
{
    private String title;
    private String artist;
    private String genre;

    public Song(String title, String artist, String genre)
    {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getGenre() { return genre; }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Song other = (Song) obj;
        return title.equals(other.title) && artist.equals(other.artist)
            && genre.equals(other.genre);
    }

    @Override
    public int hashCode() { return title.hashCode(); }
}