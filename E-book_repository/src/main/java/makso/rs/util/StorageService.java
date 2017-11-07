package makso.rs.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import makso.rs.dto.EbookViewModel;
import makso.rs.model.Ebook;

public interface StorageService {

 /*   void init();

    void store(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();*/

	void store(MultipartFile file, Ebook ebook);

   
}
