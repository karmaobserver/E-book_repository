package makso.rs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import makso.rs.util.StorageProperties;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class EBookRepositoryApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		
		SpringApplication.run(EBookRepositoryApplication.class, args);
		System.out.println("E-book repository started");

	}
	//war
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EBookRepositoryApplication.class);
    }
	
}
