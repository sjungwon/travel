package bigcircle.travel.repository;

import bigcircle.travel.domain.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileRepository {
    public UploadFile findByStoreFileName(String storeFileName);

    public void saveFiles(List<UploadFile> uploadFiles);

    public void saveFile(UploadFile uploadFile);
}
