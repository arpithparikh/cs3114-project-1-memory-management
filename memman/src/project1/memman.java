package project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;


public class memman {
	
	public static void main(String[] args) throws FileNotFoundException {
		int poolSize = 0;
		int numRecs = 0;
		File commandFile = null;
		HashMap sizeMap;
		HashMap positionMap;
		TxtScanner scanner;
		ArrayList<String> commands;
		
		// Parse the command line arguments
		if (args.length == 3)
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
		positionMap = new HashMap(numRecs);
		sizeMap = new HashMap(numRecs);
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
			
			
			//// ///////////
			if(currCommand[0].equals("insert"))
			{
				newRecord = toByteArray(currCommand[2], currCommand[3], currCommand[4]);
				newRecordSize = newRecord.length;
				recNum = Integer.parseInt(currCommand[1]);
				positionHandle = myMemoryManager.insert(newRecord, newRecordSize);
				positionMap.put(recNum, positionHandle);
				sizeMap.put(recNum, newRecordSize);		
			}
		}
		
		System.out.println(sizeMap.get(0).toString());		
		
	}
	
	
	
	
	
	public static byte[] toByteArray(String x, String y, String city)
	{
		
		String bigString = x + y + city;
		byte[] encodedData = bigString.getBytes();
		
		return encodedData;
		
	}

	
}
