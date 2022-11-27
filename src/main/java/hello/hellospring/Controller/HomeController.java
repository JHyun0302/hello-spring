package hello.hellospring.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") //localhost:8080에 들어가면 @GetMapping 실행됨
    public String home(){
        return "home";
    }

}
