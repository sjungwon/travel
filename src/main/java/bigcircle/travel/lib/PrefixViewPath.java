package bigcircle.travel.lib;

@FunctionalInterface
public interface PrefixViewPath {
    public String call(String path);
}
