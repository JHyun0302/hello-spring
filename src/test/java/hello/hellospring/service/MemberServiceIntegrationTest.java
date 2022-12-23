package hello.hellospring.service;
/**
 * Spring Container 연동하고 하는 테스트: 통합 테스트
 * 자바 코드만으로 테스트: 단위 테스트(통합 테스트보다 설계가 잘 된 case)
 */

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional //test 실행시 Transactional 먼저 실행해서 db에 있는 데이터 다 날리고 빈 상태로 rollback 시키고 test 수행 (test 각각 모두 적용)
class MemberServiceIntegrationTest {
    /**
     * Spring Container에게서 memberRepository & memService 달라고 해야함!
     * -> constructure injection에서 "new" 아님!
     */

    /**
     * field 기반으로 @Autowired 바로 쓰기
     */
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;


    @Test
        //@Commit //test 결과가 db에 저장됨
    void 회원가입() { //@test는 한글로 이름써도 됨
        //given: 어떤 상황이 주어졌을 때
        Member member = new Member();
        member.setName("spring");

        //when: 이걸 실행했을 때
        Long saveId = memberService.join(member);

        //then: 결과가 이게 나와야 해!
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName()); // import: option + enter
        // (given: "spring") & (when: saveId값) 같은가?
    }

    @Test
    public void 중복_회원_예외() { //예외 터뜨리기
        //given: 어떤 상황이 주어졌을 때
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when: 이걸 실행했을 때
        /*
                 memberService.join(member1);
                  try {
                      memberService.join(member2);
                      fail();
                  }catch (IllegalStateException e){
                      assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
                  }
         */

        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // 뒤에 executable(lambda 로직) 실행시 앞에 expectedType (IllegalStateException 예외)가 터져야함
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //then: 결과가 이게 나와야 해!

    }
}