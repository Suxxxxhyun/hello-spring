package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    /*
    @AfterEach : 한번에 여러 테스트를 실행하면 메모리 DB에 직전 테스트의 결과가 남을 수 있다. 이렇게
    되면 다음 이전 테스트 때문에 다음 테스트가 실패할 가능성이 있다. @AfterEach 를 사용하면 각 테스트가
    종료될 때 마다 이 기능을 실행한다. 여기서는 메모리 DB에 저장된 데이터를 삭제한다.
    테스트는 각각 독립적으로 실행되어야 한다. 테스트 순서에 의존관계가 있는 것은 좋은 테스트가 아니다.
     */
    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
        //독립적으로 실행하기 때문에, 회원가입()에 spring으로 저장한 회원과
        //중복_회원_예외()에 spring으로 저장한 회원 방식으로 해도
        //오류가 나지 않는다. afterEach()로, 메모리 DB에 저장된 데이터를 삭제했기 때문.
    }

    @Test
    void 회원가입() {
        //given(무엇인가 주어졌을때)
        Member member = new Member();
        member.setName("spring");

        //when(이를 실행하면)
        Long saveId = memberService.join(member);

        //then(이렇게 나와야해)문법
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        //일부러 같은 이름의 회원을 등록함.
        /* 방법1)
        memberService.join(member1);
        try{
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/

        /*방법2 : member2을 회원가입시키면 IllegalStateException이 발생함.
        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));*/

        //방법3
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}