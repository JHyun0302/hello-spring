package hello.hellospring.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model){ //http://localhost:8080/hello
        model.addAttribute("data", "월드!!!");
        return "hello";
    }
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){ // http://localhost:8080/hello-mvc?name= world!!
        model.addAttribute("name", name);
        return "hello-template";
    }
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){ // http://localhost:8080/hello-string?name=spring
        return "hello " +name; //"hello spring"
    }

    @GetMapping("hello-api") // {key:value}, 객체를 넘겨줄때 api 사용
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){ //http://localhost:8080/hello-api?name=world!!
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello{
        private String name;

        public String getName() { //Getter&Setter: Alt + Insert
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
