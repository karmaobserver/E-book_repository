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
        "/profile",
        "/profile/passwordChange",
        "/categoryAdd",
        "/userAdd",
        "/ebooksByCategory",
        "/ebooksSearch",
        "/ebookEdit",
        "/languages",
        "/ebookAdd",
        "/ebookEdit",
        "/getEbookData",
        "/ebookSearch",
        
        
       
    })
    public String index() {
        return "forward:/index.html";
    }
}
