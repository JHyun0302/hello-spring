package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em; //JPA는 EntityManager가 모든 게 동작함

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * 저장하고 자동으로 count하는 id는 sql문 짤 필요 없음
     */
    @Override
    public Member save(Member member) {
        em.persist(member); //persist: 저장
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); //find(조회할 타입, pk)
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList(); //select m : 객체(Member) 자체를 select함
        //JPQL(객체지향 쿼리): 보통은 table 대상으로 쿼리 날리는데 JPQL은 (Member) 객체를 대상으로 쿼리 날림
        //그럼 SQL로 번역이 됨
    }
}
