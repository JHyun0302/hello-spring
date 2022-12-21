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
    public String hello(Model model) {
        model.addAttribute("data", "spring!!!");
        return "hello";// localhost:8080/hello  -> resources/templates/hello.html의 ${data}에 'spring!!!' 들어감.
    }

    @GetMapping("hello-mvc") // http://localhost:8080/hello-mvc?name= world!!   //name = world!! 넣어줌
    public String helloMvc(@RequestParam("name" /*required = true(default)*/ /*Command + p*/) String name, Model model) { // name = "입력값"
        model.addAttribute("name", name);
        return "hello-template"; // localhost:8080/hello-mvc  -> resources/templates/hello-template.html
    }


    /**
     * .api 방식은 return하는 .html 없음 -> return: 문자 or 객체
     */
    @GetMapping("hello-string") /*문자만 출력 "hello spring"*/
    @ResponseBody // viewResolver(.html의 body)가 아니라 http 응답에 직접 넣어주겠다! --> 페이지 소스 코드에 "hello spring"만 찍힘
    public String helloString(@RequestParam("name") String name) { // http://localhost:8080/hello-string?name=spring
        return "hello " + name; // 문자인 경우: http에 바로 던짐(name=spring)
    }

    @GetMapping("hello-api") /*데이터 출력 --> 객체를 넘겨줄때 api 사용*/
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) { //http://l   ocalhost:8080/hello-api?name=world!!
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // 객체인 경우: json({key:value})으로 만들어서 http에 반환
    }

    /**
     * hello-api에서 사용할 객체
     */
    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
