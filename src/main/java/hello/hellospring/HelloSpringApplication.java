package hello.hellospring;
/**
 * package hello.hellospring; 하위 패키지만 @Component를 통해 스프링 빈으로 등록 가능
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringApplication.class, args);
    }

}


