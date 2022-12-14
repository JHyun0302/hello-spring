package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * test만들기: Ctrl + Shift + T
 */
//@Service //스프링이 MemberService class를 스프링 컨테이너에 연결시켜줌
@Transactional //데이터 저장하고 수정할때 필요
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired // memberService는 memberRepository가 필요!(파라미터)
    //  springConfig에서 @Bean을 모두 주석처리 시키고 실행시 스프링이 Memberservice 객체를 관리하지 않으므로 에러남
    public MemberService(MemberRepository memberRepository) { //constructor
        this.memberRepository = memberRepository;
    }

/*    public static void main(String[] args) {
        MemberService memberService = new MemberService(); // new한 경우 @Autowired는 작동하지 않음.
    }*/

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        //같은 이름이 있는 중복 회원x
       /*Optional<Member> result = memberRepository.findByName(member.getName()); // command + option + V
    방법1:    result.orElseGet()   //값이 있으면 꺼내고 값이 없으면 orElseGet() 메서드의 default값 꺼냄
    방법2:    result.ifPresent(m->{             // ifPiresent: 어떤 값이 있으면 Illegal~ 작동함
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });*/
        
        ValidateDuplicateMember(member); //Refactor This: Ctrl + T
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복회원 검증
     */
    private void ValidateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
