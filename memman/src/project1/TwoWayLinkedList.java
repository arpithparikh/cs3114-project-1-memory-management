/**
 * This class represents a Doubly Linked list.
 *
 * @author Xavier Seymour (xaviers3)
 * @version Sept 2, 2010
 * @param <T>
 *            The type of the object to be stored.
 */

public class TwoWayLinkedList<T>
    implements TwoWayList<T>
{
    // position where additions and removals occur.
    private ListNode<T> head;
    private ListNode<T> current;
    private ListNode<T> tail;
    private int         size;

    /**
     * Constructs an initially empty TwoWayLinkedList.
     */
    public TwoWayLinkedList()
    {
        head = new ListNode<T>( null );
        tail = new ListNode<T>( null );
        current = head;
        size = 0;
    }


    /**
     * Adds an object to the list. Inserts item just prior to the current item.
     * Inserted item becomes the new current item.
     *
     * @param value
     *            the object to add.
     */
    public void add( T value )
    {
        ListNode<T> node = new ListNode<T>( value );
        if ( size == 0 )
        {
            node.setNext( tail );
            node.setPrevious( head );
            head.setNext( node );
            tail.setPrevious( node );
        }
        else if ( size > 0 && !( current.equals( head ) ) )
        {
            // sets next link of the created node to be the current node.
            node.setNext( current );

            // sets the previous link of the created node to the previous node.
            // of the current node, if there is one
            ListNode<T> currentPrev = null;
            currentPrev = current.getPrevious();
            node.setPrevious( currentPrev );

            // sets the previous link of current to be the created node.
            current.setPrevious( node );

            // sets the next link of the previous node of current to the created
            // node.
            node.getPrevious().setNext( node );
        }
        else
        {
            // sets next link of the created node to be the current node.
            node.setNext( current.getNext() );
            node.setPrevious( current );
            current.getNext().setPrevious( node );
            current.setNext( node );
        }

        current = node;

        size++;
    }


    /**
     * Returns the object stored in the current field.
     *
     * @return The object stored in current.
     */
    public T getCurrentElement()
    {
        if ( current != null )
        {
            return current.getElement();
        }
        else
        {
            return null;
        }
    }


    /**
     * Tests if the list is empty.
     *
     * @return True if the list is empty, false otherwise.
     */
    public boolean isEmpty()
    {
        if (size == 0) {
            return true;
        }else return false;
    }


    /**
     * Make the list logically empty.
     */
    public void makeEmpty()
    {
        head = new ListNode<T>( null );
        tail = new ListNode<T>( null );
        current = head;
        size = 0;
    }


    /**
     * Gets the next item in the list, setting current to that item. Moves the
     * current position in the appropriate direction.
     */
    public void next()
    {
        //if ( !( current.equals( tail ) ) )
        //{
            ListNode<T> next = null;
            next = current.getNext();
            current = next;
        //}

    }


    // ----------------------------------------------------------
    /**
     * @return the head
     */
    public ListNode<T> getHead()
    {
        return head;
    }


    // ----------------------------------------------------------
    /**
     * @return the current
     */
    public ListNode<T> getCurrent()
    {
        return current;
    }


    // ----------------------------------------------------------
    /**
     * @return the tail
     */
    public ListNode<T> getTail()
    {
        return tail;
    }


    // ----------------------------------------------------------
    /**
     * @return the size
     */
    public int getSize()
    {
        return size;
    }


    /**
     * Gets the previous item in the list, setting current to that item. Move
     * the current position in the appropriate direction
     */
    public void previous()
    {
        if ( !( current.equals( head ) ) )
        {
            ListNode<T> previous = null;
            previous = current.getPrevious();
            current = previous;
        }
    }


    /**
     * Removes the item in current from the list. The item following it becomes
     * the new current item.
     */
    public void removeCurrent()
    {
        if ( size > 0 && !( current.equals( head ) )
            && !( current.equals( tail ) ) )
        {
            // sets the node (before current) next link to currents next link.
            // Effectively bypassing current.
            ListNode<T> currentPrev = null;
            currentPrev = current.getPrevious();
            currentPrev.setNext( current.getNext() );

            // sets the node (after current) previous link to currents previous
            // link. Effectively bypassing current.
            ListNode<T> currentNext = null;
            currentNext = current.getNext();
            currentNext.setPrevious( current.getPrevious() );
            current = current.getNext();
            size--;
        }

        if ( size == 0 )
        {
            current = head;

        }
    }


    // ----------------------------------------------------------
    /**
     * Returns true if the list is at the start
     * @return True or false
     */
    public boolean isAtStart()
    {
        return current == head;
    }


    // ----------------------------------------------------------
    /**
     * Returns true if the list is at the end
     * @return True of false
     */
    public boolean isAtEnd()
    {
        return current == tail;
    }


    // ----------------------------------------------------------



    // ----------------------------------------------------------
    /**
     * Move the list's current position back to the start (the front).
     */
    public void moveToStart()
    {
        current = head;
    }


    // ----------------------------------------------------------
    /**
     * Move the list's current position all the way to the end, just past the
     * last element (the tail).
     */
    public void moveToEnd()
    {
        current = tail;
    }


    /**
     * Gets the size of the list.
     *
     * @return size The size of the list.
     */
    public int size()
    {
        return size;
    }

}
