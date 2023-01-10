package bigcircle.travel.service;

import bigcircle.travel.domain.UploadFile;
import bigcircle.travel.lib.file.FileStore;
import bigcircle.travel.repository.FileRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class FileService {

    private final FileStore fileStore;
    private final FileRepository fileRepository;

    public FileService(FileStore fileStore, FileRepository fileRepository) {
        this.fileStore = fileStore;
        this.fileRepository = fileRepository;
    }

    @Transactional
    public List<UploadFile> saveFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> uploadFiles = this.fileStore.storeFiles(multipartFiles);
        for (UploadFile uploadFile : uploadFiles) {
            this.fileRepository.saveFile(uploadFile);
        }
        return uploadFiles;
    }

    public UploadFile findByStoreFileName(String storeFileName){
        return this.fileRepository.findByStoreFileName(storeFileName);
    }
}
