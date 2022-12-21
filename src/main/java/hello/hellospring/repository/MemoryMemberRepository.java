package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

/**
 * 구현체: 가벼운 메모리 기반의 데이터 저장소
 */


public class MemoryMemberRepository implements MemberRepository { // Implements: Alt + Enter
    /**
     * Map<id, name>
     */
    private static Map<Long, Member> store = new HashMap<>(); // Import: Alt + Enter
    private static long sequence = 0L; // 0,1,2... key값 생성

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // null이면 optional 감싸서 반환
    }

    @Override
    public Optional<Member> findByName(String name) {
        /**
         * lambda:
         */
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                // getName()의 name과 parameter의 name이 같은지 loop 돌면서 비교
                .findAny();
        // 같은 값 1개라도 찾으면 바로 반환 (끝까지 찾는데 없으면 Optional 감싸서 반환)
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    } // test끝날 때마다 store.clear();
}
