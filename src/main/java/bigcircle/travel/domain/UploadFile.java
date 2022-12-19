package bigcircle.travel.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * PK - storeFileName
 */
@Getter
@EqualsAndHashCode
@ToString
public class UploadFile {
    private final String uploadFileName;
    private final String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
