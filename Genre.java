package arraystack;
// Virginia Tech Honor Code Pledge:
//
// As a Hokie, I will conduct myself with honor and integrity at all times.
// I will not lie, cheat, or steal, nor will I accept the actions of those 
// who do.
// -- Armaan Ali (amali052024)
//
// LLM Statement:
// I have not used any assistance for the assignment beyond course 
// resources and staff.

/**
 *  This is the Genre Class for the MusicMate application.
 * 
 *  @author Armaan
 *  @version Oct 12, 2026
 */
public class Genre
{

    // ----------------------------------------------------------
    /**
     * Create a new Genre object.
     */
    public Genre()
    {
        // TODO Auto-generated constructor stub
    }
    //~ Fields ................................................................
    
    private String name;
    //~ Constructors ..........................................................
    // ----------------------------------------------------------
    /**
     * Create a new Genre object.
     * @param name
     */
    public Genre(String name)
    {
        this.setName(name);
    }
    //~Public  Methods ........................................................
    // ----------------------------------------------------------
    /**
     * This gets the name for the Genre.
     * @return name for the Genre
     */
    public String getName()
    {
        return name;
    }
    // ----------------------------------------------------------
    /**
     * This sets the name for the Genre. 
     * @param name sets the name for the Genre.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Two Genres are equal if they both have the same class.
     *
     * @param o
     *            the other object to compare to this
     *
     * @return {@code true}, if the genres are equal; {@code false} otherwise.
     */
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass().equals(o.getClass())){
            return true;
        }
        return false;
    }
}
