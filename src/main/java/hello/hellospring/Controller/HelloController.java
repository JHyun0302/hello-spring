package hello.hellospring.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    /**
     * Controller가 리턴 값을 문자로 반환시 "viewResolver"가 화면을 찾아서 처리함.
     */
    @GetMapping("hello")  //http://localhost:8080/hello
    public String hello(Model model){
        model.addAttribute("data", "spring!!!");
        return "hello";// localhost:8080/hello  -> resources/templates/hello.html의 ${data}에 'spring!!!' 들어감.
    }

    @GetMapping("hello-mvc") // http://localhost:8080/hello-mvc?name= world!!   //name = world!! 넣어줌
    public String helloMvc(@RequestParam("name" /*required = true(default)*/ /*Ctrl + p*/) String name, Model model){
        model.addAttribute("name", name);
        return "hello-template"; // localhost:8080/hello-mvc  -> resources/templates/hello-template.html
    }


    @GetMapping("hello-string") /*문자만 출력 "hello spring"*/
    @ResponseBody // .html의 body가 아니라 http에서 body부분에 직접 넣어주겠다! --> 페이지 소스 코드에 "hello spring"만 찍힘
    public String helloString(@RequestParam("name") String name){ // http://localhost:8080/hello-string?name=spring
        return "hello " +name;
    }

    @GetMapping("hello-api") /*데이터 출력 --> 객체를 넘겨줄때 api 사용*/ // json방식 - {key:value}
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){ //http://localhost:8080/hello-api?name=world!!
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // 문자가 아닌 객체 return
    }

    /**
     * hello-api에서 사용할 객체
     */
    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
