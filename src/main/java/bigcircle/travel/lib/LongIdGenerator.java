package bigcircle.travel.lib;

public class LongIdGenerator {
    private Long id = 0L;

    public synchronized Long newId(){
        return ++id;
    }
}
