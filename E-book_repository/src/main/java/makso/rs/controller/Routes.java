package makso.rs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public  class Routes {

    @RequestMapping({
        "/about",
        "/home",
        "/tracks/{id:\\w+}",
        "/categories",
        "/ebooks",
        "/users",
        "/login",
        
       
    })
    public String index() {
        return "forward:/index.html";
    }
}
