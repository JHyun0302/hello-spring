package hello.hellospring.Controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
        System.out.println("memberService = " + memberService.getClass()); //$$EnhancerBySpringCGLIB -> 프록시 생성 확인
    }

    @GetMapping("/members/new") // http://localhost:8080/members/new
    public String createForm() {
        return "members/createMemberForm"; // resources/templates/members/createMemberForm.html
    }

    /**
     * @GetMapping: url창에 직접 입력할 때 사용
     * @PostMapping: .html에서 데이터를 전달 받을 때 사용
     */
    @PostMapping("/members/new")
    public String create(MemberForm form) { // createMemberForm.html의 input에서 넘어온 name을 MemberForm의 name에 저장
        Member member = new Member();
        member.setName(form.getName()); // form.getName()된 name을 =MemberForm의 private String name;에 넣어줌
        /*System.out.println("member= " +member.getName());*/

        memberService.join(member);

        return "redirect:/"; //다시 home화면으로
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        // members List 전체를 model에 담아서 html에 출력
        return "members/memberList";
    }
}

/**
 * 최근 파일 보기: Ctrl + E
 */
