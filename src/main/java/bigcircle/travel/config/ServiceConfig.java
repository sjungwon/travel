package bigcircle.travel.config;

import bigcircle.travel.lib.file.FileStore;
import bigcircle.travel.repository.*;
import bigcircle.travel.service.FileService;
import bigcircle.travel.service.ItemService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ServiceConfig {
    @Bean
    public FileService fileService(FileStore fileStore, FileRepository fileRepository){
        return new FileService(fileStore, fileRepository);
    }

    @Bean
    public ItemService itemService(ItemRepository itemRepository, AddressRepository addressRepository, FileRepository fileRepository, ItemImageRepository itemImageRepository, FileStore fileStore){
        return new ItemService(itemRepository, addressRepository,fileRepository, itemImageRepository, fileStore);
    }
}
