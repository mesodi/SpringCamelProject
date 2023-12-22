package es.wacoco.SpringCamelProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerClass {

    @GetMapping ("/")
    public String auth(){
        return "index";
    }
}
