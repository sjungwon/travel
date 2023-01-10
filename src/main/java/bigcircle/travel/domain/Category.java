package bigcircle.travel.domain;

public enum Category {
    HOTEL(1L, "호텔"),
    ETC(2L, "나머지");


    private final Long id;
    private final String kr;

    Category(Long id, String kr) {
        this.id = id;
        this.kr = kr;
    }

    public String getKr() {
        return kr;
    }

    public Long getId() {
        return id;
    }
}
