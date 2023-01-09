package bigcircle.travel.lib.file;

import bigcircle.travel.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename){
        return fileDir + filename;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        if(multipartFiles == null){
            return storeFileResult;
        }
        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()){
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String resizedOriginalFileName = createResizedOriginalFileName(originalFilename);
        String storeFileName = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new UploadFile(resizedOriginalFileName, storeFileName);
    }

    public void deleteFile(UploadFile uploadFile){
        File file = new File(getFullPath(uploadFile.getStoreFileName()));
        if(file.exists()){
            boolean delete = file.delete();
        }
    }

    public void deleteFile(String storeFileName){
        File file = new File(getFullPath(storeFileName));
        if(file.exists()){
            boolean delete = file.delete();
        }
    }

    private String createResizedOriginalFileName(String originalFileName){
        if(originalFileName.length() < 50){
            return originalFileName;
        }

        String ext = extractExt(originalFileName);
        return originalFileName.substring(0,40) + ext;
    }

    private String createStoreFileName(String originalFilename){
        String ext = extractExt(originalFilename);
        String id = UUID.randomUUID().toString();
        if(id.length() > 30){
            id = id.substring(0,31);
        }
        return id + "." + ext;
    }

    private String extractExt(String originalFileName){
        int index = originalFileName.lastIndexOf(".");
        return originalFileName.substring(index+1);
    }
}
