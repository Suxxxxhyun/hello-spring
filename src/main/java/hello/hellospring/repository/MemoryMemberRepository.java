package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository{

    //store는 회원을 저장하기 위한 것
    private static Map<Long, Member> store = new HashMap<>();
    //sequence는 0,1,2와 같은 키값을 생성하기 위한 것
    private static long sequence = 0L;
    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //ofNullable메소드를 통해 Member객체가 null이어도 해당 객체를 감싸줌.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        //파라미터로 받은 name과 같은지를 루프를 돌며서 확인. 찾았다면(findAny)
        //걔를 반환, 찾지 못하면 null반환
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    //findAll - 지금까지 저장된 회원의 리스트를 반환
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
