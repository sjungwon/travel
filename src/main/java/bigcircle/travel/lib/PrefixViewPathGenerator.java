package bigcircle.travel.lib;

import org.springframework.stereotype.Component;

@Component
public class PrefixViewPathGenerator {
    public PrefixViewPath prefixView(String prefix){
        return (String path) -> {
            if(path == null){
                return prefix;
            }
            return prefix + "/" + path;
        };
    }
}
