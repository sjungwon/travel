package bigcircle.travel.lib;

public class LongIdGenerator {

    private static Long id = 0L;

    public synchronized Long getId(){
        return ++id;
    }

}
