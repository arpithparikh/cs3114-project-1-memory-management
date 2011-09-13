import junit.framework.TestCase;


// -------------------------------------------------------------------------
/**
 *  Tests the TwoWayLinkedList
 *
 *  @author Xavier Seymour
 *  @version Sep 10, 2011
 */
public class TwoWayLinkedListTest
    extends TestCase
{
    private TwoWayLinkedList<Integer> list;

    protected void setUp()
        throws Exception
    {
        super.setUp();
        list = new TwoWayLinkedList<Integer>();
    }

    /**
     * Tests the constructor of class CircularTwoWayLinkedList.
     */
    public void testTwoWayLinkedList()
    {
        assertSame(list.size() , 0);
        assertNull(list.getCurrentElement());
    }


    /**
     * Tests the add method of class CircularTwoWayLinkedList.
     */
    public void testAdd()
    {
        assertSame(0, list.size());
        list.add( 1 );
        assertSame(1, list.size());
        assertSame( 1 , list.getCurrentElement());
        list.moveToStart();
        assertSame(list.getCurrent(), list.getHead());
        list.next();
        assertSame( 1 , list.getCurrentElement());
        list.next();
        assertSame(list.getCurrent(), list.getTail());
        list.previous();
        assertSame( 1, list.getCurrentElement());
        list.previous();
        assertSame(list.getCurrent(), list.getHead());
        list.next();
        list.add( 2 );
        assertSame( 2 , list.size());
        assertSame( 2 , list.getCurrentElement());
        list.next();
        assertSame( 1 , list.getCurrentElement());
        list.previous();
        assertSame( 2 , list.getCurrentElement());
        list.add( 3 );
        assertSame( 3 , list.size());
        assertSame( 3 , list.getCurrentElement());
        list.next();
        assertSame( 2 , list.getCurrentElement());
        list.moveToStart();
        list.add( 4 );
        list.previous();
        assertSame(list.getCurrent(), list.getHead());
        list.next();
        list.next();
        assertSame(3, list.getCurrentElement());

    }

    /**
     * Tests the isEmpty method of class CircularTwoWayLinkedList.
     */
    public void testIsEmpty()
    {
        assertTrue(list.isEmpty());
        list.add( 1 );
        assertFalse(list.isEmpty());
    }


    /**
     * Tests the makeEmpty method of class CircularTwoWayLinkedList.
     */
    public void testMakeEmpty()
    {
        list.add( 10 );
        list.add( 5 );
        assertFalse(list.isEmpty());
        list.makeEmpty();
        assertTrue(list.isEmpty());
        assertSame(0 , list.size());
    }


    /**
     * Tests the next method of class CircularTwoWayLinkedList.
     */
    public void testNext()
    {
        list.add( 3 );
        list.add( 2 );
        list.add( 1 );
        list.next();
        assertSame(2, list.getCurrentElement());
        list.next();
        assertSame(3, list.getCurrentElement());
        list.next();
        assertSame(list.getCurrent(), list.getTail());

    }


    /**
     * Tests the previous method of class CircularTwoWayLinkedList.
     */
    public void testPrevious()
    {
        assertSame(0, list.size());
        list.add( 3 );
        list.add( 2 );
        list.add( 1 );
        list.previous();
        assertSame(list.getCurrent(), list.getHead());


    }


    /**
     * Tests theremoveCurrent method of class CircularTwoWayLinkedList.
     */
    public void testRemoveCurrent()
    {
        list.add( 3 );
        list.add( 2 );
        list.add( 1 );
        list.next();
        assertSame( 2, list.getCurrentElement());
        list.removeCurrent();
        assertSame( 3 , list.getCurrentElement());
        assertSame( 2 , list.size());
        list.next();
        assertSame(list.getCurrent(), list.getTail());
        list.removeCurrent();
        assertSame( list.getCurrent(), list.getTail());
        assertSame( 2 , list.size());
        list.previous();
        assertSame( 3 , list.getCurrentElement());
        list.previous();
        assertSame( 1 , list.getCurrentElement());
        list.previous();
        assertSame( list.getCurrent(), list.getHead());
        list.removeCurrent();
        assertSame( list.getCurrent(), list.getHead());
        list.next();
        list.removeCurrent();
        assertSame( 3 , list.getCurrentElement());
        list.removeCurrent();
        assertSame( 0 , list.size());
        assertSame(list.getCurrent(), list.getHead());

    }


}
