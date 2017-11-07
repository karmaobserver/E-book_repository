package makso.rs.util;

import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    private String location =  ResourceBundle.getBundle("index").getString("docs");

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
