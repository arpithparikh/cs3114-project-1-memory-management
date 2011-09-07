package project1;

import java.util.HashMap;

public class MemManager {
	
		private byte[] memoryPool;
	
		// constructor
		MemManager(int poolSize) {
			
			 memoryPool = new byte[(int)poolSize];
		
		}
		
		// Insert a record and return its position handle.
		// space contains the record to be inserted, of length size.
		public int insert(byte[] space, int size)
		{
			int position = findMemory();
			
			// insert record at position
			for (int i = 0; i < size; i++)
			{
				memoryPool[position+i] = space[i];
			}
			
			return position;
		}
		
		/*
		// Free a block at posHandle. Merge adjacent blocks if appropriate.
		void remove(Handle theHandle);
		// Return the record with handle posHandle, up to size bytes.
		// Place the record into space.
		void get(byte[] space, Handle theHandle, int size);
		
			*/
		public int findMemory()
		{
			int pos = 0;
			
			
			return pos;
			
		}
		
	
		
		

}
