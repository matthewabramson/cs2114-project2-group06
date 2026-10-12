import java.util.Scanner;

/**
 * Runs MusicMate as a text menu where the user types choices.
 *
 * @author Matthew Abramson
 * @version October 2026
 */
public class MusicMateMain
{
    private static PlaylistManager pm = new PlaylistManager();
    private static Scanner in = new Scanner(System.in);

    /**
     * Shows the menu until the user quits.
     *
     * @param args not used
     */
    public static void main(String[] args)
    {
        String choice = "";
        while (!choice.equals("6"))
        {
            System.out.println();
            System.out.println("1) Add  2) View  3) Remove  4) "
                + "Shuffle  5) Genre  6) Quit");
            choice = ask("Choice: ");

            if (choice.equals("1"))
            {
                addSong();
            }
            else if (choice.equals("2"))
            {
                viewPlaylist();
            }
            else if (choice.equals("3"))
            {
                removeSong();
            }
            else if (choice.equals("4"))
            {
                System.out.println("Now playing: " + pm.shuffleSong());
            }
            else if (choice.equals("5"))
            {
                System.out.println("Now playing: " + 
            pm.getSongByGenre(ask("Genre: ")));
            }
        }
        System.out.println("Bye!");
    }

    /**
     * Asks the user for a song and adds it
     */
    private static void addSong()
    {
        String title = ask("Title: ");
        String artist = ask("Artist: ");
        String genre = ask("Genre: ");

        if (pm.addSong(title, artist, genre))
        {
            System.out.println("Added.");
        }
        else
        {
            System.out.println("Not added: title is not found");
        }
    }

    /**
     * Prints every song in the playlist
     */
    private static void viewPlaylist()
    {
        System.out.println(pm.viewPlaylist());
    }

    /**
     * Allows the user to remove a song
     */
    private static void removeSong()
    {
        if (pm.removeSongByTitle(ask("Title to remove: ")))
        {
            System.out.println("Removed");
        }
        else
        {
            System.out.println("No song with that title");
        }
    }

    /**
     * Prints a question and returns what the user types.
     *
     * @param question the text to show
     * @return the user's answer
     */
    private static String ask(String question)
    {
        System.out.print(question);
        return in.nextLine();
    }
}