package bigcircle.travel.repository.dto;

public class ItemSaveDto {

    private String title;


    private Integer zonecode;

    private String addressDetail;

    private String description;

    private Long categoryId;

    public ItemSaveDto() {
    }

    public ItemSaveDto(String title, Integer zonecode, String addressDetail, String description, Long categoryId) {
        this.title = title;
        this.zonecode = zonecode;
        this.addressDetail = addressDetail;
        this.description = description;
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public Integer getZonecode() {
        return zonecode;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public String getDescription() {
        return description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setZonecode(Integer zonecode) {
        this.zonecode = zonecode;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
