package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * memberService()와  memberRepository()를 스프링 빈에 등록
 */
@Configuration
public class SpringConfig {
    /*private DataSource dataSource; //JDBC 사용할 때 필요

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    /*private EntityManager em; //JdbcTemplate 사용할 때 필요

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }*/

    private final MemberRepository memberRepository; //SpringData JPA 사용할 때 필요

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean //@Configuration 보고 @Bean을 스프링 빈에 등록시킴
    public MemberService memberService() {
        //return new MemberService(memberRepository()); // memberService는 memberRepository가 필요!(파라미터)
        return new MemberService(memberRepository); // SpringDataJpa
    }

    /*@Bean
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }*/

//    @Bean
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository(); //MemberRepository는 인터페이스라 new 안됨
//        return new JdbcMemberRepository(dataSource);
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
//  }
}