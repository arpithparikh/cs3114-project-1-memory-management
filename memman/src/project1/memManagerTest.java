
public class memManagerTest
{

    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     * @param args
     */
    public static void main( String[] args )
    {
        MemManager test = new MemManager(100);
        //should be 1
        System.out.println("freeBlockList Size at initialization: "+ test.getFreeBlockList().size());

        //only 1 freeBlock at start
        System.out.println("freeBlockContent: " );

        //Test insert
        byte[] array20 = new byte[20];
        byte[] array15 = new byte[15];
        byte[] array25 = new byte[25];

        array20[0] = 20;
        for(int i = 1; i<array20.length; i++) {
            array20[i] = 1;
        }

        array15[0] = 15;
        for(int i = 1; i<array15.length; i++) {
            array15[i] = 1;
        }
        array25[0] = 25;
        for(int i = 1; i<array25.length; i++) {
            array25[i] = 1;
        }


        test.insert( array20, 20 );
        //System.out.println("freeblockListSize: "+ test.getFreeBlockList().size());
        test.insert( array20, 20 );
        test.insert( array20, 20 );
        test.insert( array15, 15 );
        test.remove( 60 );
        test.remove( 20 );
        test.insert( array15, 15 );
        test.insert( array25, 25 );
        test.remove( 40 );
        test.remove(20);
        test.remove( 60 );
        test.remove( 0 );

        test.getFreeBlockList().moveToStart();
        test.getFreeBlockList().next();
        for(int i=1; i<test.getFreeBlockList().size()+1; i++) {
            Object[] keyArray =
                test.getFreeBlockList().getCurrentElement().keySet().toArray();
            Integer key = (Integer)keyArray[0];
            System.out.println(i+".) "+ "position: "+ key+ ", Size: "+
                test.getFreeBlockList().getCurrentElement().get( key ) );
            test.getFreeBlockList().next();
        }



    }

}
