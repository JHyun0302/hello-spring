package hello.hellospring.service;

import hello.hellospring.repository.JdbcMemberRepository;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * memberService()와  memberRepository()를 스프링 빈에 등록
 */
@Configuration
public class springConfig {

    private DataSource dataSource;

    @Autowired
    public springConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }



    @Bean //@Configuration 보고 @Bean을 스프링 빈에 등록시킴
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        //return new MemoryMemberRepository(); //MemberRepository는 인터페이스라 new 안됨
        return new JdbcMemberRepository(dataSource);
    }
}
