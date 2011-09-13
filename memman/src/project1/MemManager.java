import java.util.HashMap;


// -------------------------------------------------------------------------
/**
 *  This class will be represent our memory manager, handling any memory tasks
 *  and freeBlockList modifications.
 *
 *  @author Xavier Seymour xaviers3
 *  @version Sept 5, 2011
 */
public class MemManager
{

    //The memory pool in which we will store our data
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

    private String                                      caller;


    // constructor
    // ----------------------------------------------------------
    /**
     * Creates a new MemManager object.
     * @param poolSize The size of the memory pool we will work on in number of
     * bytes.
     */
    public MemManager( int poolSize )
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


    /**
     * Insert a record into the memory pool and return its position handle.
     * @param space The record to be inserted
     * @param size The size of the record in bytes that is being inserted
     * @return position The position handle of the record. The first byte position the
     * record was placed
     */
    public int insert( byte[] space, int size )
    {
        caller = "insert";
        int position = findMemory( size );

        // If position = -1, then there is no available freeBlock large enough
        // to
        // Accommodate the space.
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




    /**
     * Find smallest memory location that the record can fit in. Return the byte
     *  position
     * @param size The size of the record in question
     * @return position The starting byte position within the memory pool which will
     * house the record
     */
    public int findMemory( int size )
    {
        // represents the smallest available freeBlock within out freeBlock list
        // that will accommodate the size of the entry.
        int smallestFreeBlock = poolSize + 1;
        int position = -1;
        freeBlockList.moveToStart();
        freeBlockList.next();
        while ( !freeBlockList.isAtEnd() )
        {
            Object[] keyArray =
                freeBlockList.getCurrentElement().keySet().toArray();
            Integer key = (Integer)keyArray[0];

            if ( ( freeBlockList.getCurrentElement().get( key ) < smallestFreeBlock )
                && ( freeBlockList.getCurrentElement().get( key ) >= size ) )
            {
                position = key;
                smallestFreeBlock = freeBlockList.getCurrentElement().get( key );
            }
            freeBlockList.next();
        }

        return position;

    }


    // ----------------------------------------------------------
    /**
     * Update the freeBlockList after modifying the memory pool
     * @param position The record inserted or removed bte position
     * @param size The size of the record
     */
    public void updateFreeBlockList( int position, int size )
    {
        int key = findBlock( position );
        // If the inserted data's size takes up the entire freeBlock, remove
        // that
        // freeBlock from the freeBlockList. Otherwise, shorten the current
        // freeBlock
        // accordingly and update that blocks position and size
        if ( size == freeBlockList.getCurrentElement().get( key ) )
        {
            freeBlockList.removeCurrent();
        }
        else
        {
            int freeBlockSize = freeBlockList.getCurrentElement().get( key );
            freeBlockList.getCurrentElement().remove( key );
            freeBlockList.getCurrentElement().put(
                position + size,
                freeBlockSize - size );
        }
    }


    // Free a block at the position specified by the Handle.
    // Merge adjacent free blocks.
    // ----------------------------------------------------------
    /**
     * Free a block at the position specified by the Handle.
     *  Merge adjacent free blocks.
     * @param position The position of the record to be removed
     */
    public void remove( int position )
    {
        caller = "remove";
        byte byteSize = memoryPool[position];
        Integer size = (int)byteSize;
        // remove the record starting at the given position
        for ( int i = 0; i < size; i++ )
        {
            memoryPool[position + i] = 0;
        }

        findBlock( position );
        freeBlock = new HashMap<Integer, Integer>( 1 );
        freeBlock.put( position, size );
        freeBlockList.add( freeBlock );
        Object[] keyArray =
            freeBlockList.getCurrentElement().keySet().toArray();
        int key = (Integer)keyArray[0];

        caller = "check";
        int currentValue = freeBlockList.getCurrentElement().get( key );
        int mergePoint1 = position + currentValue;
        int rightKeyMatch = findBlock( mergePoint1 );

        int mergePoint2 = position;
        int leftKeyMatch = -1;
        int priorValue = -1;
        int key2 = -1;
        if ( 1==1)
        {
            findBlock(mergePoint2);
            freeBlockList.previous();
            if ( !( freeBlockList.getCurrent().equals( freeBlockList.getHead() ) )
                && !( freeBlockList.getCurrent().equals( freeBlockList.getTail() ) ) &&
                !( freeBlockList.getCurrent().equals( null )))
            {
                Object[] keyArray2 =
                    freeBlockList.getCurrentElement().keySet().toArray();
                key2 = (Integer)keyArray2[0];
                priorValue = freeBlockList.getCurrentElement().get( key2 );
                leftKeyMatch = key2 + priorValue;
            }

        }

        if ( rightKeyMatch == mergePoint1 )
        {
            findBlock(mergePoint1);
            freeBlockList.previous();
            freeBlockList.removeCurrent();
            int rightValue =
                freeBlockList.getCurrentElement().get( rightKeyMatch );

            freeBlockList.getCurrentElement().clear();
            freeBlockList.getCurrentElement().put(
                position,
                currentValue + rightValue );
            //if()
        }
        if(leftKeyMatch == mergePoint2) {
            findBlock(mergePoint2);
            freeBlockList.previous();
            freeBlockList.removeCurrent();
            int rightValue =
                freeBlockList.getCurrentElement().get(leftKeyMatch);

            freeBlockList.getCurrentElement().clear();
            freeBlockList.getCurrentElement().put(
                key2,
                priorValue + rightValue );
        }

    }


    /**
     * If the caller of this method is insert, finds the freeBlock whose key
     * matches the position in question and sets the 'current' field of the
     * freeBlockList to point to that freeBlock If the caller of this method is
     * remove, finds the freeBlock whose key is just larger than the position in
     * question and sets the 'current' field of the freeBlockList to point to
     * that freeBlock. This will allow freeBlock positions to be ordered
     * (calling next on the freeBlockList will return the freeBlock whose
     * position value is just larger than the current freeBlock)
     *
     * @param position
     *            The starting position of the space
     * @return the key of the current freeBlock
     */
    public int findBlock( int position )
    {

        freeBlockList.moveToStart();
        freeBlockList.next();
        Integer key = -1;
        while ( !freeBlockList.isAtEnd() )
        {
            Object[] keyArray =
                freeBlockList.getCurrentElement().keySet().toArray();
            key = (Integer)keyArray[0];
            if ( caller.equals( "insert" ) || caller.equals( "check" ) )
            {
                if ( key == position )
                {
                    break;
                }

            }
            if ( caller.equals( "remove" ) )
            {
                if ( key > position )
                {
                    break;
                }
            }
            freeBlockList.next();
        }

        return key;
    }

    // ----------------------------------------------------------
    /**
     * @return the memoryPool
     */
    public byte[] getMemoryPool()
    {
        return memoryPool;
    }


    // ----------------------------------------------------------
    /**
     * @return the freeBlockList
     */
    public TwoWayLinkedList<HashMap<Integer, Integer>> getFreeBlockList()
    {
        return freeBlockList;
    }


    // ----------------------------------------------------------
    /**
     * @return the poolSize
     */
    public int getPoolSize()
    {

        return poolSize;

    }

}
