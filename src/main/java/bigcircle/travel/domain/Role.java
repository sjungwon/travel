package bigcircle.travel.domain;

public enum Role {
    TYPE1("ADMIN"), TYPE2("USER");

    final String type;

    Role(String type){
        this.type = type;
    }
}
