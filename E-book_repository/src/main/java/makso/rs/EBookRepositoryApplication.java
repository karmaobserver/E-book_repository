package makso.rs;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import makso.rs.udd.indexer.IndexManager;
import makso.rs.udd.indexer.UDDIndexer;
import makso.rs.util.StorageProperties;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class EBookRepositoryApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		
		SpringApplication.run(EBookRepositoryApplication.class, args);
		System.out.println("E-book repository started");
		
		//IndexManager.IndexInit();
		//TODO temp soultion when there is no indexes yet
		String path = ResourceBundle.getBundle("index").getString("index");
		File indexDirPath = new File(path);
		Directory indexDir;
		try {
			indexDir = new SimpleFSDirectory(indexDirPath);
			DirectoryReader reader = DirectoryReader.open(indexDir);
		} catch (IOException e) {
			IndexManager.getIndexer();
			//e.printStackTrace();
		}
		
	}
	//war
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EBookRepositoryApplication.class);
    }
	
}
