package hello.hellospring.repository;


import hello.hellospring.domain.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    //Optional은 java8에 들어가는 기능으로, findById나 findByName이 null일 수 있는데,
    //null을 처리하기 위한 것임.
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);

    //회원의 리스트를 반환
    List<Member> findAll();
}
