package bigcircle.travel.domain;

import java.util.UUID;

public enum CategoryType {
    HOTEL(1L, "호텔"),
    ETC(2L, "나머지");


    private Long id;
    private String korean;

    CategoryType(Long id, String korean) {
        this.id = id;
        this.korean = korean;

    }

    public String getKorean(){return korean;}

    public Long getId() {
        return id;
    }
}
