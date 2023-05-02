package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
//@Controller를 통해
//스프링이 스프링컨테이너에 MemberController객체를 생성하고 관리함
public class MemberController {
    private MemberService memberService;

    //1. 의존 주입 중 필드주입
    //@Autowired
    //private MemberService memberService;


    //2. 의존 주입 중 setter의존주입
    //@Autowired
    //public void setMemberService(MemberService memberService){
    //    this.memberService = memberService;
    //}

    @Autowired
    //3. 의존주입 중 생성자 의존주입
    //@Autowired으로,
    //스프링이 스프링 컨테이너에 있는 MemberService를 가져다가 연결해줌.
    public MemberController(MemberService memberService) {
       this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }

}
