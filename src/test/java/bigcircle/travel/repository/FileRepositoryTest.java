package bigcircle.travel.repository;

import bigcircle.travel.domain.UploadFile;
import bigcircle.travel.repository.memory.FileMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
class FileRepositoryTest {
    @Autowired
    private FileRepository fileRepository;

    @AfterEach
    void afterEach(){
        if(this.fileRepository instanceof FileMemoryRepository){
            ((FileMemoryRepository)this.fileRepository).clear();
        }
    }

    @Test
    public void save() throws IOException {
        //given
        UploadFile uploadFile1 = new UploadFile("테스트이미지1.jpg", UUID.randomUUID().toString().substring(0, 30) + ".jpg");
        UploadFile uploadFile2 = new UploadFile("테스트이미지2.jpg", UUID.randomUUID().toString().substring(0, 30) + ".jpg");
        List<UploadFile> uploadFiles = new ArrayList<>();
        uploadFiles.add(uploadFile1);
        uploadFiles.add(uploadFile2);

        //when
        for (UploadFile uploadFile : uploadFiles) {
            this.fileRepository.saveFile(uploadFile);
        }
    }

    @Test
    @DisplayName("저장 후 찾기")
    public void saveAndFind() throws IOException {
        //given
        //mock를 사용하지 않게 받는 데이터를 변경함 -> 파일 저장은 다른 레이어에서 수행, 저장한 정보만 넘겨서 db에 저장
//        MockMultipartFile image = new MockMultipartFile("image", "테스트이미지1.jpg", "image/png", new FileInputStream("/Users/jungwon/dev/java/study/travel/filestore/ff6c505b-9ae0-4017-a621-5b8f55402754.jpeg"));
        UploadFile uploadFile1 = new UploadFile("테스트이미지1.jpg", UUID.randomUUID().toString().substring(0, 30) + ".jpg");
        UploadFile uploadFile2 = new UploadFile("테스트이미지2.jpg", UUID.randomUUID().toString().substring(0, 30) + ".jpg");
        List<UploadFile> uploadFiles = new ArrayList<>();
        uploadFiles.add(uploadFile1);
        uploadFiles.add(uploadFile2);

        //when
        for (UploadFile uploadFile : uploadFiles) {
            this.fileRepository.saveFile(uploadFile);
        }

        //then
        for (UploadFile uploadFile : uploadFiles) {
            UploadFile found = this.fileRepository.findByStoreFileName(uploadFile.getStoreFileName());
            Assertions.assertThat(found.getStoreFileName()).isEqualTo(uploadFile.getStoreFileName());
            Assertions.assertThat(found.getUploadFileName()).isEqualTo(uploadFile.getUploadFileName());
        }
    }

    @Test
    @DisplayName("delete")
    public void delete(){
        //given
        UploadFile uploadFile = new UploadFile("테스트이미지1.jpg", UUID.randomUUID().toString().substring(0, 30) + ".jpg");
        this.fileRepository.saveFile(uploadFile);

        //when
        this.fileRepository.deleteFile(uploadFile.getStoreFileName());

        //then
        Assertions.assertThatThrownBy(()->
                this.fileRepository.findByStoreFileName(uploadFile.getStoreFileName())).isInstanceOf(EmptyResultDataAccessException.class);
    }
}