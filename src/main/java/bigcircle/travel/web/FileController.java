package bigcircle.travel.web;

import bigcircle.travel.lib.file.FileStore;
import bigcircle.travel.domain.UploadFile;
import bigcircle.travel.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/files")
@Slf4j
public class FileController {

    private final FileService fileService;
    private final FileStore fileStore;

    public FileController(FileService fileService, FileStore fileStore) {
        this.fileService = fileService;
        this.fileStore = fileStore;
    }

    @GetMapping
    public String getForm(){
        return "test";
    }

    @GetMapping("/test/{filename}")
    public String getImage(@PathVariable String filename, Model model){
        model.addAttribute("url","http://localhost:8080/file/"+filename);
        return "test2";
    }

    @ResponseBody
    @PostMapping("/save")
    public List<UploadFile> saveFile(@RequestParam List<MultipartFile> multipartFiles) throws IOException {

        List<UploadFile> uploadFiles = fileService.saveFiles(multipartFiles);

        for (MultipartFile multipartFile : multipartFiles) {
            log.info("file={}", multipartFile.getOriginalFilename());

        }

        return uploadFiles;
    }


    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadFile(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @GetMapping("/attach/{filename}")
    public ResponseEntity<Resource> downloadAttachFile(@PathVariable String filename) throws MalformedURLException {

        UploadFile uploadFile = fileService.findByStoreFileName(filename);
        UrlResource urlResource = new UrlResource("file:"+fileStore.getFullPath(filename));

        String encodedUploadFileName = UriUtils.encode(uploadFile.getUploadFileName(), StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(urlResource);
    }
}
