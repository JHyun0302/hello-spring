package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect //AOP 사용시 사용
@Component // Component Scan으로 스프링에 연결 or SpringConfig에서 @Bean
public class TimeTraceAop {
    @Around("execution(* hello.hellospring..*(..))") // 공통 관심 사항을 어디에 적용할 것인지 (패지키명.그 밑에 있는 것들.. * 클래스(파라미터))
    public Object execut(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed(); //다음 메서드로 진행
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }

    }
}
