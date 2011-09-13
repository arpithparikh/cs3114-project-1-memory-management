package project1;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class memman
{

    public static void main( String[] args )
        throws IOException
    {
        int poolSize = 0;
        int numRecs = 0;
        File commandFile = null;
        HashMap<Integer, Integer> positionMap;
        TxtScanner scanner;
        ArrayList<String> commands;

        // Parse the command line arguments
        if ( args.length == 3 ) // Command argument size must be 3
        {
            try
            {
                poolSize = Integer.parseInt( args[0] );
                numRecs = Integer.parseInt( args[1] );
            }
            catch ( NumberFormatException e )
            {
                System.err.println( "First argument must be an integer" );
                System.exit( 1 );
            }
            commandFile = new File( args[2] );
            scanner = new TxtScanner( commandFile );

        }
        else
        {
            System.err.println( "Usage: java memman <pool-size> <num-recs> <command-file>" );
            System.exit( 1 );
        }

        MemManager myMemoryManager = new MemManager( poolSize );
        System.out.println( "freeBlockList Size at initialization: "
            + myMemoryManager.getFreeBlockList().size() );

        // only 1 freeBlock at start
        System.out.println( "freeBlockContent: " );

        positionMap = new HashMap<Integer, Integer>( numRecs );
        scanner = new TxtScanner( commandFile ); // Parse Commands from File

        commands = scanner.getCommands(); // Parse commands into a String Array
        // List

        for ( int i = 0; i < commands.size(); i++ )
        {
            String[] currCommand = commands.get( i ).split( "\\s+" );
            // System.out.println( currCommand[0] );

            if ( currCommand[0].equals( "" ) )
            {
                String[] newCommand = new String[currCommand.length - 1];
                if ( newCommand.length > 0 )
                {
                    System.arraycopy(
                        currCommand,
                        1,
                        newCommand,
                        0,
                        currCommand.length - 1 );
                    currCommand = newCommand;
                }
            }

            int recNum;
            int positionHandle;
            // // Convert City Info into ByteArray /////
            byte[] newRecord;
            int newRecordSize;
            // // The print method passes -1 if print all is the command. It
            // passes the record handle if a specific record needs to be
            // printed.
            if ( currCommand[0].equals( "insert" ) )
            {
                recNum = Integer.parseInt( currCommand[1] );
                boolean recordCanReplace = true;
                // check if the recNum is valid
                if ( recNum >= 0 && recNum <= numRecs - 1 )
                {
                    newRecord =
                        toByteArray(
                            currCommand[2],
                            currCommand[3],
                            currCommand[4] );
                    newRecordSize = newRecord[0];

                    // check if the recNum given already exists. If it does
                    // remove the old
                    // record and replace with this new record.
                    Object[] keys = positionMap.keySet().toArray();
                    for ( int j = 0; j < keys.length; j++ )
                    {
                        if ( recNum == (Integer)keys[j] )
                        {
                            myMemoryManager.remove( positionMap.get( keys[j] ) );
                            positionMap.remove( keys[j] );
                            break;
                        }
                    }
                    if ( recordCanReplace )
                    {
                        positionHandle =
                            myMemoryManager.insert( newRecord, newRecordSize );
                        if ( positionHandle != -1 )
                        {
                            positionMap.put( recNum, positionHandle );
                        }
                        else
                        {
                            System.err.println( "Record is too large to fit in available space" );
                        }
                    }
                    else
                    {
                        System.err.println( "Record is too large to overwrite record handle "
                            + recNum );
                    }
                }
                else
                {
                    System.err.println( "Valid record Numbers range from 0 to "
                        + ( numRecs - 1 ) );
                }

            }
            else if ( currCommand[0].equals( "remove" ) )
            {
                // which record number do we want to remove? Lets get the handle
                // for the record
                int recToBeRemoved = Integer.parseInt( currCommand[1] );
                if ( recToBeRemoved >= 0 && recToBeRemoved <= numRecs - 1 )
                {
                    // check if the recordToBeRemoved exists. If it does
                    // remove the record at that recNum, otherwise print a
                    // message
                    Object[] keys = positionMap.keySet().toArray();
                    for ( int j = 0; j < keys.length; j++ )
                    {
                        if ( recToBeRemoved == (Integer)keys[j] )
                        {
                            myMemoryManager.remove( positionMap.get( keys[j] ) );
                            positionMap.remove( keys[j] );
                            break;
                        }
                        else if ( j == ( keys.length - 1 ) )
                        {
                            System.err.println( "The record inputted does not exist."
                                + " Please check to make sure you've selected the"
                                + " proper record for removal" );
                        }
                    }

                }
                else
                {
                    System.err.println( "Valid record Numbers range from 0 to "
                        + ( numRecs - 1 ) );
                }
            }
            else if ( currCommand[0].equals( "print" ) )
            {
                /**
                 * int posHandle; // Position where the data is store in the
                 * memory // pool if ( currCommand[1].isEmpty() ) { posHandle =
                 * -1; // Let's define -1 to mean "Print All" } else // If we
                 * need to print a specific record { posHandle =
                 * positionMap.get( currCommand[1] ); // Let's look // up the //
                 * position //myMemoryManager.print( posHandle ); }
                 */
            	if(currCommand.length == 1)  // 
            	{
            		//Then print all records. 
            		for(int k =0; k < positionMap.size(); k++)
            		{
            			if (positionMap.containsKey(k))
            			{
            			//	System.out.println(myMemoryManager.readRecord(positionMap.get(k)).toString());
            			}
            		}
            	} else if (currCommand.length == 2){
            		// Print specific record
            	//	System.out.println(myMemoryManager.readRecord(positionMap.get(currCommand[1])).toString());
            	}
            }
            else
            {
            	//System.err.println( "Invalid command. Command can only be insert, remove or print");

            }

        }

        for ( int i = 1; i < myMemoryManager.getFreeBlockList().size() + 1; i++ )
        {
            Object[] keyArray =
                myMemoryManager.getFreeBlockList()
                    .getCurrentElement()
                    .keySet()
                    .toArray();
            Integer key = (Integer)keyArray[0];
            System.out.println( i
                + ".) "
                + "position: "
                + key
                + ", Size: "
                + myMemoryManager.getFreeBlockList().getCurrentElement().get(
                    key ) );
            myMemoryManager.getFreeBlockList().next();
        }

    }


    public static byte getLength( byte[] array )
    {
        byte l = (byte)array.length;
        l = (byte)( l + 1 );
        return (byte)array.length;
    }


    public static byte[] toByteArray( String x, String y, String city )
        throws IOException
    {

        String dataString = x + " " + " " + y + " " + city; // concatenate all data elements
        System.out.println(dataString);

        byte[] dataByteArray = dataString.getBytes(); // Encode dataString into
        System.out.println(dataByteArray);
        String value = new String(dataByteArray);
        
        System.out.println(value);
        byte[] recordArray = new byte[dataByteArray.length + 1];
        recordArray[0] = (byte)( getLength( dataByteArray ) + 1 );

        System.arraycopy(
            dataByteArray,
            0,
            recordArray,
            1,
            dataByteArray.length );
        return recordArray;

    }

}
