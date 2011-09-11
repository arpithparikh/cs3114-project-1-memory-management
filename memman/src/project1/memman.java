package project1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class memman {
	
	public static void main(String[] args) throws IOException {
		int poolSize = 0;
		int numRecs = 0;
		File commandFile = null;
		HashMap<Integer, Integer> positionMap;
		TxtScanner scanner;
		ArrayList<String> commands;
		
		// Parse the command line arguments
		if (args.length == 3)	// Command argument size must be 3
		{
			try {
				poolSize = Integer.parseInt(args[0]);	
				numRecs = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				System.err.println("First argument must be an integer");
		        System.exit(1);
			}
			commandFile = new File (args[2]);
			scanner = new TxtScanner(commandFile);
		
			
		} else
		{
			System.err.println("Usage: java memman <pool-size> <num-recs> <command-file>");
	        System.exit(1);	
		}
		
		MemManager myMemoryManager = new MemManager(poolSize);
		positionMap = new HashMap<Integer, Integer>(numRecs);
		scanner = new TxtScanner(commandFile);	// Parse Commands from File
		
		commands = scanner.getCommands();	// Parse commands into a String Array List
		
		for (int i = 0; i < commands.size(); i++)
		{
			String[] currCommand = commands.get(i).split("\\s+");
			System.out.println(currCommand[0]);

			int recNum;
			int positionHandle;
			
			//// Convert City Info into ByteArray /////		
			byte[] newRecord;
			int newRecordSize;
			
			
			//// The print method passes -1 if print all is the command. It passes the record handle if a specific record needs to be printed. 
			if(currCommand[0].equals("insert"))
			{
				newRecord = toByteArray(currCommand[2], currCommand[3], currCommand[4]);
				newRecordSize = newRecord[0];
				recNum = Integer.parseInt(currCommand[1]);
				positionHandle = myMemoryManager.insert(newRecord, newRecordSize);
				positionMap.put(recNum, positionHandle);
				
			} else if (currCommand[0].equals("remove"))
			{
				int recToBeRemoved = Integer.parseInt(currCommand[1]); // Which record number do we want to remove?
				int recordHandle = positionMap.get(recToBeRemoved);		// Let's get the handle for the record
				myMemoryManager.remove(recordHandle);
				
			} else if (currCommand[0].equals("print"))
			{
				int posHandle;	// Position where the data is store in the memory pool
				if (currCommand[1].isEmpty())
				{
					posHandle = -1;  // Let's define -1 to mean "Print All"
					
				} else  //If we need to print a specific record
				{
					posHandle = positionMap.get(currCommand[1]);  // Let's look up the position
					myMemoryManager.print(posHandle);
				}
				
			} else 
			{
				// Let's print an error message
				
			}
		}
				
	}
	
	public static byte[] toByteArray(String x, String y, String city) throws IOException
	{
		
		String dataString = x + y + city;	//concatenate all data elements 
		
	//	ByteArrayOutputStream b = new ByteArrayOutputStream();  // Create a byte array output stream object
		byte[] dataByteArray = dataString.getBytes(); 	// Encode dataString into byte array 
	//	b.write(buffer); 	// Write bytearray to the output stream
	//	byte[] dataByteArray = b.toByteArray(); 
		byte[] recordArray = null;
		recordArray[0] = (byte) dataByteArray.length;
		
		System.arraycopy(dataByteArray, 0, recordArray, 1, dataByteArray.length);
		return recordArray;
		
	}

	
}
