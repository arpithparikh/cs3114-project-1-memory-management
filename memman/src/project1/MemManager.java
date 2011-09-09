import java.util.HashMap;


public class MemManager
{

    private byte[]                                      memoryPool;

    // The linkedList will consist of HashMaps which will store free space
    // information
    private TwoWayLinkedList<HashMap<Integer, Integer>> freeBlockList;

    // This HashMap will store two integers. The key will be the byte position
    // within the memory pool which points to the starting byte of the free
    // space.
    // The value will be the size of the free block, or the number of bytes it
    // consists of.
    private HashMap<Integer, Integer>                   freeBlock;

    private int                                         poolSize;


    // constructor
    MemManager( int poolSize )
    {

        this.poolSize = poolSize;
        memoryPool = new byte[poolSize];
        // creates a twoWayLinkedList that will store HashMaps defining the
        // blocks
        // of free space within our pool
        freeBlock = new HashMap<Integer, Integer>( 1 );
        freeBlock.put( 0, poolSize );
        freeBlockList = new TwoWayLinkedList<HashMap<Integer, Integer>>();
        // Initially adds the entire memory
        freeBlockList.add( freeBlock );


    }


    // Insert a record and return its position handle.
    // space contains the record to be inserted, of length size.
    public int insert( byte[] space, int size )
    {
        int position = findMemory( size );

        //If position = -1, then there is no available freeBlock large enough to
        //Accommodate the space.
        if ( !( position == -1 ) )
        {
            // insert record at position
            for ( int i = 0; i < size; i++ )
            {
                memoryPool[position + i] = space[i];
            }

            // update the freeBlockList to match the updated memoryPool.
            updateFreeBlockList( position, size );
        }
        return position;
    }


    /*
     * // Free a block at posHandle. Merge adjacent blocks if appropriate. void
     * remove(Handle theHandle); // Return the record with handle posHandle, up
     * to size bytes. // Place the record into space. void get(byte[] space,
     * Handle theHandle, int size);
     */
    // Find smallest memory location that the record can fit in. Return the byte
    // position
    public int findMemory( int size )
    {
        // represents the smallest available freeBlock within out freeBlock list
        // that will accommodate the size of the entry.
        int smallestfreeBlock = poolSize;
        int position = -1;
        freeBlockList.moveToStart();
        freeBlockList.next();
        while ( !freeBlockList.isAtEnd() )
        {
            Integer[] keyArray =
                (Integer[])freeBlockList.getCurrentElement().keySet().toArray();
            Integer key = keyArray[0];

            if ( ( freeBlockList.getCurrentElement().get( key ) < smallestfreeBlock )
                && ( freeBlockList.getCurrentElement().get( key ) >= size ) )
            {
                position = key;
            }
            freeBlockList.next();
        }

        return position;

    }


    public void updateFreeBlockList( int position, int size )
    {
        freeBlockList.moveToStart();
        freeBlockList.next();
        Integer key = -1;
        while ( !freeBlockList.isAtEnd() )
        {
            Integer[] keyArray =
                (Integer[])freeBlockList.getCurrentElement().keySet().toArray();
            key = keyArray[0];
            if(key == position) {
                break;
            }
            freeBlockList.next();
        }
        //If the inserted data's size takes up the entire freeBlock, remove that
        //freeBlock from the freeBlockList. Otherwise, shorten the current freeBlock
        //accordingly and update that blocks position and size
        if(size == freeBlockList.getCurrentElement().get( key )) {
            freeBlockList.removeCurrent();
        }else {
            int freeBlockSize = freeBlockList.getCurrentElement().get( key );
            freeBlockList.getCurrentElement().remove( key );
            freeBlockList.getCurrentElement().put( size, freeBlockSize-size );
        }
    }

}
