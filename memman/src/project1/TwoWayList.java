
/**
 *  This interface represents a doubly linked list.
 *
 *  @author Xavier Seymour (xaviers3)
 *  @version Apr 29, 2010
 *  @param <T> The type of the object to be stored.
 */
public interface TwoWayList<T>
{
    // ----------------------------------------------------------

    /**
     * Adds an object to the list.
     * @param newElement the object to add.
     */
    public void add(T newElement );

    /**
     * Returns the object stored in the current field.
     * @return The object stored in current.
     */
    public Object getCurrentElement();

    /**
     * Tests if the list is empty.
     * @return True if the list is empty, false otherwise.
     */
    public boolean isEmpty();

    /**
     * Makes the list empty.
     */
    public void makeEmpty();

    /**
     * Gets the next item in the list, setting current to that item.
     */
    public void next();

    /**
     * Gets the previous item in the list, setting current to that item.
     */
    public void previous();

    /**
     * Removes the item in current from the list.
     */
    public void removeCurrent();

    /**
     * Gets the size of the list.
     * @return The size of the list.
     */
    public int size();

}
