package project1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


// -------------------------------------------------------------------------
/**
 *  Write a one-sentence summary of your class here.
 *  Follow it with additional details about its purpose, what abstraction
 *  it represents, and how to use it.
 *
 *  @author Xavier Seymour, Semere Sium
 *  @version Sep 6, 2011
 */
public class TxtScanner
{
    private Scanner fileScan = null;

    private ArrayList<String> commands;


    // ----------------------------------------------------------
    /**
     * Create a new TxtScanner object.
     *
     * @param f
     * @throws FileNotFoundException
     */
    public TxtScanner( File f )
        throws FileNotFoundException
    {
        commands = new ArrayList<String>();

        // scan = new Scanner( f );
        // scan.useDelimiter( " " );
        // while ( scan.hasNext() )
        // {
        // System.out.println( scan.next() );
        // }
        // this scanner will be the file scanner
        fileScan = new Scanner( f );

        // Go through each line of the text file and place into an array
        while ( fileScan.hasNextLine() )
        {
            String str = "";
            // this scanner will look at each line at a time
            {
                Scanner s = new Scanner( fileScan.nextLine() );
                s.useDelimiter( " " );
                while ( s.hasNext() )
                {
                    str = str + s.next() + " ";
                }
                commands.add( str );
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * @return the commands
     */
    public ArrayList<String> getCommands()
    {
        return commands;
    }


    // ----------------------------------------------------------
    /**
     * @param commands the commands to set
     */
    public void setCommands( ArrayList<String> commands )
    {
        this.commands = commands;
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param args
     * @throws FileNotFoundException
     */
    public static void main( String[] args )
        throws FileNotFoundException
    {
        TxtScanner scanner =
            new TxtScanner(
                new File(
                    "C:/Users/Xavier/workspaceSOPH/Project1_3114/src/p1_testData.txt" ) );
        for ( int i = 0; i < scanner.commands.size(); i++ )
        {
            System.out.println( scanner.commands.get( i ) );
        }
        System.out.println( "Size = " + scanner.commands.size() );
    }
}// end of main

