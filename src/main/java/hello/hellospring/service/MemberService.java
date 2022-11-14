package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;


import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    public Long join(Member member){
        //같은 이름이 있는 중복 회원x
       /*Optional<Member> result = memberRepository.findByName(member.getName());
        //result.orElseGet()
        result.ifPresent(m->{ // ifPiresent: 어떤 값이 있으면 Illegal~ 작동함
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });*/
        ValidateDuplicateMember(member); // Refactor This: Ctrl + Shift + Alt + T
        memberRepository.save(member);
        return member.getId();
    }

    private void ValidateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
            .ifPresent(m->{ // ifPiresent: 어떤 값이 있으면 Illegal~ 작동함
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    // 전체 회원 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
