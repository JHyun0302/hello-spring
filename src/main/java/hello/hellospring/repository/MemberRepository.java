package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); // 회원 저장
    Optional<Member> findById(Long id); // 만약 값이 없으면 null 반환! -> Optional을 이용해 null 감싸서 반환
    Optional<Member> findByName(String name);
    List<Member> findAll(); // 모든 회원 리스트 반환
}
