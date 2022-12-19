package bigcircle.travel.domain;

public enum Category {
    HOTEL(1L, "호텔", "HOTEL"),
    ETC(2L, "나머지", "ETC");


    private final Long id;
    private final String kr;
    private final String en;

    Category(Long id, String kr, String en) {
        this.id = id;
        this.kr = kr;
        this.en = en;
    }

    public String getKr() {
        return kr;
    }

    public String getEn() {
        return en;
    }

    public Long getId() {
        return id;
    }
}
