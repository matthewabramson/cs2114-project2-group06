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

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 *  This tests the methods for the Genre Class. 
 * 
 *  @author Armaan
 *  @version Oct 12, 2026
 */
public class GenreTest
{
    private String name;
    private Genre genretest;
    //~ Fields ................................................................


    /**
     * Test method for Genre Get name.
     */
    @Test
    public void testGetName()
    {
        name = "pop";
        genretest.setName(name);
        assertEquals("pop", genretest.getName());
    }


    /**
     * Test method for Genre set name.
     */
    @Test
    public void testSetName()
    {
        name = "pop";
        assertEquals("pop", genretest.getName());
        genretest.setName("log");
        assertEquals("log", genretest.getName());
    }


    /**
     * Test method for Genre equals object.
     */
    @Test
    public void testEquals()
    {
        assertEquals(true, genretest.equals(genretest));
        assertEquals(false, genretest.equals(null));
        String brick = "thisIsincorrect";
        assertEquals(false, genretest.equals(brick));
    }

    //~ Constructors ..........................................................

    //~Public  Methods ........................................................

}
