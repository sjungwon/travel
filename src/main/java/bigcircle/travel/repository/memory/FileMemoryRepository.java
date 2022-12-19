package bigcircle.travel.repository.memory;

import bigcircle.travel.domain.UploadFile;
import bigcircle.travel.repository.FileRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class FileMemoryRepository implements FileRepository {

    private final Map<String, UploadFile> db;

    public FileMemoryRepository() {
        this.db = new ConcurrentHashMap<>();
    }

    @Override
    public UploadFile findByStoreFileName(String storeFileName){
        return this.db.get(storeFileName);
    }

    @Override
    public void saveFiles(List<UploadFile> uploadFiles) {
        for (UploadFile uploadFile : uploadFiles) {
            this.db.put(uploadFile.getStoreFileName(),uploadFile);
        }
    }

    @Override
    public void saveFile(UploadFile uploadFile){
        this.db.put(uploadFile.getStoreFileName(), uploadFile);
    }

    public void clear(){
        this.db.clear();
    }
}
