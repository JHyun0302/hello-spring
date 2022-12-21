package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest { // create class: Alt + insert
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 테스트는 서로 의존관계없이 설계되어야함!
    public void afterEach() { // save() 테스트하고 afterEach() 돌고 findByName() 테스트하고 afterEach() 테스트하고 findAll()테스트 하는 방식
        repository.clearStore();
    }

    @Test
    public void save() { // run: Alt + Enter
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get(); // Optional을 반환 받을 때: .get();
        //System.out.println("result = " + (result == member));
        Assertions.assertEquals(member, result); // .assertEquals(expect, actual)
        assertThat(member).isEqualTo(result); // member랑 result가 같으면 성공 //import: Alt + Enter
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member(); // rename: shift + F6
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get(); // Refactor: Ctrl + Alt + V
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);

    }

}
