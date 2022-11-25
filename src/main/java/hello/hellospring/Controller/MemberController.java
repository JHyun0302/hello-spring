package hello.hellospring.Controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;

    /**
     * @Autowired private MemberService memberService; => 필드 주입(단점: 중간에 내용 바꾸기 불가능)
     */

    /**
     *     private MemberService memberService; => setter 주입(단점: 누군가 호출하면 public으로 되어있어야함)
     *
     *     @Autowired
     *     public void setMemberService(MemberService memberService) {
     *         this.memberService = memberService;
     *     }
     */


    /**
     * 생성자를 통해 memberService가 MemberController에 주입됨. => 생성자 주입
     */
    @Autowired //constructor의 매개변수(memberService)와 스프링 컨테이너에 저장된 memberService를 서로 연결시켜줌
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
