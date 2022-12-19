package bigcircle.travel.repository.dto;

import bigcircle.travel.domain.UploadFile;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class ItemSaveDto {

    private String title;
    private String thumbnail;
    private Integer zonecode;

    private String addressDetail;

    private String description;

    private Long categoryId;

    private String createdAt;

    private String updatedAt;

    public ItemSaveDto() {
    }

    public ItemSaveDto(String title, String thumbnail, Integer zonecode, String addressDetail, String description, Long categoryId, String createdAt, String updatedAt) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.zonecode = zonecode;
        this.addressDetail = addressDetail;
        this.description = description;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
