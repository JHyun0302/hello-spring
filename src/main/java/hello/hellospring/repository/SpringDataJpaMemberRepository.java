package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * extends: interfave끼리 연결시 사용
 * JpaRepository<객체, Id>, MemberRepository(다중 상속)
 */
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    /**
     * JpaRepository가 SpringDataJpaMemberRepository 가지고 있으면 자동으로 구현체 만들고 Spring Bean에 등록시켜줌
     */
    @Override
    Optional<Member> findByName(String name); //JPQL: select m from Member m where m.name = ?

//    Optional<Member> findByNameAndId(String name, Long id); //ex
}

