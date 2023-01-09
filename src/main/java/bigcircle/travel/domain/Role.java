package bigcircle.travel.domain;

public enum Role{
    ALL(1L, "전체"), ADMIN(2L, "관리자"), MANAGER(3L, "매니저") ,USER(4L, "유저"), BANNED(5L, "정지");
    private final Long id;

    private final String kr;

    Role(Long id, String kr) {
        this.id = id;
        this.kr = kr;
    }

    public Long getId() {
        return id;
    }

    public String getKr() {
        return kr;
    }

    public boolean isLowerRole(Role r){
        return this.getId() >= r.getId();
    }
}
