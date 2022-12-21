package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    /**
     * dependency injection(의존관계 주입)
     * 1. memberRepository 객체 생성
     * 2. memberService의 constructure로 memberRepository 주입
     */
    @BeforeEach // test 실행전 실행: 각각의 객체 새로 생성 (test는 독립적 실행)
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
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

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}