
/**
 *  A node in a linked list.
 *
 *  @author Xavier Seymour (xaviers3)
 *  @version Apr 29, 2010
 * @param <T> The type of the object to be stored.
 */
public class ListNode<T>
{
    //~ Instance/static variables .............................................

    private ListNode<T> next;
    private ListNode<T> previous;
    private T element;


    //~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a node.
     * @param theElement the element to be stored.
     */
    public ListNode(T theElement)
    {
        this(theElement, null);
    }

    // ----------------------------------------------------------
    /**
     * Creates a node with a link to the specified node (next node in list).
     * @param element the element to be stored.
     * @param node The node to follow this node in the list.
     */
    public ListNode(T element, ListNode<T> node)
    {
        setElement(element);
        setNext(node);


    }

    //~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Gets the current data value stored in this node.
     * @return The element
     */
    public T getElement()
    {
        return element;
    }

    // ----------------------------------------------------------
    /**
     * Set the data value stored in this node.
     * @param value the new data value to set
     */
    public void setElement(T value)
    {
        element = value;
    }

    // ----------------------------------------------------------
    /**
     * Get the next node in this chain.
     * @return a reference to the next node in the chain.
     */
    public ListNode<T> getNext()
    {
        return next;
    }

    // ----------------------------------------------------------
    /**
     * Set the value of this node's next pointer.
     * @param value the node to point to as the next one in the chain.
     */
    public void setNext(ListNode<T> value)
    {
        next = value;
    }

    //-----------------------------------------------------------
    /**
     * Gets the previous node of the current ListNode.
     * @return The previous node to set
     */
    public ListNode<T> getPrevious()
    {
        return previous;
    }

    // ----------------------------------------------------------
    /**
     * Sets the previous node of the current ListNode.
     * @param prev the previous node to set
     */
    public void setPrevious( ListNode<T> prev )
    {
        previous = prev;
    }


}
